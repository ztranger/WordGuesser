package com.hpg.wordguesser.game

import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.Locale

class AppLanguageTest {

    @Test
    fun deviceLocaleRussianSelectsRussian() {
        assertEquals(AppLanguage.Russian, AppLanguage.fromDeviceLocale(Locale.forLanguageTag("ru")))
        assertEquals(AppLanguage.Russian, AppLanguage.fromDeviceLocale(Locale.forLanguageTag("ru-RU")))
    }

    @Test
    fun otherLocalesSelectEnglish() {
        assertEquals(AppLanguage.English, AppLanguage.fromDeviceLocale(Locale.US))
        assertEquals(AppLanguage.English, AppLanguage.fromDeviceLocale(Locale.GERMANY))
        assertEquals(AppLanguage.English, AppLanguage.fromDeviceLocale(Locale.forLanguageTag("uk")))
    }

    @Test
    fun savedLanguageWinsOverDeviceLocale() {
        assertEquals(
            AppLanguage.English,
            AppLanguage.resolve("en", Locale.forLanguageTag("ru-RU"))
        )
        assertEquals(
            AppLanguage.Russian,
            AppLanguage.resolve("ru", Locale.US)
        )
    }

    @Test
    fun missingSavedLanguageUsesDevice() {
        assertEquals(AppLanguage.Russian, AppLanguage.resolve(null, Locale.forLanguageTag("ru-RU")))
        assertEquals(AppLanguage.English, AppLanguage.resolve(null, Locale.US))
    }
}

class GameStringsTest {

    @Test
    fun englishCopyAndCategories() {
        val strings = GameStrings.forLanguage(AppLanguage.English)
        assertEquals("Animals", strings.categoryTitle("animals"))
        assertEquals("Start", strings.start)
        assertEquals("Team 1", strings.teamName(1))
        assertEquals("+1 point this round", strings.roundPoints(1))
        assertEquals("+2 points this round", strings.roundPoints(2))
        assertEquals("First to 20 words", strings.playUntilWords(20))
    }

    @Test
    fun russianPluralForms() {
        val strings = GameStrings.forLanguage(AppLanguage.Russian)
        assertEquals("+1 очко за раунд", strings.roundPoints(1))
        assertEquals("+2 очка за раунд", strings.roundPoints(2))
        assertEquals("+5 очков за раунд", strings.roundPoints(5))
        assertEquals("+21 очко за раунд", strings.roundPoints(21))
        assertEquals("До победы: 20 слов", strings.playUntilWords(20))
    }
}
