package com.hpg.wordguesser

import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hpg.wordguesser.game.GameScreen
import com.hpg.wordguesser.game.GameViewModel
import com.hpg.wordguesser.ui.screens.CountdownScreen
import com.hpg.wordguesser.ui.screens.HowToPlayScreen
import com.hpg.wordguesser.ui.screens.PlayScreen
import com.hpg.wordguesser.ui.screens.RoundResultsScreen
import com.hpg.wordguesser.ui.screens.ScoreboardScreen
import com.hpg.wordguesser.ui.screens.SetupScreen
import com.hpg.wordguesser.ui.theme.Ink

@Composable
fun WordGuesserApp(viewModel: GameViewModel = viewModel()) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val keepAwake = state.screen == GameScreen.Countdown || state.screen == GameScreen.Play
    KeepScreenOn(keepAwake)

    when (state.screen) {
        GameScreen.Countdown, GameScreen.Play -> BackHandler { }
        GameScreen.RoundResults -> BackHandler { viewModel.onResultsNext() }
        GameScreen.Scoreboard -> BackHandler { viewModel.newGame() }
        GameScreen.Setup -> Unit
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Ink)
            .systemBarsPadding()
    ) {
        when (state.screen) {
            GameScreen.Setup -> SetupScreen(
                state = state,
                onTargetScore = viewModel::setTargetScore,
                onTeamCount = viewModel::setTeamCount,
                onTeamName = viewModel::setTeamName,
                onDuration = viewModel::setRoundDuration,
                onToggleCategory = viewModel::toggleCategory,
                onSetTabCategories = viewModel::setTabCategoriesSelected,
                onLanguage = viewModel::setLanguage,
                onShowHowToPlay = viewModel::showHowToPlay,
                onStart = viewModel::startGame
            )
            GameScreen.Countdown -> CountdownScreen(state)
            GameScreen.Play -> PlayScreen(
                state = state,
                onGuessed = viewModel::onGuessed,
                onSkipped = viewModel::onSkipped
            )
            GameScreen.RoundResults -> RoundResultsScreen(
                state = state,
                onNext = viewModel::onResultsNext
            )
            GameScreen.Scoreboard -> ScoreboardScreen(
                state = state,
                onStartNextRound = viewModel::startNextRound,
                onNewGame = viewModel::newGame
            )
        }
        if (state.showHowToPlay) {
            HowToPlayScreen(
                strings = state.strings,
                onFinished = viewModel::dismissHowToPlay
            )
        }
    }
}

@Composable
private fun KeepScreenOn(enabled: Boolean) {
    val view = LocalView.current
    DisposableEffect(enabled) {
        val window = (view.context as? ComponentActivity)?.window
        if (enabled) {
            window?.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        }
        onDispose {
            window?.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        }
    }
}
