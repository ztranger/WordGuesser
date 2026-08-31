package com.hpg.wordguesser.ui.screens

import androidx.activity.compose.BackHandler
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hpg.wordguesser.game.GameStrings
import com.hpg.wordguesser.ui.components.PrimaryGameButton
import com.hpg.wordguesser.ui.theme.Cream
import com.hpg.wordguesser.ui.theme.CreamMuted
import com.hpg.wordguesser.ui.theme.GuessGreen
import com.hpg.wordguesser.ui.theme.Ink
import com.hpg.wordguesser.ui.theme.InkCard
import com.hpg.wordguesser.ui.theme.SkipRose
import com.hpg.wordguesser.ui.theme.Sunset
import com.hpg.wordguesser.ui.theme.Violet

@Composable
fun HowToPlayScreen(
    strings: GameStrings,
    onFinished: () -> Unit
) {
    var page by rememberSaveable { mutableIntStateOf(0) }
    BackHandler {
        if (page > 0) page = 0 else onFinished()
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Ink)
            .padding(horizontal = 24.dp, vertical = 20.dp)
    ) {
        Text(
            text = strings.howToPlay,
            style = MaterialTheme.typography.headlineLarge,
            color = Cream,
            fontWeight = FontWeight.Bold
        )
        Spacer(Modifier.height(28.dp))
        if (page == 0) {
            HowToPlayExplainPage(strings)
        } else {
            HowToPlayButtonsPage(strings)
        }
        Spacer(Modifier.weight(1f))
        PageDots(selected = page)
        Spacer(Modifier.height(20.dp))
        PrimaryGameButton(
            text = if (page == 0) strings.next else strings.howToPlayGotIt,
            onClick = {
                if (page == 0) page = 1 else onFinished()
            },
            containerColor = Violet,
            contentColor = Cream
        )
    }
}

@Composable
private fun HowToPlayExplainPage(strings: GameStrings) {
    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(24.dp))
                .background(InkCard)
                .padding(vertical = 28.dp, horizontal = 20.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "???",
                    color = Cream,
                    fontWeight = FontWeight.Bold,
                    fontSize = 48.sp
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    text = strings.appTitle.uppercase(),
                    style = MaterialTheme.typography.labelLarge,
                    color = Sunset,
                    letterSpacing = 1.2.sp
                )
            }
        }
        Spacer(Modifier.height(28.dp))
        Text(
            text = strings.howToPlayPage1Title,
            style = MaterialTheme.typography.headlineMedium,
            color = Cream,
            fontWeight = FontWeight.Bold
        )
        Spacer(Modifier.height(12.dp))
        Text(
            text = strings.howToPlayPage1Body,
            style = MaterialTheme.typography.bodyLarge,
            color = CreamMuted
        )
    }
}

@Composable
private fun HowToPlayButtonsPage(strings: GameStrings) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            RuleColorCard(
                title = strings.skip,
                hint = strings.skipHint,
                containerColor = SkipRose,
                contentColor = Cream,
                modifier = Modifier.weight(1f)
            )
            RuleColorCard(
                title = strings.guessed,
                hint = strings.guessedHint,
                containerColor = GuessGreen,
                contentColor = Ink,
                modifier = Modifier.weight(1f)
            )
        }
        Spacer(Modifier.height(28.dp))
        Text(
            text = strings.howToPlayPage2Title,
            style = MaterialTheme.typography.headlineMedium,
            color = Cream,
            fontWeight = FontWeight.Bold
        )
        Spacer(Modifier.height(12.dp))
        Text(
            text = strings.howToPlayPage2Body,
            style = MaterialTheme.typography.bodyLarge,
            color = CreamMuted
        )
    }
}

@Composable
private fun RuleColorCard(
    title: String,
    hint: String,
    containerColor: Color,
    contentColor: Color,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(22.dp))
            .background(containerColor)
            .padding(vertical = 22.dp, horizontal = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            color = contentColor,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(4.dp))
        Text(
            text = hint,
            color = contentColor.copy(alpha = 0.8f),
            fontSize = 13.sp,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun PageDots(selected: Int) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        repeat(2) { index ->
            Box(
                modifier = Modifier
                    .padding(horizontal = 5.dp)
                    .size(if (index == selected) 10.dp else 8.dp)
                    .clip(CircleShape)
                    .background(if (index == selected) Sunset else InkCard)
            )
        }
    }
}
