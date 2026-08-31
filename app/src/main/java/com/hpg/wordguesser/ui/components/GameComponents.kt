package com.hpg.wordguesser.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.hpg.wordguesser.game.WordDifficulty
import com.hpg.wordguesser.ui.theme.Cream
import com.hpg.wordguesser.ui.theme.GuessGreen
import com.hpg.wordguesser.ui.theme.Ink
import com.hpg.wordguesser.ui.theme.InkCard
import com.hpg.wordguesser.ui.theme.SkipRose
import com.hpg.wordguesser.ui.theme.Sunset

@Composable
fun OptionChip(
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val background by animateColorAsState(
        if (selected) Sunset else InkCard,
        label = "chipBg"
    )
    val content by animateColorAsState(
        if (selected) Ink else Cream,
        label = "chipFg"
    )
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(background)
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 10.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            color = content,
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
fun ChipRow(
    options: List<Pair<String, Boolean>>,
    onSelect: (Int) -> Unit
) {
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        options.forEachIndexed { index, (label, selected) ->
            OptionChip(label = label, selected = selected, onClick = { onSelect(index) })
        }
    }
}

@Composable
fun SectionTitle(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleLarge,
        color = Cream,
        modifier = Modifier.padding(bottom = 10.dp, top = 8.dp)
    )
}

@Composable
fun PrimaryGameButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    containerColor: Color = Sunset,
    contentColor: Color = Ink
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp),
        shape = RoundedCornerShape(18.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor,
            disabledContainerColor = InkCard,
            disabledContentColor = Cream.copy(alpha = 0.4f)
        ),
        contentPadding = PaddingValues(horizontal = 20.dp)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun CategoryCard(
    title: String,
    countLabel: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    difficultyLabel: String? = null,
    difficulty: WordDifficulty? = null
) {
    val border = if (selected) Sunset else Color.Transparent
    val background by animateColorAsState(
        if (selected) Sunset.copy(alpha = 0.18f) else InkCard,
        label = "catBg"
    )
    val difficultyColor = when (difficulty) {
        WordDifficulty.Easy -> GuessGreen
        WordDifficulty.Medium -> Sunset
        WordDifficulty.Hard -> SkipRose
        null -> Cream.copy(alpha = 0.7f)
    }
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(18.dp))
            .background(background)
            .border(2.dp, border, RoundedCornerShape(18.dp))
            .clickable(onClick = onClick)
            .padding(horizontal = 14.dp, vertical = 12.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            color = Cream,
            fontWeight = FontWeight.SemiBold,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
        if (!difficultyLabel.isNullOrBlank()) {
            Text(
                text = difficultyLabel,
                style = MaterialTheme.typography.labelLarge,
                color = difficultyColor,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
        Text(
            text = countLabel,
            style = MaterialTheme.typography.bodyMedium,
            color = Cream.copy(alpha = 0.7f),
            modifier = Modifier.padding(top = 2.dp)
        )
    }
}
