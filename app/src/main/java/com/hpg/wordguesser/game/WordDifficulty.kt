package com.hpg.wordguesser.game

enum class WordDifficulty(val fileSuffix: String) {
    Easy("easy"),
    Medium("medium"),
    Hard("hard")
}

enum class CategoryTab {
    Easy,
    Medium,
    Hard,
    Other;

    val difficulty: WordDifficulty?
        get() = when (this) {
            Easy -> WordDifficulty.Easy
            Medium -> WordDifficulty.Medium
            Hard -> WordDifficulty.Hard
            Other -> null
        }
}
