package com.hpg.wordguesser.game

import org.junit.Assert.assertEquals
import org.junit.Test

class GameUiStateTest {

    private val animalsEasy = WordCategory("animals_easy", "Animals", "animals_easy.txt", difficulty = WordDifficulty.Easy)
    private val animalsMedium = WordCategory("animals_medium", "Animals", "animals_medium.txt", difficulty = WordDifficulty.Medium)
    private val animalsHard = WordCategory("animals_hard", "Animals", "animals_hard.txt", difficulty = WordDifficulty.Hard)
    private val transport = WordCategory("transport", "Transport", "transport.txt")

    private val state = GameUiState(
        categories = listOf(animalsEasy, animalsMedium, animalsHard, transport),
        selectedCategoryIds = setOf("animals_easy", "animals_hard", "transport")
    )

    @Test
    fun eachTabShowsOnlyItsOwnPacks() {
        assertEquals(listOf("animals_easy"), state.categoriesOnTab(CategoryTab.Easy).map { it.id })
        assertEquals(listOf("animals_medium"), state.categoriesOnTab(CategoryTab.Medium).map { it.id })
        assertEquals(listOf("animals_hard"), state.categoriesOnTab(CategoryTab.Hard).map { it.id })
        assertEquals(listOf("transport"), state.categoriesOnTab(CategoryTab.Other).map { it.id })
    }

    @Test
    fun tabBadgeCountsSelectionsOnThatTab() {
        assertEquals(1, state.selectedCountOnTab(CategoryTab.Easy))
        assertEquals(0, state.selectedCountOnTab(CategoryTab.Medium))
        assertEquals(1, state.selectedCountOnTab(CategoryTab.Hard))
        assertEquals(1, state.selectedCountOnTab(CategoryTab.Other))
    }
}
