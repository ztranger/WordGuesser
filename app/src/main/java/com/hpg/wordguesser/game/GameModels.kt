package com.hpg.wordguesser.game

data class WordCategory(
    val id: String,
    val title: String,
    val fileName: String,
    val wordCount: Int = 0,
    val difficulty: WordDifficulty? = null,
    val difficultyLabel: String? = null
) {
    val fullTitle: String
        get() = if (difficultyLabel.isNullOrBlank()) title else "$title · $difficultyLabel"
}

data class Team(
    val id: Int,
    val name: String,
    val score: Int = 0
)

enum class GameScreen {
    Setup,
    Countdown,
    Play,
    RoundResults,
    Scoreboard
}

data class GameUiState(
    val screen: GameScreen = GameScreen.Setup,
    val categories: List<WordCategory> = emptyList(),
    val selectedCategoryIds: Set<String> = emptySet(),
    val targetScore: Int = 20,
    val teamCount: Int = 2,
    val teamNames: List<String> = emptyList(),
    val roundDurationSec: Int = 60,
    val teams: List<Team> = emptyList(),
    val currentTeamIndex: Int = 0,
    val countdown: Int = 3,
    val remainingMs: Long = 0L,
    val currentWord: String = "",
    val currentCategoryTitle: String = "",
    val roundGuessed: List<String> = emptyList(),
    val roundMissed: List<String> = emptyList(),
    val gameOver: Boolean = false,
    val winnerIndex: Int? = null,
    val wordsReady: Boolean = false,
    val language: AppLanguage = AppLanguage.English,
    val showHowToPlay: Boolean = false
) {
    val currentTeam: Team?
        get() = teams.getOrNull(currentTeamIndex)

    val roundPoints: Int
        get() = roundGuessed.size

    val winner: Team?
        get() = winnerIndex?.let { teams.getOrNull(it) }

    val strings: GameStrings
        get() = GameStrings.forLanguage(language)

    fun categoriesOnTab(tab: CategoryTab): List<WordCategory> =
        categories.filter { it.difficulty == tab.difficulty }

    fun selectedCountOnTab(tab: CategoryTab): Int =
        categories.count { it.difficulty == tab.difficulty && it.id in selectedCategoryIds }

    fun withTabSelection(tab: CategoryTab, selectAll: Boolean): Set<String> {
        val tabIds = categoriesOnTab(tab).map { it.id }.toSet()
        return if (selectAll) selectedCategoryIds + tabIds else selectedCategoryIds - tabIds
    }
}
