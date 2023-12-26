@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)

package com.sosauce.cutecalc

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sosauce.cutecalc.ui.theme.GlobalFont
import com.sosauce.cutecalc.ui.theme.MediumGray


@Composable
fun CalculatorUI () {
    val scaffoldState = rememberScaffoldState()
    val viewModel = viewModel<CalculatorViewModel>()
    val state = viewModel.state
    val buttonSpacing = 9.dp
    val displayText = state.number1 + (state.operation?.symbol ?: "") + state.number2
    val fontSizeDisplay = when {
        displayText.length >= 15 -> 30.sp
        displayText.length >= 11 -> 40.sp
        else -> 60.sp // Default font size
    }


    androidx.compose.material.Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            AppBar()
        },
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
                .padding(15.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter),
                verticalArrangement = Arrangement.spacedBy(buttonSpacing),
            ) {
                Column {
                    Text(
                        text = displayText,
                        textAlign = TextAlign.End,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp),
                        fontWeight = FontWeight.Light,
                        fontSize = fontSizeDisplay,
                        color = Color.White,
                        fontFamily = GlobalFont
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
                ) {
                    CalculatorButton(
                        symbol = "AC",
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .aspectRatio(2f)
                            .weight(2f)
                    ) {
                        viewModel.onAction(CalculatorAction.Clear)
                    }
                    CalculatorButton(
                        symbol = "⌫",
                        color = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier
                            .aspectRatio(1f)
                            .weight(1f)
                    ) {
                        viewModel.onAction(CalculatorAction.Delete)
                    }
                    CalculatorButton(
                        symbol = "÷",
                        color = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier
                            .aspectRatio(1f)
                            .weight(1f)
                    ) {
                        viewModel.onAction(CalculatorAction.Operation(CalculatorOperation.Divide))
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
                ) {
                    CalculatorButton(
                        symbol = "7",
                        color = MediumGray,
                        modifier = Modifier
                            .aspectRatio(1f)
                            .weight(1f)
                    ) {
                        viewModel.onAction(CalculatorAction.Number(7))
                    }
                    CalculatorButton(
                        symbol = "8",
                        color = MediumGray,
                        modifier = Modifier
                            .aspectRatio(1f)
                            .weight(1f)
                    ) {
                        viewModel.onAction(CalculatorAction.Number(8))
                    }
                    CalculatorButton(
                        symbol = "9",
                        color = MediumGray,
                        modifier = Modifier
                            .aspectRatio(1f)
                            .weight(1f)
                    ) {
                        viewModel.onAction(CalculatorAction.Number(9))
                    }
                    CalculatorButton(
                        symbol = "×",
                        color = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier
                            .aspectRatio(1f)
                            .weight(1f)
                    ) {
                        viewModel.onAction(CalculatorAction.Operation(CalculatorOperation.Multiply))
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
                ) {
                    CalculatorButton(
                        symbol = "4",
                        color = MediumGray,
                        modifier = Modifier
                            .aspectRatio(1f)
                            .weight(1f)
                    ) {
                        viewModel.onAction(CalculatorAction.Number(4))
                    }
                    CalculatorButton(
                        symbol = "5",
                        color = MediumGray,
                        modifier = Modifier
                            .aspectRatio(1f)
                            .weight(1f)
                    ) {
                        viewModel.onAction(CalculatorAction.Number(5))
                    }
                    CalculatorButton(
                        symbol = "6",
                        color = MediumGray,
                        modifier = Modifier
                            .aspectRatio(1f)
                            .weight(1f)
                    ) {
                        viewModel.onAction(CalculatorAction.Number(6))
                    }
                    CalculatorButton(
                        symbol = "−",
                        color = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier
                            .aspectRatio(1f)
                            .weight(1f)
                    ) {
                        viewModel.onAction(CalculatorAction.Operation(CalculatorOperation.Subtract))
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
                ) {
                    CalculatorButton(
                        symbol = "1",
                        color = MediumGray,
                        modifier = Modifier
                            .aspectRatio(1f)
                            .weight(1f)
                    ) {
                        viewModel.onAction(CalculatorAction.Number(1))
                    }
                    CalculatorButton(
                        symbol = "2",
                        color = MediumGray,
                        modifier = Modifier
                            .aspectRatio(1f)
                            .weight(1f)
                    ) {
                        viewModel.onAction(CalculatorAction.Number(2))
                    }
                    CalculatorButton(
                        symbol = "3",
                        color = MediumGray,
                        modifier = Modifier
                            .aspectRatio(1f)
                            .weight(1f)
                    ) {
                        viewModel.onAction(CalculatorAction.Number(3))
                    }
                    CalculatorButton(
                        symbol = "+",
                        color = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier
                            .aspectRatio(1f)
                            .weight(1f)
                    ) {
                        viewModel.onAction(CalculatorAction.Operation(CalculatorOperation.Add))
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
                ) {
                    CalculatorButton(
                        symbol = "0",
                        color = MediumGray,
                        modifier = Modifier
                            .aspectRatio(2f)
                            .weight(2f)
                    ) {
                        viewModel.onAction(CalculatorAction.Number(0))
                    }
                    CalculatorButton(
                        symbol = ".",
                        color = MediumGray,
                        modifier = Modifier
                            .aspectRatio(1f)
                            .weight(1f)
                    ) {
                        viewModel.onAction(CalculatorAction.Decimal)
                    }
                    CalculatorButton(
                        symbol = "=",
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .aspectRatio(1f)
                            .weight(1f)
                    ) {
                        viewModel.onAction(CalculatorAction.Calculate)
                    }
                }
            }
        }
    }
}