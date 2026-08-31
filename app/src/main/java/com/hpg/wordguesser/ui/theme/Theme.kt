package com.hpg.wordguesser.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val GameColorScheme = darkColorScheme(
    primary = Sunset,
    onPrimary = Ink,
    secondary = Violet,
    onSecondary = Cream,
    tertiary = GuessGreen,
    onTertiary = Ink,
    background = Ink,
    onBackground = Cream,
    surface = InkElevated,
    onSurface = Cream,
    surfaceVariant = InkCard,
    onSurfaceVariant = CreamMuted,
    error = SkipRose,
    onError = Cream
)

@Composable
fun WordGuesserTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = GameColorScheme,
        typography = Typography,
        content = content
    )
}
