package com.sosauce.cutecalc.logic.navigation

import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalActivity
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sosauce.cutecalc.history.HistoryViewModel
import com.sosauce.cutecalc.logic.CalcAction
import com.sosauce.cutecalc.logic.CalcViewModel
import com.sosauce.cutecalc.screens.CalculatorScreen
import com.sosauce.cutecalc.screens.HistoryScreen
import com.sosauce.cutecalc.screens.settings.SettingsScreen

@Composable
fun Nav(historyViewModel: HistoryViewModel) {


    val activity = LocalActivity.current
    var screenToDisplay by rememberSaveable { mutableStateOf(Screens.MAIN) }
    val viewModel = viewModel<CalcViewModel>()
    val calculations by historyViewModel.allCalculations.collectAsStateWithLifecycle()


    // Mimic back behavior from navigation
    BackHandler {
        if (screenToDisplay != Screens.MAIN) {
            screenToDisplay = Screens.MAIN
        } else {
            activity?.moveTaskToBack(true)
        }
    }

    AnimatedContent(
        targetState = screenToDisplay,
        transitionSpec = { fadeIn() togetherWith fadeOut() }
    ) { screen ->
        when (screen) {
            Screens.MAIN -> {
                CalculatorScreen(
                    viewModel = viewModel,
                    onNavigate = { screenToDisplay = it },
                    historyViewModel = historyViewModel
                )
            }

            Screens.HISTORY -> {
                HistoryScreen(
                    calculations = calculations,
                    onNavigate = { screenToDisplay = it },
                    onEvents = historyViewModel::onEvent,
                    onPutBackToField = { calculation ->
                        viewModel.handleAction(CalcAction.ResetField)
                        viewModel.handleAction(CalcAction.AddToField(calculation))
                    }
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