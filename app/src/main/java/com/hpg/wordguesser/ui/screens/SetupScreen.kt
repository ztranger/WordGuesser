package com.hpg.wordguesser.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.hpg.wordguesser.game.GameRules
import com.hpg.wordguesser.game.GameUiState
import com.hpg.wordguesser.ui.components.CategoryCard
import com.hpg.wordguesser.ui.components.ChipRow
import com.hpg.wordguesser.ui.components.PrimaryGameButton
import com.hpg.wordguesser.ui.components.SectionTitle
import com.hpg.wordguesser.ui.theme.Cream
import com.hpg.wordguesser.ui.theme.CreamMuted
import com.hpg.wordguesser.ui.theme.Ink
import com.hpg.wordguesser.ui.theme.InkCard
import com.hpg.wordguesser.ui.theme.Sunset
import com.hpg.wordguesser.ui.theme.Violet

@Composable
fun SetupScreen(
    state: GameUiState,
    onTargetScore: (Int) -> Unit,
    onTeamCount: (Int) -> Unit,
    onTeamName: (Int, String) -> Unit,
    onDuration: (Int) -> Unit,
    onToggleCategory: (String) -> Unit,
    onStart: () -> Unit
) {
    val canStart = state.wordsReady && state.selectedCategoryIds.isNotEmpty()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Ink)
            .imePadding()
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            item(span = { GridItemSpan(2) }) {
                Column {
                    Text(
                        text = "Угадай слово",
                        style = MaterialTheme.typography.headlineLarge,
                        color = Cream,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Объясняйте слова своей команде на время",
                        style = MaterialTheme.typography.bodyLarge,
                        color = CreamMuted,
                        modifier = Modifier.padding(top = 6.dp, bottom = 8.dp)
                    )
                    SectionTitle("Играем до скольких слов")
                    ChipRow(
                        options = GameRules.targetScoreOptions.map { score ->
                            "$score" to (state.targetScore == score)
                        },
                        onSelect = { onTargetScore(GameRules.targetScoreOptions[it]) }
                    )
                    SectionTitle("Сколько команд")
                    ChipRow(
                        options = GameRules.teamCountOptions.map { count ->
                            "$count" to (state.teamCount == count)
                        },
                        onSelect = { onTeamCount(GameRules.teamCountOptions[it]) }
                    )
                    Spacer(Modifier.height(8.dp))
                    state.teamNames.forEachIndexed { index, name ->
                        OutlinedTextField(
                            value = name,
                            onValueChange = { onTeamName(index, it) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 8.dp),
                            singleLine = true,
                            label = { Text("Название команды ${index + 1}") },
                            shape = RoundedCornerShape(16.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Sunset,
                                unfocusedBorderColor = InkCard,
                                focusedLabelColor = Sunset,
                                unfocusedLabelColor = CreamMuted,
                                focusedTextColor = Cream,
                                unfocusedTextColor = Cream,
                                cursorColor = Sunset
                            )
                        )
                    }
                    SectionTitle("Длительность раунда")
                    ChipRow(
                        options = GameRules.roundDurationOptions.map { seconds ->
                            durationLabel(seconds) to (state.roundDurationSec == seconds)
                        },
                        onSelect = { onDuration(GameRules.roundDurationOptions[it]) }
                    )
                    SectionTitle("Категории слов")
                    Text(
                        text = "Слова хранятся в файлах и пополняются при обновлении игры",
                        style = MaterialTheme.typography.bodyMedium,
                        color = CreamMuted,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                }
            }
            items(state.categories, key = { it.id }) { category ->
                CategoryCard(
                    title = category.title,
                    wordCount = category.wordCount,
                    selected = category.id in state.selectedCategoryIds,
                    onClick = { onToggleCategory(category.id) },
                    modifier = Modifier.fillMaxWidth()
                )
            }
            item(span = { GridItemSpan(2) }) {
                Spacer(Modifier.height(88.dp))
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 16.dp)
        ) {
            PrimaryGameButton(
                text = if (canStart) "Старт" else "Выберите категории",
                onClick = onStart,
                enabled = canStart,
                containerColor = Violet,
                contentColor = Cream
            )
        }
    }
}

private fun durationLabel(seconds: Int): String = when (seconds) {
    30 -> "30 сек"
    60 -> "1 мин"
    90 -> "1,5 мин"
    else -> "$seconds сек"
}
