package com.hpg.wordguesser.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.hpg.wordguesser.game.GameUiState
import com.hpg.wordguesser.ui.components.PrimaryGameButton
import com.hpg.wordguesser.ui.theme.Cream
import com.hpg.wordguesser.ui.theme.CreamMuted
import com.hpg.wordguesser.ui.theme.GuessGreen
import com.hpg.wordguesser.ui.theme.Ink
import com.hpg.wordguesser.ui.theme.InkCard
import com.hpg.wordguesser.ui.theme.SkipRose
import com.hpg.wordguesser.ui.theme.Sunset

@Composable
fun RoundResultsScreen(
    state: GameUiState,
    onNext: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Ink)
            .padding(horizontal = 20.dp, vertical = 16.dp)
    ) {
        Text(
            text = "Раунд окончен",
            style = MaterialTheme.typography.headlineMedium,
            color = Cream
        )
        Text(
            text = state.currentTeam?.name ?: "",
            style = MaterialTheme.typography.titleLarge,
            color = Sunset,
            modifier = Modifier.padding(top = 4.dp)
        )
        Text(
            text = "+${state.roundPoints} ${pointsWord(state.roundPoints)} за раунд",
            style = MaterialTheme.typography.headlineLarge,
            color = GuessGreen,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 16.dp, bottom = 20.dp)
        )

        Row(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            WordColumn(
                title = "Угадано",
                words = state.roundGuessed,
                accent = GuessGreen,
                modifier = Modifier.weight(1f)
            )
            WordColumn(
                title = "Не угадано",
                words = state.roundMissed,
                accent = SkipRose,
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(Modifier.height(16.dp))
        PrimaryGameButton(text = "Далее", onClick = onNext)
    }
}

@Composable
private fun WordColumn(
    title: String,
    words: List<String>,
    accent: androidx.compose.ui.graphics.Color,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(InkCard, RoundedCornerShape(20.dp))
            .padding(14.dp)
    ) {
        Text(
            text = "$title · ${words.size}",
            color = accent,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(Modifier.height(8.dp))
        if (words.isEmpty()) {
            Text("—", color = CreamMuted)
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(words) { word ->
                    Text(text = word, color = Cream, style = MaterialTheme.typography.bodyLarge)
                }
            }
        }
    }
}

private fun pointsWord(count: Int): String {
    val mod10 = count % 10
    val mod100 = count % 100
    return when {
        mod100 in 11..14 -> "очков"
        mod10 == 1 -> "очко"
        mod10 in 2..4 -> "очка"
        else -> "очков"
    }
}
