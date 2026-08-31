package com.hpg.wordguesser.game

data class WordCategory(
    val id: String,
    val title: String,
    val fileName: String,
    val wordCount: Int = 0
)

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
    val language: AppLanguage = AppLanguage.English
) {
    val currentTeam: Team?
        get() = teams.getOrNull(currentTeamIndex)

    val roundPoints: Int
        get() = roundGuessed.size

    val winner: Team?
        get() = winnerIndex?.let { teams.getOrNull(it) }

    val strings: GameStrings
        get() = GameStrings.forLanguage(language)
}
