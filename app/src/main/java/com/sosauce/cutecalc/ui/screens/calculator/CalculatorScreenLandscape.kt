@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)

package com.sosauce.cutecalc.ui.screens.calculator

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEach
import com.sosauce.cutecalc.R
import com.sosauce.cutecalc.data.actions.CalcAction
import com.sosauce.cutecalc.data.datastore.rememberHistoryMaxItems
import com.sosauce.cutecalc.data.datastore.rememberSaveErrorsToHistory
import com.sosauce.cutecalc.data.datastore.rememberShowClearButton
import com.sosauce.cutecalc.data.datastore.rememberUseHistory
import com.sosauce.cutecalc.domain.repository.HistoryEvents
import com.sosauce.cutecalc.ui.navigation.Screens
import com.sosauce.cutecalc.ui.screens.calculator.components.CalcButton
import com.sosauce.cutecalc.ui.screens.calculator.components.CalculationDisplay
import com.sosauce.cutecalc.ui.screens.calculator.components.CuteButton
import com.sosauce.cutecalc.ui.screens.history.HistoryViewModel
import com.sosauce.cutecalc.utils.BACKSPACE
import com.sosauce.cutecalc.utils.whichParenthesis
import java.text.DecimalFormatSymbols

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CalculatorScreenLandscape(
    viewModel: CalculatorViewModel,
    historyViewModel: HistoryViewModel,
    onNavigate: (Screens) -> Unit,
    onScrollToHistory: () -> Unit
) {
    val showClearButton by rememberShowClearButton()
    val localeDecimalChar =
        remember { DecimalFormatSymbols.getInstance().decimalSeparator.toString() }
    val saveErrorsToHistory by rememberSaveErrorsToHistory()
    val maxItemsToHistory by rememberHistoryMaxItems()
    val saveToHistory by rememberUseHistory()

    val row1 = listOf(
        CalcButton(
            text = "√",
            backgroundColor = MaterialTheme.colorScheme.surfaceContainer,
            onClick = { viewModel.handleAction(CalcAction.AddToField("√")) }
        ),
        CalcButton(
            text = "π",
            backgroundColor = MaterialTheme.colorScheme.surfaceContainer,
            onClick = { viewModel.handleAction(CalcAction.AddToField("π")) }
        ),
        CalcButton(
            text = "9",
            backgroundColor = MaterialTheme.colorScheme.surfaceContainer,
            onClick = { viewModel.handleAction(CalcAction.AddToField("9")) }
        ),
        CalcButton(
            text = "8",
            backgroundColor = MaterialTheme.colorScheme.surfaceContainer,
            onClick = { viewModel.handleAction(CalcAction.AddToField("8")) }
        ),
        CalcButton(
            text = "7",
            backgroundColor = MaterialTheme.colorScheme.surfaceContainer,
            onClick = { viewModel.handleAction(CalcAction.AddToField("7")) }
        ),
        CalcButton(
            text = "^",
            backgroundColor = MaterialTheme.colorScheme.secondaryContainer,
            onClick = { viewModel.handleAction(CalcAction.AddToField("^")) }
        ),
        if (showClearButton) {
            CalcButton(
                text = viewModel.textFieldState.text.toString().whichParenthesis(),
                backgroundColor = MaterialTheme.colorScheme.secondaryContainer,
                onClick = {
                    viewModel.handleAction(
                        CalcAction.AddToField(
                            viewModel.textFieldState.text.toString().whichParenthesis()
                        )
                    )
                }
            )
        } else {
            CalcButton(
                text = "(",
                backgroundColor = MaterialTheme.colorScheme.secondaryContainer,
                onClick = { viewModel.handleAction(CalcAction.AddToField("(")) }
            )
        },
        if (showClearButton) {
            CalcButton(
                text = "C",
                backgroundColor = MaterialTheme.colorScheme.inversePrimary,
                onClick = { viewModel.handleAction(CalcAction.ResetField) }
            )
        } else {
            CalcButton(
                text = ")",
                backgroundColor = MaterialTheme.colorScheme.secondaryContainer,
                onClick = {
                    viewModel.handleAction(
                        CalcAction.AddToField(
                            viewModel.textFieldState.text.toString().whichParenthesis()
                        )
                    )
                }
            )
        }
    )
    val row2 = listOf(
        CalcButton(
            text = "%",
            backgroundColor = MaterialTheme.colorScheme.surfaceContainer,
            onClick = { viewModel.handleAction(CalcAction.AddToField("%")) }
        ),
        CalcButton(
            text = "3",
            backgroundColor = MaterialTheme.colorScheme.surfaceContainer,
            onClick = { viewModel.handleAction(CalcAction.AddToField("3")) }
        ),
        CalcButton(
            text = "4",
            backgroundColor = MaterialTheme.colorScheme.surfaceContainer,
            onClick = { viewModel.handleAction(CalcAction.AddToField("4")) }
        ),
        CalcButton(
            text = "5",
            backgroundColor = MaterialTheme.colorScheme.surfaceContainer,
            onClick = { viewModel.handleAction(CalcAction.AddToField("5")) }
        ),
        CalcButton(
            text = "6",
            backgroundColor = MaterialTheme.colorScheme.surfaceContainer,
            onClick = { viewModel.handleAction(CalcAction.AddToField("6")) }
        ),
        CalcButton(
            text = "+",
            backgroundColor = MaterialTheme.colorScheme.secondaryContainer,
            onClick = { viewModel.handleAction(CalcAction.AddToField("+")) }
        ),
        CalcButton(
            text = "-",
            backgroundColor = MaterialTheme.colorScheme.secondaryContainer,
            onClick = { viewModel.handleAction(CalcAction.AddToField("-")) }
        ),
        CalcButton(
            text = BACKSPACE,
            backgroundColor = MaterialTheme.colorScheme.inversePrimary,
            onClick = { viewModel.handleAction(CalcAction.Backspace) },
            onLongClick = { viewModel.handleAction(CalcAction.ResetField) }
        )
    )
    val row3 = listOf(
        CalcButton(
            text = "!",
            backgroundColor = MaterialTheme.colorScheme.surfaceContainer,
            onClick = { viewModel.handleAction(CalcAction.AddToField("!")) }
        ),
        CalcButton(
            text = "2",
            backgroundColor = MaterialTheme.colorScheme.surfaceContainer,
            onClick = { viewModel.handleAction(CalcAction.AddToField("2")) }
        ),
        CalcButton(
            text = "1",
            backgroundColor = MaterialTheme.colorScheme.surfaceContainer,
            onClick = { viewModel.handleAction(CalcAction.AddToField("1")) }
        ),
        CalcButton(
            text = "0",
            backgroundColor = MaterialTheme.colorScheme.surfaceContainer,
            onClick = { viewModel.handleAction(CalcAction.AddToField("0")) }
        ),
        CalcButton(
            text = localeDecimalChar,
            backgroundColor = MaterialTheme.colorScheme.surfaceContainer,
            onClick = { viewModel.handleAction(CalcAction.AddToField(".")) }
        ),
        CalcButton(
            text = "×",
            backgroundColor = MaterialTheme.colorScheme.secondaryContainer,
            onClick = { viewModel.handleAction(CalcAction.AddToField("×")) }
        ),
        CalcButton(
            text = "/",
            backgroundColor = MaterialTheme.colorScheme.secondaryContainer,
            onClick = { viewModel.handleAction(CalcAction.AddToField("/")) }
        ),
        CalcButton(
            text = "=",
            backgroundColor = MaterialTheme.colorScheme.inversePrimary,
            onClick = {
                val operation = viewModel.textFieldState.text.toString()
                viewModel.handleAction(CalcAction.GetResult)
                val result = viewModel.evaluatedCalculation

                if (saveToHistory && operation != result) {
                    historyViewModel.onEvent(
                        HistoryEvents.AddCalculation(
                            operation = operation,
                            result = result,
                            maxHistoryItems = maxItemsToHistory,
                            saveErrors = saveErrorsToHistory
                        )
                    )
                }
            },
        )
    )

    Scaffold(contentWindowInsets = WindowInsets.safeDrawing) { pv ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(pv),
            contentAlignment = Alignment.BottomCenter
        ) {
            Column(
                modifier = Modifier.align(Alignment.TopStart)
            ) {
                IconButton(
                    onClick = { onNavigate(Screens.SETTINGS) },
                    shapes = IconButtonDefaults.shapes()
                ) {
                    Icon(
                        painter = painterResource(R.drawable.settings_filled),
                        contentDescription = stringResource(R.string.settings),
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }
                IconButton(
                    onClick = onScrollToHistory,
                    shapes = IconButtonDefaults.shapes()
                ) {
                    Icon(
                        painter = painterResource(R.drawable.history_rounded),
                        contentDescription = stringResource(R.string.history),
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }
            }

            Column {
                CalculationDisplay(
                    viewModel = viewModel
                )
                Column(
                    modifier = Modifier.padding(horizontal = 10.dp),
                    verticalArrangement = Arrangement.spacedBy(9.dp),
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(9.dp)
                    ) {
                        row1.fastForEach { button ->
                            CuteButton(
                                text = button.text,
                                backgroundColor = button.backgroundColor,
                                onClick = button.onClick,
                                roundButton = false
                            )
                        }

                    }
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(9.dp)
                    ) {
                        row2.fastForEach { button ->
                            CuteButton(
                                text = button.text,
                                backgroundColor = button.backgroundColor,
                                onClick = button.onClick,
                                onLongClick = button.onLongClick,
                                roundButton = false
                            )
                        }
                    }
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(9.dp)
                    ) {
                        row3.fastForEach { button ->
                            CuteButton(
                                text = button.text,
                                backgroundColor = button.backgroundColor,
                                onClick = button.onClick,
                                roundButton = false
                            )
                        }
                    }
                }
            }
        }


    }
}