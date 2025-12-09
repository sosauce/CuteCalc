package com.sosauce.cutecalc.ui.navigation

import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalActivity
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sosauce.cutecalc.data.actions.CalcAction
import com.sosauce.cutecalc.ui.screens.calculator.CalculatorScreen
import com.sosauce.cutecalc.ui.screens.calculator.CalculatorViewModel
import com.sosauce.cutecalc.ui.screens.history.HistoryScreen
import com.sosauce.cutecalc.ui.screens.history.HistoryViewModel
import com.sosauce.cutecalc.ui.screens.settings.SettingsScreen
import com.sosauce.cutecalc.utils.CalculatorViewModelFactory
import com.sosauce.cutecalc.utils.HistoryViewModelFactory
import kotlinx.coroutines.launch

@Composable
fun Nav() {


    val activity = LocalActivity.current!!
    val scope = rememberCoroutineScope()
    val viewModel =
        viewModel<CalculatorViewModel>(factory = CalculatorViewModelFactory(activity.application))
    val historyViewModel =
        viewModel<HistoryViewModel>(factory = HistoryViewModelFactory(activity.application))
    var screenToDisplay by rememberSaveable { mutableStateOf(Screens.MAIN) }

    val pagerState = rememberPagerState { 2 }

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
        transitionSpec = { fadeIn() togetherWith fadeOut() },
        modifier = Modifier.background(MaterialTheme.colorScheme.background)
    ) { screen ->
        when (screen) {
            Screens.MAIN -> {
                HorizontalPager(
                    state = pagerState
                ) { page ->
                    when (page) {
                        0 -> {
                            CalculatorScreen(
                                viewModel = viewModel,
                                onNavigate = { screenToDisplay = it },
                                historyViewModel = historyViewModel,
                                onScrollToHistory = {
                                    scope.launch {
                                        pagerState.animateScrollToPage(1)
                                    }
                                }
                            )
                        }

                        1 -> {

                            val calculations by historyViewModel.allCalculations.collectAsStateWithLifecycle()


                            HistoryScreen(
                                calculations = calculations,
                                onEvents = historyViewModel::onEvent,
                                onPutBackToField = { calculation ->
                                    viewModel.handleAction(CalcAction.ResetField)
                                    viewModel.handleAction(CalcAction.AddToField(calculation))
                                },
                                onScrollToMain = {
                                    scope.launch {
                                        pagerState.animateScrollToPage(0)
                                    }
                                }
                            )
                        }
                    }
                }
            }

            Screens.SETTINGS -> {
                SettingsScreen(
                    onNavigate = { screenToDisplay = it }
                )
            }
        }
    }

}