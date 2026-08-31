package com.hpg.wordguesser.game

import com.hpg.wordguesser.data.WordRepository
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class SetupSettingsTest {

    private val known = setOf("animals", "food", "sports")

    @Test
    fun firstLaunchSelectsEveryKnownCategory() {
        val setup = SetupSettings.sanitize(
            raw = SetupSettings(selectedCategoryIds = known),
            knownCategoryIds = known,
            language = AppLanguage.Russian
        )
        assertEquals(known, setup.selectedCategoryIds)
    }

    @Test
    fun restoresValidSettingsAndTeamNames() {
        val setup = SetupSettings.sanitize(
            raw = SetupSettings(
                targetScore = 50,
                teamCount = 3,
                teamNames = listOf("Лисы", "Волки", "Совы"),
                roundDurationSec = 90,
                selectedCategoryIds = setOf("food", "sports")
            ),
            knownCategoryIds = known,
            language = AppLanguage.Russian
        )
        assertEquals(50, setup.targetScore)
        assertEquals(3, setup.teamCount)
        assertEquals(listOf("Лисы", "Волки", "Совы"), setup.teamNames)
        assertEquals(90, setup.roundDurationSec)
        assertEquals(setOf("food", "sports"), setup.selectedCategoryIds)
    }

    @Test
    fun dropsUnknownValuesAndFillsMissingTeamNames() {
        val setup = SetupSettings.sanitize(
            raw = SetupSettings(
                targetScore = 999,
                teamCount = 8,
                teamNames = listOf("Лисы"),
                roundDurationSec = 12,
                selectedCategoryIds = setOf("food", "removed")
            ),
            knownCategoryIds = known,
            language = AppLanguage.Russian
        )
        assertEquals(20, setup.targetScore)
        assertEquals(2, setup.teamCount)
        assertEquals(listOf("Лисы", "Команда 2"), setup.teamNames)
        assertEquals(60, setup.roundDurationSec)
        assertEquals(setOf("food"), setup.selectedCategoryIds)
    }

    @Test
    fun emptySavedCategoriesFallBackToDefaults() {
        val defaults = setOf("animals")
        val setup = SetupSettings.sanitize(
            raw = SetupSettings(selectedCategoryIds = emptySet()),
            knownCategoryIds = known,
            language = AppLanguage.English,
            defaultCategoryIds = defaults
        )
        assertEquals(defaults, setup.selectedCategoryIds)
    }

    @Test
    fun oldCategoryIdsExpandIntoDifficultyPacks() {
        val known = setOf(
            "animals_easy",
            "animals_medium",
            "animals_hard",
            "food_easy",
            "transport"
        )
        val setup = SetupSettings.sanitize(
            raw = SetupSettings(selectedCategoryIds = setOf("animals", "food")),
            knownCategoryIds = known,
            language = AppLanguage.English
        )
        assertEquals(
            setOf("animals_easy", "animals_medium", "animals_hard", "food_easy"),
            setup.selectedCategoryIds
        )
    }

    @Test
    fun firstLaunchDefaultsToEasyAndOtherPacks() {
        val defaults = WordRepository.defaultCategoryIds
        val setup = SetupSettings.sanitize(
            raw = SetupSettings(selectedCategoryIds = emptySet()),
            knownCategoryIds = WordRepository.knownCategoryIds,
            language = AppLanguage.English,
            defaultCategoryIds = defaults
        )
        assertEquals(defaults, setup.selectedCategoryIds)
        assertTrue("animals_easy" in setup.selectedCategoryIds)
        assertTrue("transport" in setup.selectedCategoryIds)
        assertFalse("animals_medium" in setup.selectedCategoryIds)
        assertFalse("animals_hard" in setup.selectedCategoryIds)
    }
}
