package com.hpg.wordguesser.game

object GameRules {
    val targetScoreOptions = listOf(10, 15, 20, 30, 40, 50, 75, 100)
    val teamCountOptions = (2..6).toList()
    val roundDurationOptions = listOf(30, 60, 90)

    fun nextTeamIndex(currentIndex: Int, teamCount: Int): Int =
        (currentIndex + 1) % teamCount

    fun isCircleComplete(lastPlayedIndex: Int, teamCount: Int): Boolean =
        lastPlayedIndex == teamCount - 1

    fun hasReachedTarget(scores: List<Int>, targetScore: Int): Boolean =
        scores.any { it >= targetScore }

    fun uniqueLeaderIndex(scores: List<Int>): Int? {
        if (scores.isEmpty()) return null
        val max = scores.max()
        val leaders = scores.withIndex().filter { it.value == max }
        return leaders.singleOrNull()?.index
    }

    /**
     * Game ends only after the current circle of rounds is finished,
     * at least one team reached the target, and there is a unique leader.
     * Teams that have not yet played in this circle still get their round.
     */
    fun shouldFinishGame(
        scores: List<Int>,
        targetScore: Int,
        lastPlayedIndex: Int
    ): Boolean {
        if (!hasReachedTarget(scores, targetScore)) return false
        if (!isCircleComplete(lastPlayedIndex, scores.size)) return false
        return uniqueLeaderIndex(scores) != null
    }

    fun defaultTeamNames(count: Int): List<String> =
        (1..count).map { "Команда $it" }

    fun adjustTeamNames(current: List<String>, count: Int): List<String> {
        val defaults = defaultTeamNames(count)
        return defaults.mapIndexed { index, fallback ->
            current.getOrNull(index)?.takeIf { it.isNotBlank() } ?: fallback
        }
    }
}

class WordDeck(words: List<Pair<String, String>>) {
    private val source: List<Pair<String, String>> = words
    private val remaining = ArrayDeque<Pair<String, String>>()

    init {
        reshuffle()
    }

    val isEmpty: Boolean get() = source.isEmpty()

    fun next(): Pair<String, String> {
        if (source.isEmpty()) return "—" to ""
        if (remaining.isEmpty()) reshuffle()
        return remaining.removeFirst()
    }

    private fun reshuffle() {
        remaining.clear()
        remaining.addAll(source.shuffled())
    }
}
