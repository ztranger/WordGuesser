package com.hpg.wordguesser.ui.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hpg.wordguesser.game.GameUiState
import com.hpg.wordguesser.ui.theme.Cream
import com.hpg.wordguesser.ui.theme.CreamMuted
import com.hpg.wordguesser.ui.theme.GuessGreen
import com.hpg.wordguesser.ui.theme.GuessGreenDeep
import com.hpg.wordguesser.ui.theme.Ink
import com.hpg.wordguesser.ui.theme.InkCard
import com.hpg.wordguesser.ui.theme.SkipRose
import com.hpg.wordguesser.ui.theme.SkipRoseDeep
import com.hpg.wordguesser.ui.theme.Sunset
import com.hpg.wordguesser.ui.theme.TimerUrgent
import kotlin.math.ceil

@Composable
fun PlayScreen(
    state: GameUiState,
    onGuessed: () -> Unit,
    onSkipped: () -> Unit
) {
    val haptic = LocalHapticFeedback.current
    val totalMs = (state.roundDurationSec * 1000L).coerceAtLeast(1L)
    val progress = (state.remainingMs / totalMs.toFloat()).coerceIn(0f, 1f)
    val secondsLeft = ceil(state.remainingMs / 1000.0).toInt().coerceAtLeast(0)
    val urgent = secondsLeft <= 10
    val timerColor by animateColorAsState(
        if (urgent) TimerUrgent else Sunset,
        label = "timerColor"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Ink)
            .padding(horizontal = 20.dp, vertical = 16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = state.currentTeam?.name ?: "",
                    style = MaterialTheme.typography.titleLarge,
                    color = Cream
                )
                Text(
                    text = state.strings.guessedInRound(state.roundGuessed.size),
                    style = MaterialTheme.typography.bodyMedium,
                    color = CreamMuted
                )
            }
            Box(contentAlignment = Alignment.Center, modifier = Modifier.size(92.dp)) {
                CircularProgressIndicator(
                    progress = { progress },
                    modifier = Modifier.fillMaxSize(),
                    color = timerColor,
                    strokeWidth = 7.dp,
                    trackColor = InkCard,
                    strokeCap = ProgressIndicatorDefaults.CircularDeterminateStrokeCap
                )
                Text(
                    text = formatTime(secondsLeft),
                    color = timerColor,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp
                )
            }
        }

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                if (state.currentCategoryTitle.isNotBlank()) {
                    Text(
                        text = state.currentCategoryTitle.uppercase(),
                        style = MaterialTheme.typography.labelLarge,
                        color = Sunset,
                        letterSpacing = 1.6.sp
                    )
                    Spacer(Modifier.height(16.dp))
                }
                Text(
                    text = state.currentWord,
                    color = Cream,
                    fontWeight = FontWeight.Bold,
                    fontSize = 40.sp,
                    lineHeight = 46.sp,
                    textAlign = TextAlign.Center
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Button(
                onClick = {
                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                    onSkipped()
                },
                modifier = Modifier
                    .weight(1f)
                    .height(92.dp),
                shape = RoundedCornerShape(22.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = SkipRose,
                    contentColor = Cream
                )
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .clip(CircleShape)
                            .background(SkipRoseDeep)
                    )
                    Spacer(Modifier.height(6.dp))
                    Text(state.strings.skip, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    Text(state.strings.skipHint, fontSize = 12.sp, color = Cream.copy(alpha = 0.85f))
                }
            }
            Button(
                onClick = {
                    haptic.performHapticFeedback(HapticFeedbackType.Confirm)
                    onGuessed()
                },
                modifier = Modifier
                    .weight(1f)
                    .height(92.dp),
                shape = RoundedCornerShape(22.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = GuessGreen,
                    contentColor = Ink
                )
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .clip(CircleShape)
                            .background(GuessGreenDeep)
                    )
                    Spacer(Modifier.height(6.dp))
                    Text(state.strings.guessed, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    Text(state.strings.guessedHint, fontSize = 12.sp, color = Ink.copy(alpha = 0.75f))
                }
            }
        }
    }
}

private fun formatTime(totalSeconds: Int): String {
    val minutes = totalSeconds / 60
    val seconds = totalSeconds % 60
    return "%d:%02d".format(minutes, seconds)
}
