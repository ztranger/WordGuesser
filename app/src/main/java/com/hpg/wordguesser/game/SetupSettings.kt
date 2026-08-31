package com.hpg.wordguesser.game

data class SetupSettings(
    val targetScore: Int = 20,
    val teamCount: Int = 2,
    val teamNames: List<String> = emptyList(),
    val roundDurationSec: Int = 60,
    val selectedCategoryIds: Set<String> = emptySet()
) {
    companion object {
        fun sanitize(
            raw: SetupSettings,
            knownCategoryIds: Set<String>,
            language: AppLanguage
        ): SetupSettings {
            val teamCount = raw.teamCount.takeIf { it in GameRules.teamCountOptions } ?: 2
            val targetScore = raw.targetScore.takeIf { it in GameRules.targetScoreOptions } ?: 20
            val roundDurationSec =
                raw.roundDurationSec.takeIf { it in GameRules.roundDurationOptions } ?: 60
            val selected = raw.selectedCategoryIds
                .intersect(knownCategoryIds)
                .ifEmpty { knownCategoryIds }
            return SetupSettings(
                targetScore = targetScore,
                teamCount = teamCount,
                teamNames = GameRules.adjustTeamNames(raw.teamNames, teamCount, language),
                roundDurationSec = roundDurationSec,
                selectedCategoryIds = selected
            )
        }
    }
}
