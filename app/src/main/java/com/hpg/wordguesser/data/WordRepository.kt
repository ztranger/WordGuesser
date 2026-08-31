package com.hpg.wordguesser.data

import android.content.Context
import com.hpg.wordguesser.game.AppLanguage
import com.hpg.wordguesser.game.GameStrings
import com.hpg.wordguesser.game.WordCategory
import java.io.File

data class CategoryDefinition(
    val id: String,
    val fileName: String
)

class WordRepository(private val context: Context) {

    fun ensureWordFiles(language: AppLanguage) {
        migrateLegacyRussianFiles()
        val destDir = languageDir(language)
        destDir.mkdirs()
        for (category in definitions) {
            val dest = File(destDir, category.fileName)
            if (!dest.exists()) {
                copyFromAssets(language, category.fileName, dest)
            } else {
                mergeNewWordsFromAssets(language, category.fileName, dest)
            }
        }
    }

    fun loadCategories(language: AppLanguage): List<WordCategory> {
        val strings = GameStrings.forLanguage(language)
        return definitions.map { category ->
            WordCategory(
                id = category.id,
                title = strings.categoryTitle(category.id),
                fileName = category.fileName,
                wordCount = loadWords(language, category.id).size
            )
        }
    }

    fun loadWords(language: AppLanguage, categoryId: String): List<String> {
        val category = definitions.firstOrNull { it.id == categoryId } ?: return emptyList()
        val file = File(languageDir(language), category.fileName)
        val lines = if (file.exists()) {
            file.readLines()
        } else {
            readAssetLines(language, category.fileName)
        }
        return lines.map { it.trim() }
            .filter { it.isNotEmpty() && !it.startsWith("#") }
            .distinct()
    }

    fun loadWordsForCategories(
        language: AppLanguage,
        categoryIds: Set<String>
    ): List<Pair<String, String>> {
        val strings = GameStrings.forLanguage(language)
        return categoryIds.flatMap { id ->
            val title = strings.categoryTitle(id)
            loadWords(language, id).map { word -> word to title }
        }
    }

    private fun languageDir(language: AppLanguage): File =
        File(File(context.filesDir, WORDS_DIR), language.code)

    private fun copyFromAssets(language: AppLanguage, fileName: String, dest: File) {
        context.assets.open(assetPath(language, fileName)).use { input ->
            dest.outputStream().use { output -> input.copyTo(output) }
        }
    }

    private fun mergeNewWordsFromAssets(language: AppLanguage, fileName: String, dest: File) {
        val existing = dest.readLines()
            .map { it.trim() }
            .filter { it.isNotEmpty() && !it.startsWith("#") }
            .toMutableSet()
        val newcomers = readAssetLines(language, fileName)
            .map { it.trim() }
            .filter { it.isNotEmpty() && !it.startsWith("#") && it !in existing }
        if (newcomers.isNotEmpty()) {
            dest.appendText(newcomers.joinToString(prefix = "\n", separator = "\n", postfix = "\n"))
        }
    }

    private fun readAssetLines(language: AppLanguage, fileName: String): List<String> =
        context.assets.open(assetPath(language, fileName)).bufferedReader().use { it.readLines() }

    private fun assetPath(language: AppLanguage, fileName: String): String =
        "$ASSETS_DIR/${language.code}/$fileName"

    private fun migrateLegacyRussianFiles() {
        val root = File(context.filesDir, WORDS_DIR)
        val ruDir = File(root, AppLanguage.Russian.code)
        if (ruDir.exists()) return
        val legacyFiles = definitions.map { File(root, it.fileName) }.filter { it.exists() }
        if (legacyFiles.isEmpty()) return
        ruDir.mkdirs()
        legacyFiles.forEach { file ->
            file.copyTo(File(ruDir, file.name), overwrite = false)
            file.delete()
        }
    }

    companion object {
        const val WORDS_DIR = "words"
        const val ASSETS_DIR = "words"

        val definitions = listOf(
            CategoryDefinition("animals", "animals.txt"),
            CategoryDefinition("food", "food.txt"),
            CategoryDefinition("professions", "professions.txt"),
            CategoryDefinition("sports", "sports.txt"),
            CategoryDefinition("movies", "movies.txt"),
            CategoryDefinition("objects", "objects.txt"),
            CategoryDefinition("nature", "nature.txt"),
            CategoryDefinition("actions", "actions.txt")
        )

        val knownCategoryIds: Set<String> = definitions.map { it.id }.toSet()
    }
}
