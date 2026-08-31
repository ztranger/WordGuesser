package com.hpg.wordguesser.ui.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hpg.wordguesser.game.GameUiState
import com.hpg.wordguesser.ui.theme.Cream
import com.hpg.wordguesser.ui.theme.CreamMuted
import com.hpg.wordguesser.ui.theme.Ink
import com.hpg.wordguesser.ui.theme.Sunset

@Composable
fun CountdownScreen(state: GameUiState) {
    val scale by animateFloatAsState(
        targetValue = 1f + (state.countdown % 2) * 0.08f,
        animationSpec = tween(250),
        label = "countdownScale"
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Ink),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = state.currentTeam?.name ?: "",
                style = MaterialTheme.typography.headlineMedium,
                color = Sunset,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 24.dp)
            )
            Text(
                text = state.strings.getReady,
                style = MaterialTheme.typography.bodyLarge,
                color = CreamMuted,
                modifier = Modifier.padding(top = 8.dp, bottom = 28.dp)
            )
            Text(
                text = state.countdown.toString(),
                color = Cream,
                fontSize = 120.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.scale(scale)
            )
            Spacer(Modifier.height(12.dp))
            Text(
                text = state.strings.roundStartsSoon,
                style = MaterialTheme.typography.bodyMedium,
                color = CreamMuted
            )
        }
    }
}
