package com.hpg.wordguesser.data

import android.content.Context
import com.hpg.wordguesser.game.AppLanguage
import com.hpg.wordguesser.game.SetupSettings

class SetupPreferences(context: Context) {

    private val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun load(): SetupSettings? {
        if (!prefs.contains(KEY_TARGET_SCORE)) return null
        val names = prefs.getString(KEY_TEAM_NAMES, null)
            ?.split(NAME_SEPARATOR)
            ?.map { it.trim() }
            ?.filter { it.isNotEmpty() }
            .orEmpty()
        return SetupSettings(
            targetScore = prefs.getInt(KEY_TARGET_SCORE, 20),
            teamCount = prefs.getInt(KEY_TEAM_COUNT, 2),
            teamNames = names.ifEmpty { emptyList() },
            roundDurationSec = prefs.getInt(KEY_ROUND_DURATION, 60),
            selectedCategoryIds = prefs.getStringSet(KEY_CATEGORIES, emptySet())?.toSet().orEmpty()
        )
    }

    fun save(settings: SetupSettings) {
        prefs.edit()
            .putInt(KEY_TARGET_SCORE, settings.targetScore)
            .putInt(KEY_TEAM_COUNT, settings.teamCount)
            .putInt(KEY_ROUND_DURATION, settings.roundDurationSec)
            .putString(KEY_TEAM_NAMES, settings.teamNames.joinToString(NAME_SEPARATOR))
            .putStringSet(KEY_CATEGORIES, settings.selectedCategoryIds)
            .apply()
    }

    fun loadLanguageCode(): String? = prefs.getString(KEY_LANGUAGE, null)

    fun saveLanguage(language: AppLanguage) {
        prefs.edit().putString(KEY_LANGUAGE, language.code).apply()
    }

    companion object {
        private const val PREFS_NAME = "game_setup"
        private const val KEY_TARGET_SCORE = "target_score"
        private const val KEY_TEAM_COUNT = "team_count"
        private const val KEY_TEAM_NAMES = "team_names"
        private const val KEY_ROUND_DURATION = "round_duration"
        private const val KEY_CATEGORIES = "selected_categories"
        private const val KEY_LANGUAGE = "language"
        private const val NAME_SEPARATOR = "\u001F"
    }
}
