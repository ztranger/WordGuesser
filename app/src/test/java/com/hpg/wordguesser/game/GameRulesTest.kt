package com.hpg.wordguesser.game

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class GameRulesTest {

    @Test
    fun nextTeamWrapsAroundTheCircle() {
        assertEquals(1, GameRules.nextTeamIndex(0, 3))
        assertEquals(2, GameRules.nextTeamIndex(1, 3))
        assertEquals(0, GameRules.nextTeamIndex(2, 3))
    }

    @Test
    fun gameContinuesIfLaterTeamsHaveNotPlayedThisCircle() {
        val scores = listOf(20, 5, 4)
        assertFalse(GameRules.shouldFinishGame(scores, targetScore = 20, lastPlayedIndex = 0))
        assertFalse(GameRules.shouldFinishGame(scores, targetScore = 20, lastPlayedIndex = 1))
    }

    @Test
    fun catchUpWhenSomeoneHitTargetButCircleIsOpen() {
        assertTrue(GameRules.isCatchUpPending(listOf(20, 5, 4), targetScore = 20, nextTeamIndex = 1))
        assertTrue(GameRules.isCatchUpPending(listOf(22, 21, 0), targetScore = 20, nextTeamIndex = 2))
    }

    @Test
    fun noCatchUpAtStartOfANewCircle() {
        assertFalse(GameRules.isCatchUpPending(listOf(20, 5, 4), targetScore = 20, nextTeamIndex = 0))
        assertFalse(GameRules.isCatchUpPending(listOf(12, 9, 11), targetScore = 20, nextTeamIndex = 1))
    }

    @Test
    fun gameEndsAfterCircleIfThereIsAUniqueLeaderAboveTarget() {
        val scores = listOf(22, 18, 10)
        assertTrue(GameRules.shouldFinishGame(scores, targetScore = 20, lastPlayedIndex = 2))
        assertEquals(0, GameRules.uniqueLeaderIndex(scores))
    }

    @Test
    fun lastTeamReachingTargetCanEndTheGame() {
        val scores = listOf(10, 12, 25)
        assertTrue(GameRules.shouldFinishGame(scores, targetScore = 20, lastPlayedIndex = 2))
        assertEquals(2, GameRules.uniqueLeaderIndex(scores))
    }

    @Test
    fun tiedLeadersKeepPlayingAnotherCircle() {
        val scores = listOf(22, 22, 10)
        assertFalse(GameRules.shouldFinishGame(scores, targetScore = 20, lastPlayedIndex = 2))
        assertNull(GameRules.uniqueLeaderIndex(scores))
    }

    @Test
    fun nobodyAtTargetMeansTheGameGoesOn() {
        val scores = listOf(12, 9, 11)
        assertFalse(GameRules.shouldFinishGame(scores, targetScore = 20, lastPlayedIndex = 2))
    }

    @Test
    fun scoresMayExceedTheTarget() {
        val scores = listOf(27, 14)
        assertTrue(GameRules.shouldFinishGame(scores, targetScore = 20, lastPlayedIndex = 1))
        assertEquals(0, GameRules.uniqueLeaderIndex(scores))
    }

    @Test
    fun wordDeckCyclesWithoutRepeatingUntilExhausted() {
        val deck = WordDeck(listOf("а" to "к", "б" to "к", "в" to "к"))
        val firstPass = listOf(deck.next().first, deck.next().first, deck.next().first).toSet()
        assertEquals(setOf("а", "б", "в"), firstPass)
        val continued = deck.next().first
        assertTrue(continued in firstPass)
    }

    @Test
    fun adjustTeamNamesKeepsExistingAndFillsTheRest() {
        val names = GameRules.adjustTeamNames(listOf("Лисы", "Волки"), 4, AppLanguage.Russian)
        assertEquals(listOf("Лисы", "Волки", "Команда 3", "Команда 4"), names)
    }

    @Test
    fun generatedTeamNamesFollowTheSelectedLanguage() {
        val names = GameRules.adjustTeamNames(listOf("Команда 1", "Foxes"), 2, AppLanguage.English)
        assertEquals(listOf("Team 1", "Foxes"), names)
    }
}
