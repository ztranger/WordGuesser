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

    fun isCatchUpPending(
        scores: List<Int>,
        targetScore: Int,
        nextTeamIndex: Int
    ): Boolean {
        if (scores.size < 2) return false
        if (nextTeamIndex !in 1 until scores.size) return false
        return hasReachedTarget(scores, targetScore)
    }

    fun defaultTeamNames(count: Int, language: AppLanguage): List<String> =
        (1..count).map { GameStrings.forLanguage(language).teamName(it) }

    fun isGeneratedTeamName(name: String, index: Int): Boolean {
        val number = index + 1
        return AppLanguage.entries.any { language ->
            name == GameStrings.forLanguage(language).teamName(number)
        }
    }

    fun adjustTeamNames(
        current: List<String>,
        count: Int,
        language: AppLanguage
    ): List<String> {
        val defaults = defaultTeamNames(count, language)
        return defaults.mapIndexed { index, fallback ->
            val existing = current.getOrNull(index)
            when {
                existing.isNullOrBlank() -> fallback
                isGeneratedTeamName(existing, index) -> fallback
                else -> existing
            }
        }
    }
}

class WordDeck(words: List<Pair<String, String>>) {
    private val pool: List<Pair<String, String>> = words.distinctBy { it.first.lowercase() }
    private val remaining = ArrayDeque<Pair<String, String>>()
    private var lastDrawn: Pair<String, String>? = null

    init {
        refill(avoid = null)
    }

    val isEmpty: Boolean get() = pool.isEmpty()

    fun next(): Pair<String, String> {
        if (pool.isEmpty()) return "—" to ""
        if (remaining.isEmpty()) refill(avoid = lastDrawn)
        val word = remaining.removeFirst()
        lastDrawn = word
        return word
    }

    private fun refill(avoid: Pair<String, String>?) {
        remaining.clear()
        val shuffled = pool.shuffled().toMutableList()
        if (
            avoid != null &&
            shuffled.size > 1 &&
            shuffled.first().first.equals(avoid.first, ignoreCase = true)
        ) {
            shuffled.add(shuffled.removeAt(0))
        }
        remaining.addAll(shuffled)
    }
}
