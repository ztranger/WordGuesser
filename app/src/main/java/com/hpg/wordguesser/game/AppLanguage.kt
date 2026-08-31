package com.hpg.wordguesser.game

import java.util.Locale

enum class AppLanguage(val code: String) {
    English("en"),
    Russian("ru");

    companion object {
        fun fromCode(code: String?): AppLanguage? =
            entries.firstOrNull { it.code.equals(code, ignoreCase = true) }

        fun fromDeviceLocale(locale: Locale = Locale.getDefault()): AppLanguage =
            if (locale.language.equals(Russian.code, ignoreCase = true)) Russian else English

        fun resolve(savedCode: String?, deviceLocale: Locale = Locale.getDefault()): AppLanguage =
            fromCode(savedCode) ?: fromDeviceLocale(deviceLocale)
    }
}
