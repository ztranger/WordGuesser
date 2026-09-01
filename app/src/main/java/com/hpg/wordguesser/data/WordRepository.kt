package com.hpg.wordguesser.data

import android.content.Context
import com.hpg.wordguesser.game.AppLanguage
import com.hpg.wordguesser.game.GameStrings
import com.hpg.wordguesser.game.WordCategory
import com.hpg.wordguesser.game.WordDifficulty
import java.io.File

data class CategoryDefinition(
    val topicId: String,
    val difficulty: WordDifficulty? = null
) {
    val id: String = if (difficulty == null) topicId else "${topicId}_${difficulty.fileSuffix}"
    val fileName: String = "$id.txt"
}

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
                applyWordFixes(dest)
                mergeNewWordsFromAssets(language, category.fileName, dest)
            }
        }
    }

    fun loadCategories(language: AppLanguage): List<WordCategory> {
        val strings = GameStrings.forLanguage(language)
        return definitions.map { category ->
            WordCategory(
                id = category.id,
                title = strings.topicTitle(category.topicId),
                fileName = category.fileName,
                wordCount = loadWords(language, category.id).size,
                difficulty = category.difficulty,
                difficultyLabel = category.difficulty?.let { strings.difficultyLabel(it) }
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
            val category = definitions.firstOrNull { it.id == id }
            val title = if (category == null) {
                id
            } else {
                strings.categoryTitle(category.topicId, category.difficulty)
            }
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

    private fun applyWordFixes(file: File) {
        if (!file.exists()) return
        val original = file.readText()
        var updated = original
        for ((from, to) in WORD_FIXES) {
            updated = updated.replace(from, to)
        }
        if (updated != original) {
            file.writeText(updated)
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

        private val WORD_FIXES = mapOf(
            "тестрал" to "фестрал",
            "Ровена Когтевран" to "Кандида Когтевран"
        )

        val definitions: List<CategoryDefinition> = buildList {
            addAll(split("animals"))
            addAll(split("food"))
            addAll(split("professions"))
            addAll(split("sports"))
            addAll(split("movies"))
            addAll(split("objects"))
            addAll(split("nature"))
            addAll(split("actions"))
            addAll(split("cities"))
            addAll(split("music"))
            addAll(split("people"))
            addAll(split("harry_potter"))
            add(CategoryDefinition("transport"))
            add(CategoryDefinition("clothes"))
            add(CategoryDefinition("fairy_tales"))
            add(CategoryDefinition("technology"))
            add(CategoryDefinition("holidays"))
            add(CategoryDefinition("school"))
            add(CategoryDefinition("space"))
            add(CategoryDefinition("hobbies"))
            add(CategoryDefinition("emotions"))
        }

        val knownCategoryIds: Set<String> = definitions.map { it.id }.toSet()

        val defaultCategoryIds: Set<String> = definitions
            .filter { it.difficulty == null || it.difficulty == WordDifficulty.Easy }
            .map { it.id }
            .toSet()

        private fun split(topicId: String): List<CategoryDefinition> = listOf(
            CategoryDefinition(topicId, WordDifficulty.Easy),
            CategoryDefinition(topicId, WordDifficulty.Medium),
            CategoryDefinition(topicId, WordDifficulty.Hard)
        )
    }
}
