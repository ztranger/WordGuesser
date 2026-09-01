package com.hpg.wordguesser.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.hpg.wordguesser.game.CategoryTab
import com.hpg.wordguesser.game.GameUiState
import com.hpg.wordguesser.ui.components.CategoryCard
import com.hpg.wordguesser.ui.components.PrimaryGameButton
import com.hpg.wordguesser.ui.components.tabAccent
import com.hpg.wordguesser.ui.theme.Cream
import com.hpg.wordguesser.ui.theme.CreamMuted
import com.hpg.wordguesser.ui.theme.Ink
import com.hpg.wordguesser.ui.theme.Violet

private val categoryTabs = CategoryTab.entries

@Composable
fun CategoryPickerScreen(
    state: GameUiState,
    onToggleCategory: (String) -> Unit,
    onSetTabCategories: (CategoryTab, Boolean) -> Unit,
    onDismiss: () -> Unit
) {
    val strings = state.strings
    var categoryTab by rememberSaveable { mutableIntStateOf(0) }
    val selectedTab = categoryTabs[categoryTab.coerceIn(categoryTabs.indices)]
    val visibleCategories = state.categoriesOnTab(selectedTab)
    BackHandler(onBack = onDismiss)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Ink)
    ) {
        Column(modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 16.dp)) {
            Text(
                text = strings.categories,
                style = MaterialTheme.typography.headlineLarge,
                color = Cream,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = strings.selectedPacksLabel(state.selectedCategoryIds.size),
                style = MaterialTheme.typography.bodyLarge,
                color = CreamMuted,
                modifier = Modifier.padding(top = 6.dp, bottom = 8.dp)
            )
            Text(
                text = strings.categoriesHint,
                style = MaterialTheme.typography.bodyMedium,
                color = CreamMuted,
                modifier = Modifier.padding(bottom = 14.dp)
            )
            DifficultyTabRow(
                selectedIndex = categoryTab,
                selectedCountOnTab = { tab -> state.selectedCountOnTab(tab) },
                label = { strings.categoryTabLabel(it) },
                onSelect = { categoryTab = it }
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp, bottom = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = strings.selectAllOnTab,
                    style = MaterialTheme.typography.labelLarge,
                    color = selectedTab.tabAccent(),
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .clickable { onSetTabCategories(selectedTab, true) }
                        .padding(horizontal = 8.dp, vertical = 6.dp)
                )
                Text(
                    text = strings.clearTab,
                    style = MaterialTheme.typography.labelLarge,
                    color = selectedTab.tabAccent(),
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .clickable { onSetTabCategories(selectedTab, false) }
                        .padding(horizontal = 8.dp, vertical = 6.dp)
                )
            }
        }
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(visibleCategories, key = { it.id }) { category ->
                CategoryCard(
                    title = category.title,
                    countLabel = strings.wordCountLabel(category.wordCount),
                    selected = category.id in state.selectedCategoryIds,
                    onClick = { onToggleCategory(category.id) },
                    difficulty = category.difficulty,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
        PrimaryGameButton(
            text = strings.done,
            onClick = onDismiss,
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 16.dp),
            containerColor = Violet,
            contentColor = Cream
        )
    }
}

@Composable
private fun DifficultyTabRow(
    selectedIndex: Int,
    selectedCountOnTab: (CategoryTab) -> Int,
    label: (CategoryTab) -> String,
    onSelect: (Int) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        categoryTabs.forEachIndexed { index, tab ->
            val accent = tab.tabAccent()
            val selected = index == selectedIndex
            val selectedOnTab = selectedCountOnTab(tab)
            val background = if (selected) accent else accent.copy(alpha = 0.22f)
            val content = if (selected) Ink else accent
            Column(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(14.dp))
                    .background(background)
                    .clickable { onSelect(index) }
                    .padding(vertical = 10.dp, horizontal = 4.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = label(tab),
                    color = content,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = if (selectedOnTab > 0) "$selectedOnTab" else " ",
                    style = MaterialTheme.typography.bodyMedium,
                    color = if (selectedOnTab > 0) content else Color.Transparent,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}
