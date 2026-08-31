package com.hpg.wordguesser.data

import android.content.Context
import com.hpg.wordguesser.game.WordCategory
import java.io.File

class WordRepository(private val context: Context) {

    private val wordsDir: File
        get() = File(context.filesDir, WORDS_DIR)

    fun ensureWordFiles() {
        wordsDir.mkdirs()
        for (category in builtInCategories) {
            val dest = File(wordsDir, category.fileName)
            if (!dest.exists()) {
                copyFromAssets(category.fileName, dest)
            } else {
                mergeNewWordsFromAssets(category.fileName, dest)
            }
        }
    }

    fun loadCategories(): List<WordCategory> =
        builtInCategories.map { category ->
            category.copy(wordCount = loadWords(category.id).size)
        }

    fun loadWords(categoryId: String): List<String> {
        val category = builtInCategories.firstOrNull { it.id == categoryId } ?: return emptyList()
        val file = File(wordsDir, category.fileName)
        val lines = if (file.exists()) {
            file.readLines()
        } else {
            readAssetLines(category.fileName)
        }
        return lines.map { it.trim() }
            .filter { it.isNotEmpty() && !it.startsWith("#") }
            .distinct()
    }

    fun loadWordsForCategories(categoryIds: Set<String>): List<Pair<String, String>> {
        val titles = builtInCategories.associate { it.id to it.title }
        return categoryIds.flatMap { id ->
            val title = titles[id] ?: id
            loadWords(id).map { word -> word to title }
        }
    }

    private fun copyFromAssets(fileName: String, dest: File) {
        context.assets.open("$ASSETS_DIR/$fileName").use { input ->
            dest.outputStream().use { output -> input.copyTo(output) }
        }
    }

    private fun mergeNewWordsFromAssets(fileName: String, dest: File) {
        val existing = dest.readLines()
            .map { it.trim() }
            .filter { it.isNotEmpty() && !it.startsWith("#") }
            .toMutableSet()
        val newcomers = readAssetLines(fileName)
            .map { it.trim() }
            .filter { it.isNotEmpty() && !it.startsWith("#") && it !in existing }
        if (newcomers.isNotEmpty()) {
            dest.appendText(newcomers.joinToString(prefix = "\n", separator = "\n", postfix = "\n"))
        }
    }

    private fun readAssetLines(fileName: String): List<String> =
        context.assets.open("$ASSETS_DIR/$fileName").bufferedReader().use { it.readLines() }

    companion object {
        const val WORDS_DIR = "words"
        const val ASSETS_DIR = "words"

        val builtInCategories = listOf(
            WordCategory("animals", "Животные", "animals.txt"),
            WordCategory("food", "Еда", "food.txt"),
            WordCategory("professions", "Профессии", "professions.txt"),
            WordCategory("sports", "Спорт", "sports.txt"),
            WordCategory("movies", "Кино и сериалы", "movies.txt"),
            WordCategory("objects", "Предметы", "objects.txt"),
            WordCategory("nature", "Природа", "nature.txt"),
            WordCategory("actions", "Действия", "actions.txt")
        )
    }
}
