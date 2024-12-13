package com.sosauce.cutecalc.logic.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sosauce.cutecalc.MainActivity
import com.sosauce.cutecalc.history.HistoryViewModel
import com.sosauce.cutecalc.logic.CalcViewModel
import com.sosauce.cutecalc.screens.CalculatorUI
import com.sosauce.cutecalc.screens.HistoryScreen
import com.sosauce.cutecalc.screens.SettingsScreen


// Ta-da no need for navigation and serialization plugins for a simple app!

@Composable
fun Nav(historyViewModel: HistoryViewModel) {


    val activity = (LocalContext.current as MainActivity)
    var screenToDisplay by rememberSaveable { mutableStateOf(Screens.MAIN) }
    val viewModel = viewModel<CalcViewModel>()
    val historyState by historyViewModel.state.collectAsStateWithLifecycle()


    // Mimic back behavior from navigation
    BackHandler {
        if (screenToDisplay != Screens.MAIN) {
            screenToDisplay = Screens.MAIN
        } else {
            activity.moveTaskToBack(true)
        }
    }

    AnimatedContent(
        targetState = screenToDisplay,
        label = "",
        transitionSpec = { fadeIn() togetherWith fadeOut() }
    ) { screen ->
        when (screen) {
            Screens.MAIN -> {
                CalculatorUI(
                    viewModel = viewModel,
                    onNavigate = { screenToDisplay = it },
                    historyViewModel = historyViewModel,
                    historyState = historyState
                )
            }

            Screens.HISTORY -> {
                HistoryScreen(
                    state = historyState,
                    onNavigate = { screenToDisplay = it },
                    onEvents = historyViewModel::onEvent
                )
            }

            Screens.SETTINGS -> {
                SettingsScreen(
                    onNavigate = { screenToDisplay = it }
                )
            }
        }
    }

}