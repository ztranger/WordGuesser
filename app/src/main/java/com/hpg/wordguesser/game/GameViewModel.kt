package com.hpg.wordguesser.game

import android.app.Application
import android.os.SystemClock
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.hpg.wordguesser.data.SetupPreferences
import com.hpg.wordguesser.data.WordRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GameViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = WordRepository(application)
    private val setupPreferences = SetupPreferences(application)

    private val _uiState = MutableStateFlow(GameUiState())
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()

    private var roundJob: Job? = null
    private var deck = WordDeck(emptyList())

    init {
        val knownIds = WordRepository.knownCategoryIds
        val language = AppLanguage.resolve(setupPreferences.loadLanguageCode())
        val setup = SetupSettings.sanitize(
            raw = setupPreferences.load() ?: SetupSettings(selectedCategoryIds = knownIds),
            knownCategoryIds = knownIds,
            language = language
        )
        val strings = GameStrings.forLanguage(language)
        _uiState.update {
            it.copy(
                language = language,
                categories = WordRepository.definitions.map { definition ->
                    WordCategory(
                        id = definition.id,
                        title = strings.topicTitle(definition.topicId),
                        fileName = definition.fileName,
                        difficulty = definition.difficulty,
                        difficultyLabel = definition.difficulty?.let { strings.difficultyLabel(it) }
                    )
                },
                selectedCategoryIds = setup.selectedCategoryIds,
                targetScore = setup.targetScore,
                teamCount = setup.teamCount,
                teamNames = setup.teamNames,
                roundDurationSec = setup.roundDurationSec
            )
        }
        reloadCategories(language)
    }

    fun setLanguage(language: AppLanguage) {
        if (language == _uiState.value.language) return
        setupPreferences.saveLanguage(language)
        _uiState.update { state ->
            state.copy(
                language = language,
                teamNames = GameRules.adjustTeamNames(state.teamNames, state.teamCount, language),
                wordsReady = false
            )
        }
        persistSetup()
        reloadCategories(language)
    }

    fun setTargetScore(score: Int) {
        _uiState.update { it.copy(targetScore = score) }
        persistSetup()
    }

    fun setTeamCount(count: Int) {
        _uiState.update {
            it.copy(
                teamCount = count,
                teamNames = GameRules.adjustTeamNames(it.teamNames, count, it.language)
            )
        }
        persistSetup()
    }

    fun setTeamName(index: Int, name: String) {
        _uiState.update { state ->
            val names = state.teamNames.toMutableList()
            if (index in names.indices) {
                names[index] = name
            }
            state.copy(teamNames = names)
        }
        persistSetup()
    }

    fun setRoundDuration(seconds: Int) {
        _uiState.update { it.copy(roundDurationSec = seconds) }
        persistSetup()
    }

    fun toggleCategory(id: String) {
        _uiState.update { state ->
            val selected = state.selectedCategoryIds.toMutableSet()
            if (!selected.add(id)) {
                selected.remove(id)
            }
            state.copy(selectedCategoryIds = selected)
        }
        persistSetup()
    }

    fun startGame() {
        val state = _uiState.value
        if (!state.wordsReady || state.selectedCategoryIds.isEmpty()) return
        roundJob?.cancel()
        viewModelScope.launch {
            val words = withContext(Dispatchers.IO) {
                repository.loadWordsForCategories(state.language, state.selectedCategoryIds)
            }
            if (words.isEmpty()) return@launch
            deck = WordDeck(words)
            val names = GameRules.adjustTeamNames(state.teamNames, state.teamCount, state.language)
            val teams = names.mapIndexed { index, name ->
                Team(id = index, name = name.ifBlank { state.strings.teamName(index + 1) })
            }
            _uiState.update {
                it.copy(
                    teams = teams,
                    teamNames = names,
                    currentTeamIndex = 0,
                    gameOver = false,
                    winnerIndex = null,
                    roundGuessed = emptyList(),
                    roundMissed = emptyList()
                )
            }
            persistSetup()
            beginRound()
        }
    }

    fun startNextRound() {
        val state = _uiState.value
        if (state.gameOver) return
        beginRound()
    }

    fun onGuessed() {
        val state = _uiState.value
        if (state.screen != GameScreen.Play || state.currentWord.isBlank()) return
        val guessed = state.roundGuessed + state.currentWord
        val next = deck.next()
        _uiState.update {
            it.copy(
                roundGuessed = guessed,
                currentWord = next.first,
                currentCategoryTitle = next.second
            )
        }
    }

    fun onSkipped() {
        val state = _uiState.value
        if (state.screen != GameScreen.Play || state.currentWord.isBlank()) return
        val missed = state.roundMissed + state.currentWord
        val next = deck.next()
        _uiState.update {
            it.copy(
                roundMissed = missed,
                currentWord = next.first,
                currentCategoryTitle = next.second
            )
        }
    }

    fun onResultsNext() {
        val state = _uiState.value
        val scores = state.teams.map { it.score }
        val gameOver = GameRules.shouldFinishGame(
            scores = scores,
            targetScore = state.targetScore,
            lastPlayedIndex = state.currentTeamIndex
        )
        _uiState.update {
            it.copy(
                screen = GameScreen.Scoreboard,
                gameOver = gameOver,
                winnerIndex = if (gameOver) GameRules.uniqueLeaderIndex(scores) else null,
                currentTeamIndex = if (gameOver) {
                    it.currentTeamIndex
                } else {
                    GameRules.nextTeamIndex(it.currentTeamIndex, it.teams.size)
                }
            )
        }
    }

    fun newGame() {
        roundJob?.cancel()
        _uiState.update { state ->
            GameUiState(
                categories = state.categories,
                selectedCategoryIds = state.selectedCategoryIds,
                targetScore = state.targetScore,
                teamCount = state.teamCount,
                teamNames = state.teamNames,
                roundDurationSec = state.roundDurationSec,
                wordsReady = state.wordsReady,
                language = state.language
            )
        }
    }

    private fun reloadCategories(language: AppLanguage) {
        viewModelScope.launch {
            val categories = withContext(Dispatchers.IO) {
                repository.ensureWordFiles(language)
                repository.loadCategories(language)
            }
            _uiState.update { state ->
                if (state.language != language) {
                    state
                } else {
                    state.copy(categories = categories, wordsReady = true)
                }
            }
        }
    }

    private fun persistSetup() {
        val state = _uiState.value
        setupPreferences.save(
            SetupSettings(
                targetScore = state.targetScore,
                teamCount = state.teamCount,
                teamNames = state.teamNames,
                roundDurationSec = state.roundDurationSec,
                selectedCategoryIds = state.selectedCategoryIds
            )
        )
    }

    private fun beginRound() {
        roundJob?.cancel()
        roundJob = viewModelScope.launch {
            _uiState.update {
                it.copy(
                    screen = GameScreen.Countdown,
                    countdown = 3,
                    remainingMs = it.roundDurationSec * 1000L,
                    currentWord = "",
                    currentCategoryTitle = "",
                    roundGuessed = emptyList(),
                    roundMissed = emptyList()
                )
            }
            for (tick in 3 downTo 1) {
                _uiState.update { it.copy(countdown = tick) }
                delay(1000)
            }
            val first = deck.next()
            val durationMs = _uiState.value.roundDurationSec * 1000L
            _uiState.update {
                it.copy(
                    screen = GameScreen.Play,
                    currentWord = first.first,
                    currentCategoryTitle = first.second,
                    remainingMs = durationMs
                )
            }
            val startedAt = SystemClock.elapsedRealtime()
            while (isActive) {
                val remaining = durationMs - (SystemClock.elapsedRealtime() - startedAt)
                if (remaining <= 0L) {
                    finishRound()
                    break
                }
                _uiState.update { it.copy(remainingMs = remaining) }
                delay(100)
            }
        }
    }

    private fun finishRound() {
        val state = _uiState.value
        val unfinished = state.currentWord
        val missed = if (unfinished.isNotBlank()) state.roundMissed + unfinished else state.roundMissed
        val points = state.roundGuessed.size
        val updatedTeams = state.teams.mapIndexed { index, team ->
            if (index == state.currentTeamIndex) team.copy(score = team.score + points) else team
        }
        _uiState.update {
            it.copy(
                screen = GameScreen.RoundResults,
                teams = updatedTeams,
                roundMissed = missed,
                currentWord = "",
                remainingMs = 0L
            )
        }
    }
}
