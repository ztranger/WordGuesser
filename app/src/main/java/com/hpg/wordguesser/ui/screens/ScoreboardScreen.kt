package com.hpg.wordguesser.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hpg.wordguesser.game.GameStrings
import com.hpg.wordguesser.game.GameUiState
import com.hpg.wordguesser.game.Team
import com.hpg.wordguesser.ui.components.PrimaryGameButton
import com.hpg.wordguesser.ui.theme.Cream
import com.hpg.wordguesser.ui.theme.CreamMuted
import com.hpg.wordguesser.ui.theme.Gold
import com.hpg.wordguesser.ui.theme.GuessGreen
import com.hpg.wordguesser.ui.theme.Ink
import com.hpg.wordguesser.ui.theme.InkCard
import com.hpg.wordguesser.ui.theme.Sunset
import com.hpg.wordguesser.ui.theme.Violet

@Composable
fun ScoreboardScreen(
    state: GameUiState,
    onStartNextRound: () -> Unit,
    onNewGame: () -> Unit
) {
    val ranked = state.teams.sortedByDescending { it.score }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Ink)
            .padding(horizontal = 20.dp, vertical = 16.dp)
    ) {
        Text(
            text = if (state.gameOver) state.strings.gameOver else state.strings.score,
            style = MaterialTheme.typography.headlineLarge,
            color = Cream
        )
        Text(
            text = state.strings.playUntilWords(state.targetScore),
            style = MaterialTheme.typography.bodyLarge,
            color = CreamMuted,
            modifier = Modifier.padding(top = 4.dp, bottom = 16.dp)
        )

        if (state.gameOver && state.winner != null) {
            WinnerBanner(team = state.winner!!, strings = state.strings)
            Spacer(Modifier.height(16.dp))
        } else if (!state.gameOver) {
            Text(
                text = state.strings.nowPlaying(state.currentTeam?.name ?: ""),
                style = MaterialTheme.typography.titleLarge,
                color = Sunset,
                modifier = Modifier.padding(bottom = 12.dp)
            )
        }

        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            itemsIndexed(ranked, key = { _, team -> team.id }) { index, team ->
                val isNext = !state.gameOver && team.id == state.currentTeamIndex
                val isWinner = state.gameOver && team.id == state.winnerIndex
                TeamScoreRow(
                    place = index + 1,
                    team = team,
                    highlighted = isNext || isWinner,
                    isNextUp = isNext,
                    nextRoundLabel = state.strings.nextRound
                )
            }
        }

        Spacer(Modifier.height(16.dp))
        if (state.gameOver) {
            PrimaryGameButton(text = state.strings.newGame, onClick = onNewGame)
        } else {
            PrimaryGameButton(
                text = state.strings.startRound,
                onClick = onStartNextRound,
                containerColor = Violet,
                contentColor = Cream
            )
            Spacer(Modifier.height(10.dp))
            PrimaryGameButton(
                text = state.strings.giveUp,
                onClick = onNewGame,
                containerColor = InkCard,
                contentColor = Cream
            )
        }
    }
}

@Composable
private fun WinnerBanner(team: Team, strings: GameStrings) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(Gold.copy(alpha = 0.16f))
            .border(1.dp, Gold, RoundedCornerShape(24.dp))
            .padding(18.dp)
    ) {
        Text(strings.winner, color = Gold, style = MaterialTheme.typography.labelLarge)
        Text(
            text = team.name,
            color = Cream,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 4.dp)
        )
        Text(
            text = strings.winnerPoints(team.score),
            color = GuessGreen,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}

@Composable
private fun TeamScoreRow(
    place: Int,
    team: Team,
    highlighted: Boolean,
    isNextUp: Boolean,
    nextRoundLabel: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .background(if (highlighted) Sunset.copy(alpha = 0.16f) else InkCard)
            .then(
                if (highlighted) Modifier.border(1.dp, Sunset, RoundedCornerShape(18.dp))
                else Modifier
            )
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(if (place == 1) Gold else Ink),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = place.toString(),
                color = if (place == 1) Ink else Cream,
                fontWeight = FontWeight.Bold
            )
        }
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 12.dp)
        ) {
            Text(team.name, color = Cream, style = MaterialTheme.typography.titleLarge)
            if (isNextUp) {
                Text(nextRoundLabel, color = Sunset, fontSize = 12.sp)
            }
        }
        Text(
            text = team.score.toString(),
            color = Cream,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )
    }
}
