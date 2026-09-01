package com.hpg.wordguesser.game

import com.hpg.wordguesser.data.WordRepository
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
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
        assertEquals("Animals · Easy", strings.categoryTitle("animals", WordDifficulty.Easy))
        assertEquals("Easy", strings.difficultyLabel(WordDifficulty.Easy))
        assertEquals("More", strings.categoryTabLabel(CategoryTab.Other))
        assertEquals("Start", strings.start)
        assertEquals("Select all", strings.selectAllOnTab)
        assertEquals("Clear", strings.clearTab)
        assertEquals("Team 1", strings.teamName(1))
        assertEquals("+1 point this round", strings.roundPoints(1))
        assertEquals("+2 points this round", strings.roundPoints(2))
        assertEquals("First to 20 words", strings.playUntilWords(20))
        assertEquals("1 pack selected", strings.selectedPacksLabel(1))
        assertEquals("3 packs selected", strings.selectedPacksLabel(3))
        assertEquals("How to play", strings.howToPlay)
        assertEquals("Got it", strings.howToPlayGotIt)
        assertEquals("Done", strings.done)
        assertEquals("Another round so every team can play", strings.catchUpHint)
    }

    @Test
    fun russianPluralForms() {
        val strings = GameStrings.forLanguage(AppLanguage.Russian)
        assertEquals("+1 очко за раунд", strings.roundPoints(1))
        assertEquals("+2 очка за раунд", strings.roundPoints(2))
        assertEquals("+5 очков за раунд", strings.roundPoints(5))
        assertEquals("+21 очко за раунд", strings.roundPoints(21))
        assertEquals("До победы: 20 слов", strings.playUntilWords(20))
        assertEquals("Животные · Простые", strings.categoryTitle("animals", WordDifficulty.Easy))
        assertEquals("Сложные", strings.difficultyLabel(WordDifficulty.Hard))
        assertEquals("Города и страны", strings.topicTitle("cities"))
        assertEquals("Выбрано: 4", strings.selectedPacksLabel(4))
        assertEquals("Ещё", strings.categoryTabLabel(CategoryTab.Other))
        assertEquals("Как играть", strings.howToPlay)
        assertEquals("Понятно", strings.howToPlayGotIt)
        assertEquals("Готово", strings.done)
        assertEquals("Ещё раунд, чтобы все сыграли", strings.catchUpHint)
    }

    @Test
    fun everyRegisteredTopicHasLocalizedTitles() {
        val en = GameStrings.forLanguage(AppLanguage.English)
        val ru = GameStrings.forLanguage(AppLanguage.Russian)
        WordRepository.definitions.forEach { definition ->
            assertNotEquals(definition.topicId, en.topicTitle(definition.topicId))
            assertNotEquals(definition.topicId, ru.topicTitle(definition.topicId))
        }
        assertEquals(42, WordRepository.definitions.size)
        assertEquals(42, WordRepository.knownCategoryIds.size)
    }
}
