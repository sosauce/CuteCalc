@file:OptIn(ExperimentalMaterial3ExpressiveApi::class, ExperimentalMaterial3Api::class)

package com.sosauce.cutecalc.ui.screens.calculator

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEach
import com.sosauce.cutecalc.R
import com.sosauce.cutecalc.data.actions.CalcAction
import com.sosauce.cutecalc.data.datastore.rememberHistoryMaxItems
import com.sosauce.cutecalc.data.datastore.rememberIsLandscape
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


@Composable
fun CalculatorScreen(
    viewModel: CalculatorViewModel,
    historyViewModel: HistoryViewModel,
    onNavigate: (Screens) -> Unit,
) {
    val isLandscape = rememberIsLandscape()
    val localeDecimalChar =
        remember { DecimalFormatSymbols.getInstance().decimalSeparator.toString() }
    val showClearButton by rememberShowClearButton()
    val saveErrorsToHistory by rememberSaveErrorsToHistory()
    val maxItemsToHistory by rememberHistoryMaxItems()
    val saveToHistory by rememberUseHistory()

    val row1 = listOf(
        CalcButton(
            text = "!",
            backgroundColor = Color.Transparent,
            onClick = { viewModel.handleAction(CalcAction.AddToField("!")) }
        ),
        CalcButton(
            text = "%",
            backgroundColor = Color.Transparent,
            onClick = { viewModel.handleAction(CalcAction.AddToField("%")) }
        ),
        CalcButton(
            text = "√",
            backgroundColor = Color.Transparent,
            onClick = { viewModel.handleAction(CalcAction.AddToField("√")) }
        ),
        CalcButton(
            text = "π",
            backgroundColor = Color.Transparent,
            onClick = { viewModel.handleAction(CalcAction.AddToField("π")) }
        )
    )
    val row2 = listOf(
        if (showClearButton) {
            CalcButton(
                text = "C",
                backgroundColor = MaterialTheme.colorScheme.inversePrimary,
                onClick = { viewModel.handleAction(CalcAction.ResetField) }
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
                text = ")",
                backgroundColor = MaterialTheme.colorScheme.secondaryContainer,
                onClick = { viewModel.handleAction(CalcAction.AddToField(")")) }
            )
        },
        CalcButton(
            text = "^",
            backgroundColor = MaterialTheme.colorScheme.secondaryContainer,
            onClick = { viewModel.handleAction(CalcAction.AddToField("^")) }
        ),
        CalcButton(
            text = "/",
            backgroundColor = MaterialTheme.colorScheme.secondaryContainer,
            onClick = { viewModel.handleAction(CalcAction.AddToField("/")) }
        )
    )
    val row3 = listOf(
        CalcButton(
            text = "7",
            backgroundColor = MaterialTheme.colorScheme.surfaceContainer,
            onClick = { viewModel.handleAction(CalcAction.AddToField("7")) }
        ),
        CalcButton(
            text = "8",
            backgroundColor = MaterialTheme.colorScheme.surfaceContainer,
            onClick = { viewModel.handleAction(CalcAction.AddToField("8")) }
        ),
        CalcButton(
            text = "9",
            backgroundColor = MaterialTheme.colorScheme.surfaceContainer,
            onClick = { viewModel.handleAction(CalcAction.AddToField("9")) }
        ),
        CalcButton(
            text = "×",
            backgroundColor = MaterialTheme.colorScheme.secondaryContainer,
            onClick = { viewModel.handleAction(CalcAction.AddToField("×")) }
        )
    )
    val row4 = listOf(
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
            text = "-",
            backgroundColor = MaterialTheme.colorScheme.secondaryContainer,
            onClick = { viewModel.handleAction(CalcAction.AddToField("-")) }
        )
    )
    val row5 = listOf(
        CalcButton(
            text = "1",
            backgroundColor = MaterialTheme.colorScheme.surfaceContainer,
            onClick = { viewModel.handleAction(CalcAction.AddToField("1")) }
        ),
        CalcButton(
            text = "2",
            backgroundColor = MaterialTheme.colorScheme.surfaceContainer,
            onClick = { viewModel.handleAction(CalcAction.AddToField("2")) }
        ),
        CalcButton(
            text = "3",
            backgroundColor = MaterialTheme.colorScheme.surfaceContainer,
            onClick = { viewModel.handleAction(CalcAction.AddToField("3")) }
        ),
        CalcButton(
            text = "+",
            backgroundColor = MaterialTheme.colorScheme.secondaryContainer,
            onClick = { viewModel.handleAction(CalcAction.AddToField("+")) }
        )
    )
    val row6 = listOf(
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
            text = BACKSPACE,
            backgroundColor = MaterialTheme.colorScheme.surfaceContainer,
            onClick = { viewModel.handleAction(CalcAction.Backspace) },
            onLongClick = { viewModel.handleAction(CalcAction.ResetField) }
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
            }
        )
    )


    if (isLandscape) {
        return CalculatorScreenLandscape(
            historyViewModel = historyViewModel,
            viewModel = viewModel,
            onNavigate = onNavigate
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                actions = {
                    IconButton(onClick = { onNavigate(Screens.HISTORY) }) {
                        Icon(
                            painter = painterResource(R.drawable.history_rounded),
                            contentDescription = stringResource(R.string.history),
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    }
                    IconButton(onClick = { onNavigate(Screens.SETTINGS) }) {
                        Icon(
                            painter = painterResource(R.drawable.settings_filled),
                            contentDescription = stringResource(R.string.settings),
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    }
                }
            )
        }
    ) { pv ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(pv),
            contentAlignment = Alignment.BottomCenter
        ) {
            Column {
                CalculationDisplay(
                    viewModel = viewModel
                )

                Column(
                    modifier = Modifier
                        .padding(horizontal = 10.dp),
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
                                onClick = button.onClick
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
                                onClick = button.onClick
                            )
                        }
                    }
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(9.dp)
                    ) {
                        row4.fastForEach { button ->
                            CuteButton(
                                text = button.text,
                                backgroundColor = button.backgroundColor,
                                onClick = button.onClick
                            )
                        }
                    }
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(9.dp)
                    ) {
                        row5.fastForEach { button ->
                            CuteButton(
                                text = button.text,
                                backgroundColor = button.backgroundColor,
                                onClick = button.onClick
                            )
                        }
                    }
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(9.dp)
                    ) {
                        row6.fastForEach { button ->
                            CuteButton(
                                text = button.text,
                                backgroundColor = button.backgroundColor,
                                onClick = button.onClick,
                                onLongClick = button.onLongClick
                            )
                        }
                    }
                }
            }
        }
    }
}
