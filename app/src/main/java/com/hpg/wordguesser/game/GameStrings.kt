package com.hpg.wordguesser.game

data class GameStrings(
    val appTitle: String,
    val appSubtitle: String,
    val playUntil: String,
    val teamCount: String,
    val roundDuration: String,
    val categories: String,
    val categoriesHint: String,
    val start: String,
    val selectCategories: String,
    val language: String,
    val languageRussian: String,
    val languageEnglish: String,
    val settings: String,
    val close: String,
    val getReady: String,
    val roundStartsSoon: String,
    val skip: String,
    val skipHint: String,
    val guessed: String,
    val guessedHint: String,
    val roundOver: String,
    val guessedTitle: String,
    val missedTitle: String,
    val next: String,
    val gameOver: String,
    val score: String,
    val newGame: String,
    val startRound: String,
    val giveUp: String,
    val winner: String,
    val nextRound: String,
    private val teamNameTemplate: String,
    private val teamNameLabelTemplate: String,
    private val guessedInRoundTemplate: String,
    private val nowPlayingTemplate: String,
    private val winnerPointsTemplate: String,
    private val duration30: String,
    private val duration60: String,
    private val duration90: String,
    private val durationFallback: String,
    private val categoryTitles: Map<String, String>,
    private val difficultyEasy: String,
    private val difficultyMedium: String,
    private val difficultyHard: String,
    private val categoryTabOther: String,
    private val pluralWords: (Int) -> String,
    private val pluralPoints: (Int) -> String,
    private val roundPointsTemplate: (String) -> String,
    private val playUntilTemplate: (String) -> String,
    private val wordCountTemplate: (String) -> String,
    private val selectedPacksTemplate: (Int) -> String
) {
    fun teamName(number: Int): String = teamNameTemplate.format(number)

    fun teamNameLabel(number: Int): String = teamNameLabelTemplate.format(number)

    fun guessedInRound(count: Int): String = guessedInRoundTemplate.format(count)

    fun nowPlaying(name: String): String = nowPlayingTemplate.format(name)

    fun winnerPoints(count: Int): String = winnerPointsTemplate.format(count, pluralPoints(count))

    fun roundPoints(count: Int): String = roundPointsTemplate("+$count ${pluralPoints(count)}")

    fun playUntilWords(count: Int): String = playUntilTemplate("$count ${pluralWords(count)}")

    fun wordCountLabel(count: Int): String = wordCountTemplate("$count ${pluralWords(count)}")

    fun selectedPacksLabel(count: Int): String = selectedPacksTemplate(count)

    fun durationLabel(seconds: Int): String = when (seconds) {
        30 -> duration30
        60 -> duration60
        90 -> duration90
        else -> durationFallback.format(seconds)
    }

    fun topicTitle(topicId: String): String = categoryTitles[topicId] ?: topicId

    fun difficultyLabel(difficulty: WordDifficulty): String = when (difficulty) {
        WordDifficulty.Easy -> difficultyEasy
        WordDifficulty.Medium -> difficultyMedium
        WordDifficulty.Hard -> difficultyHard
    }

    fun categoryTabLabel(tab: CategoryTab): String = when (tab) {
        CategoryTab.Easy -> difficultyEasy
        CategoryTab.Medium -> difficultyMedium
        CategoryTab.Hard -> difficultyHard
        CategoryTab.Other -> categoryTabOther
    }

    fun categoryTitle(topicId: String, difficulty: WordDifficulty? = null): String {
        val topic = topicTitle(topicId)
        return if (difficulty == null) topic else "$topic · ${difficultyLabel(difficulty)}"
    }

    companion object {
        fun forLanguage(language: AppLanguage): GameStrings = when (language) {
            AppLanguage.Russian -> Russian
            AppLanguage.English -> English
        }

        private val Russian = GameStrings(
            appTitle = "Угадай слово",
            appSubtitle = "Объясняйте слова своей команде на время",
            playUntil = "Играем до скольких слов",
            teamCount = "Сколько команд",
            roundDuration = "Длительность раунда",
            categories = "Категории слов",
            categoriesHint = "Простые, средние и сложные — на своих вкладках. Темы без уровня — во вкладке «Ещё»",
            start = "Старт",
            selectCategories = "Выберите категории",
            language = "Язык",
            languageRussian = "Русский",
            languageEnglish = "English",
            settings = "Настройки",
            close = "Закрыть",
            getReady = "Приготовьтесь объяснить слово",
            roundStartsSoon = "Раунд начнётся через несколько секунд",
            skip = "Пропустить",
            skipHint = "без очка",
            guessed = "Угадано",
            guessedHint = "+1 очко",
            roundOver = "Раунд окончен",
            guessedTitle = "Угадано",
            missedTitle = "Не угадано",
            next = "Далее",
            gameOver = "Игра окончена",
            score = "Счёт",
            newGame = "Новая игра",
            startRound = "Старт раунда",
            giveUp = "Сдаться и начать заново",
            winner = "Победитель",
            nextRound = "следующий раунд",
            teamNameTemplate = "Команда %d",
            teamNameLabelTemplate = "Название команды %d",
            guessedInRoundTemplate = "Угадано в раунде: %d",
            nowPlayingTemplate = "Сейчас ходит: %s",
            winnerPointsTemplate = "%d %s",
            duration30 = "30 сек",
            duration60 = "1 мин",
            duration90 = "1,5 мин",
            durationFallback = "%d сек",
            categoryTitles = mapOf(
                "animals" to "Животные",
                "food" to "Еда",
                "professions" to "Профессии",
                "sports" to "Спорт",
                "movies" to "Кино и сериалы",
                "objects" to "Предметы",
                "nature" to "Природа",
                "actions" to "Действия",
                "cities" to "Города и страны",
                "music" to "Музыка",
                "people" to "Знаменитости",
                "transport" to "Транспорт",
                "clothes" to "Одежда",
                "fairy_tales" to "Сказки",
                "technology" to "Технологии",
                "holidays" to "Праздники",
                "school" to "Школа",
                "space" to "Космос",
                "hobbies" to "Хобби",
                "emotions" to "Эмоции"
            ),
            difficultyEasy = "Простые",
            difficultyMedium = "Средние",
            difficultyHard = "Сложные",
            categoryTabOther = "Ещё",
            pluralWords = { n -> russianPlural(n, "слово", "слова", "слов") },
            pluralPoints = { n -> russianPlural(n, "очко", "очка", "очков") },
            roundPointsTemplate = { value -> "$value за раунд" },
            playUntilTemplate = { value -> "До победы: $value" },
            wordCountTemplate = { it },
            selectedPacksTemplate = { n -> "Выбрано: $n" }
        )

        private val English = GameStrings(
            appTitle = "Guess the Word",
            appSubtitle = "Explain words to your team against the clock",
            playUntil = "Play until",
            teamCount = "Number of teams",
            roundDuration = "Round duration",
            categories = "Word categories",
            categoriesHint = "Easy, medium and hard have their own tabs. Packs without a level are under More",
            start = "Start",
            selectCategories = "Select categories",
            language = "Language",
            languageRussian = "Русский",
            languageEnglish = "English",
            settings = "Settings",
            close = "Close",
            getReady = "Get ready to explain the word",
            roundStartsSoon = "The round starts in a few seconds",
            skip = "Skip",
            skipHint = "no point",
            guessed = "Guessed",
            guessedHint = "+1 point",
            roundOver = "Round over",
            guessedTitle = "Guessed",
            missedTitle = "Skipped",
            next = "Next",
            gameOver = "Game over",
            score = "Score",
            newGame = "New game",
            startRound = "Start round",
            giveUp = "Quit and start over",
            winner = "Winner",
            nextRound = "next round",
            teamNameTemplate = "Team %d",
            teamNameLabelTemplate = "Team %d name",
            guessedInRoundTemplate = "Guessed this round: %d",
            nowPlayingTemplate = "Up now: %s",
            winnerPointsTemplate = "%d %s",
            duration30 = "30 sec",
            duration60 = "1 min",
            duration90 = "1.5 min",
            durationFallback = "%d sec",
            categoryTitles = mapOf(
                "animals" to "Animals",
                "food" to "Food",
                "professions" to "Jobs",
                "sports" to "Sports",
                "movies" to "Movies & TV",
                "objects" to "Objects",
                "nature" to "Nature",
                "actions" to "Actions",
                "cities" to "Cities & countries",
                "music" to "Music",
                "people" to "Famous people",
                "transport" to "Transport",
                "clothes" to "Clothes",
                "fairy_tales" to "Fairy tales",
                "technology" to "Technology",
                "holidays" to "Holidays",
                "school" to "School",
                "space" to "Space",
                "hobbies" to "Hobbies",
                "emotions" to "Emotions"
            ),
            difficultyEasy = "Easy",
            difficultyMedium = "Medium",
            difficultyHard = "Hard",
            categoryTabOther = "More",
            pluralWords = { n -> if (n == 1) "word" else "words" },
            pluralPoints = { n -> if (n == 1) "point" else "points" },
            roundPointsTemplate = { value -> "$value this round" },
            playUntilTemplate = { value -> "First to $value" },
            wordCountTemplate = { it },
            selectedPacksTemplate = { n -> if (n == 1) "1 pack selected" else "$n packs selected" }
        )

        private fun russianPlural(n: Int, one: String, few: String, many: String): String {
            val mod10 = n % 10
            val mod100 = n % 100
            return when {
                mod100 in 11..14 -> many
                mod10 == 1 -> one
                mod10 in 2..4 -> few
                else -> many
            }
        }
    }
}
