package com.sosauce.cutecalc.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sosauce.cutecalc.components.CuteButton
import com.sosauce.cutecalc.components.CuteIconButton
import com.sosauce.cutecalc.history.HistoryEvents
import com.sosauce.cutecalc.history.HistoryViewModel
import com.sosauce.cutecalc.logic.CalcAction
import com.sosauce.cutecalc.logic.CalcViewModel
import com.sosauce.cutecalc.logic.Evaluator
import com.sosauce.cutecalc.logic.formatOrNot
import com.sosauce.cutecalc.logic.rememberDecimal
import com.sosauce.cutecalc.logic.rememberHistoryMaxItems
import com.sosauce.cutecalc.logic.rememberSaveErrorsToHistory
import com.sosauce.cutecalc.logic.rememberUseHistory
import com.sosauce.cutecalc.ui.theme.GlobalFont

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CalculatorScreenLandscape(
    viewModel: CalcViewModel,
    historyViewModel: HistoryViewModel
) {
    val saveToHistory by rememberUseHistory()
    val decimalSetting by rememberDecimal()
    val historyMaxItems by rememberHistoryMaxItems()
    val saveErrorsToHistory by rememberSaveErrorsToHistory()
    val firstRow = arrayOf("!", "%", "√", "π")
    val secondRow = arrayOf("", "9", "8", "7", "×", viewModel.parenthesis, "C")
    val thirdRow = arrayOf("3", "4", "5", "6", "+", "^")
    val fourthRow = arrayOf("2", "1", "0", ".", "-", "/", "=")


    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(end = 15.dp),
        contentWindowInsets = WindowInsets.safeDrawing
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues)
        ) {
            Row(
                modifier = Modifier
                    .horizontalScroll(rememberScrollState())
                    .align(Alignment.End)
            ) {
                DisableSoftKeyboard {
                    BasicTextField(
                        value = viewModel.displayText.copy(
                            text = formatOrNot(
                                viewModel.displayText.text,
                                decimalSetting
                            )
                        ),
                        onValueChange = { viewModel.displayText = it },
                        singleLine = true,
                        textStyle = TextStyle(
                            textAlign = TextAlign.End,
                            color = MaterialTheme.colorScheme.onBackground,
                            fontSize = 53.sp,
                            fontFamily = GlobalFont
                        ),
                        cursorBrush = SolidColor(MaterialTheme.colorScheme.onBackground),
                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                firstRow.forEach {
                    CuteButton(
                        text = it,
                        color = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.background),
                        modifier = Modifier
                            .weight(0.15f),
                        onClick = {
                            viewModel.handleAction(CalcAction.AddToField(it))
                        }
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                secondRow.forEach {
                    val containerColor = when (it) {
                        "×", viewModel.parenthesis -> MaterialTheme.colorScheme.secondaryContainer
                        "C" -> MaterialTheme.colorScheme.inversePrimary
                        else -> MaterialTheme.colorScheme.surfaceContainer
                    }
                    CuteButton(
                        text = it,
                        color = ButtonDefaults.buttonColors(
                            containerColor = containerColor,
                            disabledContainerColor = Color.Transparent
                        ),
                        modifier = Modifier
                            .weight(0.15f),
                        onClick = {
                            if (it == "C") {
                                viewModel.handleAction(CalcAction.ResetField)
                            } else viewModel.handleAction(CalcAction.AddToField(it))
                        },
                        enabled = it.isNotBlank()
                    )
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                thirdRow.forEach {
                    val containerColor = when (it) {
                        "+", "^" -> MaterialTheme.colorScheme.secondaryContainer
                        else -> MaterialTheme.colorScheme.surfaceContainer
                    }
                    CuteButton(
                        text = it,
                        color = ButtonDefaults.buttonColors(containerColor),
                        modifier = Modifier
                            .weight(0.15f),
                        onClick = { viewModel.handleAction(CalcAction.AddToField(it)) }
                    )
                }
                CuteIconButton(
                    modifier = Modifier
                        .weight(0.15f),
                    onClick = { viewModel.handleAction(CalcAction.Backspace) },
                    onLongClick = { viewModel.handleAction(CalcAction.ResetField) },
                    color = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.inversePrimary)
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.Bottom
            ) {
                fourthRow.forEach {
                    val containerColor = when (it) {
                        "-", "/" -> MaterialTheme.colorScheme.secondaryContainer
                        "=" -> MaterialTheme.colorScheme.inversePrimary
                        else -> MaterialTheme.colorScheme.surfaceContainer
                    }
                    CuteButton(
                        text = it,
                        color = ButtonDefaults.buttonColors(containerColor),
                        modifier = Modifier
                            .weight(0.15f),
                        onClick = {
                            if (it == "=") {
                                if (saveToHistory) {
                                    historyViewModel.onEvent(
                                        HistoryEvents.AddCalculation(
                                            operation = viewModel.displayText.text,
                                            result = Evaluator.eval(viewModel.displayText.text),
                                            maxHistoryItems = historyMaxItems,
                                            saveErrors = saveErrorsToHistory
                                        )
                                    )
                                }
                                viewModel.handleAction(CalcAction.GetResult)
                            } else viewModel.handleAction(CalcAction.AddToField(it))
                        }
                    )
                }
            }
        }
    }
}