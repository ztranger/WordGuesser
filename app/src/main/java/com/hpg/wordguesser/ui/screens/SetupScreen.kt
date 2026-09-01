package com.hpg.wordguesser.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.hpg.wordguesser.game.AppLanguage
import com.hpg.wordguesser.game.CategoryTab
import com.hpg.wordguesser.game.GameRules
import com.hpg.wordguesser.game.GameUiState
import com.hpg.wordguesser.ui.components.ChipRow
import com.hpg.wordguesser.ui.components.PrimaryGameButton
import com.hpg.wordguesser.ui.components.SectionTitle
import com.hpg.wordguesser.ui.components.SettingsButton
import com.hpg.wordguesser.ui.components.SettingsDialog
import com.hpg.wordguesser.ui.components.tabAccent
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
    onSetTabCategories: (CategoryTab, Boolean) -> Unit,
    onLanguage: (AppLanguage) -> Unit,
    onShowHowToPlay: () -> Unit,
    onStart: () -> Unit
) {
    val strings = state.strings
    val canStart = state.wordsReady && state.selectedCategoryIds.isNotEmpty()
    var settingsOpen by rememberSaveable { mutableStateOf(false) }
    var categoryPickerOpen by rememberSaveable { mutableStateOf(false) }
    if (settingsOpen) {
        SettingsDialog(
            language = state.language,
            strings = strings,
            onLanguage = onLanguage,
            onShowHowToPlay = {
                settingsOpen = false
                onShowHowToPlay()
            },
            onDismiss = { settingsOpen = false }
        )
    }
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Ink)
                .imePadding()
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 20.dp, vertical = 12.dp)
            ) {
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
                Spacer(Modifier.height(16.dp))
                CategoryPickerButton(
                    title = strings.categories,
                    subtitle = if (state.selectedCategoryIds.isEmpty()) {
                        strings.selectCategories
                    } else {
                        strings.selectedPacksLabel(state.selectedCategoryIds.size)
                    },
                    empty = state.selectedCategoryIds.isEmpty(),
                    onClick = { categoryPickerOpen = true }
                )
                Spacer(Modifier.height(16.dp))
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 16.dp)
            ) {
                PrimaryGameButton(
                    text = if (canStart) strings.start else strings.selectCategories,
                    onClick = {
                        if (canStart) onStart() else categoryPickerOpen = true
                    },
                    enabled = state.wordsReady,
                    containerColor = Violet,
                    contentColor = Cream
                )
            }
        }
        if (categoryPickerOpen) {
            CategoryPickerScreen(
                state = state,
                onToggleCategory = onToggleCategory,
                onSetTabCategories = onSetTabCategories,
                onDismiss = { categoryPickerOpen = false }
            )
        }
    }
}

@Composable
private fun CategoryPickerButton(
    title: String,
    subtitle: String,
    empty: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .background(InkCard)
            .then(
                if (empty) Modifier.border(1.dp, Sunset, RoundedCornerShape(18.dp))
                else Modifier
            )
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                color = Cream,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodyMedium,
                color = if (empty) Sunset else CreamMuted,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CategoryTab.entries.take(3).forEach { tab ->
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .clip(CircleShape)
                        .background(tab.tabAccent())
                )
            }
            Text(
                text = "›",
                color = CreamMuted,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(start = 4.dp)
            )
        }
    }
}
