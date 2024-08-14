package com.sosauce.cutecalc.logic.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sosauce.cutecalc.history.HistoryViewModel
import com.sosauce.cutecalc.logic.CalcViewModel
import com.sosauce.cutecalc.screens.CalculatorUI
import com.sosauce.cutecalc.screens.HistoryScreen
import com.sosauce.cutecalc.screens.SettingsScreen
import org.koin.androidx.compose.koinViewModel

@Composable
fun Nav() {
    val navController = rememberNavController()
    val historyViewModel = koinViewModel<HistoryViewModel>()
    val historyState by historyViewModel.state.collectAsStateWithLifecycle()

    NavHost(
        navController = navController,
        startDestination = Screens.Main
    ) {
        composable<Screens.Main> {
            val viewModel = viewModel<CalcViewModel>()
            CalculatorUI(
                navController = navController,
                viewModel = viewModel,
                historyViewModel = historyViewModel,
                historyState = historyState
            )
        }
        composable<Screens.Settings> {
            SettingsScreen(
                navController = navController
            )
        }
        composable<Screens.History> {
            HistoryScreen(
                navController = navController,
                state = historyState,
                onEvents = historyViewModel::onEvent
            )
        }
    }

}