package com.hpg.wordguesser.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.hpg.wordguesser.R
import com.hpg.wordguesser.game.AppLanguage
import com.hpg.wordguesser.game.GameStrings
import com.hpg.wordguesser.ui.theme.Cream
import com.hpg.wordguesser.ui.theme.CreamMuted
import com.hpg.wordguesser.ui.theme.Ink
import com.hpg.wordguesser.ui.theme.InkCard
import com.hpg.wordguesser.ui.theme.Sunset
import com.hpg.wordguesser.ui.theme.Violet

@Composable
fun SettingsButton(
    contentDescription: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = onClick,
        modifier = modifier
            .clip(CircleShape)
            .background(InkCard)
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_settings),
            contentDescription = contentDescription,
            tint = Cream
        )
    }
}

@Composable
fun SettingsDialog(
    language: AppLanguage,
    strings: GameStrings,
    onLanguage: (AppLanguage) -> Unit,
    onShowHowToPlay: () -> Unit,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(24.dp))
                .background(Ink)
                .border(1.dp, InkCard, RoundedCornerShape(24.dp))
                .padding(20.dp)
        ) {
            Text(
                text = strings.settings,
                style = MaterialTheme.typography.headlineMedium,
                color = Cream,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = strings.language,
                style = MaterialTheme.typography.titleLarge,
                color = CreamMuted,
                modifier = Modifier.padding(top = 20.dp, bottom = 12.dp)
            )
            LanguageOption(
                label = strings.languageRussian,
                selected = language == AppLanguage.Russian,
                onClick = { onLanguage(AppLanguage.Russian) }
            )
            Spacer(Modifier.height(8.dp))
            LanguageOption(
                label = strings.languageEnglish,
                selected = language == AppLanguage.English,
                onClick = { onLanguage(AppLanguage.English) }
            )
            Spacer(Modifier.height(20.dp))
            PrimaryGameButton(
                text = strings.howToPlay,
                onClick = onShowHowToPlay,
                containerColor = Violet,
                contentColor = Cream
            )
            Spacer(Modifier.height(10.dp))
            PrimaryGameButton(text = strings.close, onClick = onDismiss)
        }
    }
}

@Composable
private fun LanguageOption(
    label: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(if (selected) Sunset.copy(alpha = 0.18f) else InkCard)
            .then(
                if (selected) Modifier.border(1.dp, Sunset, RoundedCornerShape(16.dp))
                else Modifier
            )
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            color = Cream,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.weight(1f)
        )
        if (selected) {
            Spacer(Modifier.width(8.dp))
            Text("✓", color = Sunset, style = MaterialTheme.typography.titleLarge)
        }
    }
}
