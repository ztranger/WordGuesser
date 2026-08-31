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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.hpg.wordguesser.game.AppLanguage
import com.hpg.wordguesser.game.GameRules
import com.hpg.wordguesser.game.GameUiState
import com.hpg.wordguesser.ui.components.CategoryCard
import com.hpg.wordguesser.ui.components.ChipRow
import com.hpg.wordguesser.ui.components.PrimaryGameButton
import com.hpg.wordguesser.ui.components.SectionTitle
import com.hpg.wordguesser.ui.components.SettingsButton
import com.hpg.wordguesser.ui.components.SettingsDialog
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
    onLanguage: (AppLanguage) -> Unit,
    onStart: () -> Unit
) {
    val strings = state.strings
    val canStart = state.wordsReady && state.selectedCategoryIds.isNotEmpty()
    var settingsOpen by rememberSaveable { mutableStateOf(false) }
    if (settingsOpen) {
        SettingsDialog(
            language = state.language,
            strings = strings,
            onLanguage = onLanguage,
            onDismiss = { settingsOpen = false }
        )
    }
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
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = strings.appTitle,
                            style = MaterialTheme.typography.headlineLarge,
                            color = Cream,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.weight(1f)
                        )
                        SettingsButton(
                            contentDescription = strings.settings,
                            onClick = { settingsOpen = true }
                        )
                    }
                    Text(
                        text = strings.appSubtitle,
                        style = MaterialTheme.typography.bodyLarge,
                        color = CreamMuted,
                        modifier = Modifier.padding(top = 6.dp, bottom = 8.dp)
                    )
                    SectionTitle(strings.playUntil)
                    ChipRow(
                        options = GameRules.targetScoreOptions.map { score ->
                            "$score" to (state.targetScore == score)
                        },
                        onSelect = { onTargetScore(GameRules.targetScoreOptions[it]) }
                    )
                    SectionTitle(strings.teamCount)
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
                            label = { Text(strings.teamNameLabel(index + 1)) },
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
                    SectionTitle(strings.roundDuration)
                    ChipRow(
                        options = GameRules.roundDurationOptions.map { seconds ->
                            strings.durationLabel(seconds) to (state.roundDurationSec == seconds)
                        },
                        onSelect = { onDuration(GameRules.roundDurationOptions[it]) }
                    )
                    SectionTitle(strings.categories)
                    Text(
                        text = strings.categoriesHint,
                        style = MaterialTheme.typography.bodyMedium,
                        color = CreamMuted,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                }
            }
            items(state.categories, key = { it.id }) { category ->
                CategoryCard(
                    title = category.title,
                    countLabel = strings.wordCountLabel(category.wordCount),
                    selected = category.id in state.selectedCategoryIds,
                    onClick = { onToggleCategory(category.id) },
                    difficultyLabel = category.difficultyLabel,
                    difficulty = category.difficulty,
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
                text = if (canStart) strings.start else strings.selectCategories,
                onClick = onStart,
                enabled = canStart,
                containerColor = Violet,
                contentColor = Cream
            )
        }
    }
}
