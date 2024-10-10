package com.sosauce.cutecalc.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sosauce.cutecalc.components.CuteButton
import com.sosauce.cutecalc.history.HistoryEvents
import com.sosauce.cutecalc.history.HistoryState
import com.sosauce.cutecalc.history.HistoryViewModel
import com.sosauce.cutecalc.logic.CalcAction
import com.sosauce.cutecalc.logic.CalcViewModel
import com.sosauce.cutecalc.logic.Evaluator
import com.sosauce.cutecalc.logic.rememberUseHistory
import com.sosauce.cutecalc.ui.theme.GlobalFont

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LandscapeLayout(
    viewModel: CalcViewModel,
    historyViewModel: HistoryViewModel,
    historyState: HistoryState
) {
    val saveToHistory by rememberUseHistory()

    Scaffold {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(15.dp)
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align((Alignment.BottomCenter))
                    .navigationBarsPadding()
            ) {
                Row(
                    modifier = Modifier
                        .horizontalScroll(rememberScrollState())
                        .align(Alignment.End)
                ) {
                    DisableSoftKeyboard {
                        BasicTextField(
                            value = viewModel.displayText,
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
                    CuteButton(
                        text = "!",
                        color = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.background),
                        modifier = Modifier
                            .weight(0.15f),
                        onClick = {
                            viewModel.handleAction(CalcAction.AddToField("!"))
                        }
                    )
                    CuteButton(
                        text = "%",
                        color = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.background),
                        modifier = Modifier
                            .weight(0.15f),
                        onClick = {
                            viewModel.handleAction(CalcAction.AddToField("%"))
                        }
                    )
                    CuteButton(
                        text = "√",
                        color = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.background),
                        modifier = Modifier
                            .weight(0.15f),
                        onClick = {
                            viewModel.handleAction(CalcAction.AddToField("√"))
                        }
                    )
                    CuteButton(
                        text = "π",
                        color = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.background),
                        
                        modifier = Modifier
                            .weight(0.15f),
                        onClick = {
                            viewModel.handleAction(CalcAction.AddToField("PI"))
                        }
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    CuteButton(
                        text = "",
                        color = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.background),
                        modifier = Modifier
                            .weight(0.15f),
                        onClick = {}
                    )
                    CuteButton(
                        text = "9",
                        modifier = Modifier
                            .weight(0.15f),
                        onClick = {
                            viewModel.handleAction(CalcAction.AddToField("9"))
                        }
                    )
                    CuteButton(
                        text = "8",
                        modifier = Modifier
                            .weight(0.15f),
                        onClick = {
                            viewModel.handleAction(CalcAction.AddToField("8"))
                        }
                    )
                    CuteButton(
                        text = "7",
                        modifier = Modifier
                            .weight(0.15f),
                        onClick = {
                            viewModel.handleAction(CalcAction.AddToField("7"))
                        }
                    )
                    CuteButton(
                        text = "×",
                        color = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.outlineVariant),
                        
                        modifier = Modifier
                            .weight(0.15f),
                        onClick = {
                            viewModel.handleAction(CalcAction.AddToField("×"))
                        }
                    )
                    CuteButton(
                        text = viewModel.parenthesis,
                        color = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.outlineVariant),
                        
                        modifier = Modifier
                            .weight(0.15f),
                        onClick = {
                            viewModel.handleAction(CalcAction.AddToField(viewModel.parenthesis))
                        }
                    )
                    CuteButton(
                        text = "C",
                        color = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.tertiaryContainer),
                        
                        modifier = Modifier
                            .weight(0.15f),
                        onClick = {
                            viewModel.handleAction(CalcAction.ResetField)
                        }
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    CuteButton(
                        text = "3",
                        modifier = Modifier
                            .weight(0.15f),
                        onClick = {
                            viewModel.handleAction(CalcAction.AddToField("3"))
                        }
                    )
                    CuteButton(
                        text = "4",
                        modifier = Modifier
                            .weight(0.15f),
                        onClick = {
                            viewModel.handleAction(CalcAction.AddToField("4"))
                        }
                    )
                    CuteButton(
                        text = "5",
                        modifier = Modifier
                            .weight(0.15f),
                        onClick = {
                            viewModel.handleAction(CalcAction.AddToField("5"))
                        }
                    )
                    CuteButton(
                        text = "6",
                        modifier = Modifier
                            .weight(0.15f),
                        onClick = {
                            viewModel.handleAction(CalcAction.AddToField("6"))
                        }
                    )
                    CuteButton(
                        text = "+",
                        color = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.outlineVariant),
                        
                        modifier = Modifier
                            .weight(0.15f),
                        onClick = {
                            viewModel.handleAction(CalcAction.AddToField("+"))
                        }
                    )
                    CuteButton(
                        text = "^",
                        color = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.outlineVariant),
                        
                        modifier = Modifier
                            .weight(0.15f),
                        onClick = {
                            viewModel.handleAction(CalcAction.AddToField("^"))
                        }
                    )
                    CuteButton(
                        text = "⌫",
                        color = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.tertiaryContainer),
                        
                        modifier = Modifier
                            .weight(0.15f),
                        onClick = {
                            viewModel.handleAction(CalcAction.Backspace)
                        }
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.Bottom
                ) {
                    CuteButton(
                        text = "2",
                        modifier = Modifier
                            .weight(0.15f),
                        onClick = {
                            viewModel.handleAction(CalcAction.AddToField("2"))
                        }
                    )
                    CuteButton(
                        text = "1",
                        modifier = Modifier
                            .weight(0.15f),
                        onClick = {
                            viewModel.handleAction(CalcAction.AddToField("1"))
                        }
                    )
                    CuteButton(
                        text = "0",
                        modifier = Modifier
                            .weight(0.15f),
                        onClick = {
                            viewModel.handleAction(CalcAction.AddToField("0"))
                        }
                    )
                    CuteButton(
                        text = ".",
                        color = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.secondaryContainer),
                        
                        modifier = Modifier
                            .weight(0.15f),
                        onClick = {
                            viewModel.handleAction(CalcAction.AddToField("."))
                        }
                    )
                    CuteButton(
                        text = "-",
                        color = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.outlineVariant),
                        
                        modifier = Modifier
                            .weight(0.15f),
                        onClick = {
                            viewModel.handleAction(CalcAction.AddToField("-"))
                        }
                    )
                    CuteButton(
                        text = "/",
                        color = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.outlineVariant),
                        
                        modifier = Modifier
                            .weight(0.15f),
                        onClick = {
                            viewModel.handleAction(CalcAction.AddToField("/"))
                        }
                    )
                    CuteButton(
                        text = "=",
                        color = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.outlineVariant),
                        
                        modifier = Modifier
                            .weight(0.15f),
                        onClick = {
                            if (saveToHistory) {
                                historyState.operation.value =
                                    viewModel.displayText.text
                                historyState.result.value =
                                    Evaluator.eval(viewModel.displayText.text)

                                historyViewModel.onEvent(
                                    HistoryEvents.AddCalculation(
                                        operation = historyState.operation.value,
                                        result = historyState.result.value
                                    )
                                )
                            }
                            viewModel.handleAction(CalcAction.GetResult)
                        }
                    )
                }
            }
        }
    }
}