package com.sosauce.cutecalc

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.sosauce.cutecalc.ui.theme.GlobalFont

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun CalculatorUI (navController:  NavController) {
    val scaffoldState = rememberScaffoldState()
    val viewModel = viewModel<CalculatorViewModel>()
    val state = viewModel.state
    val buttonSpacing = 9.dp
    val displayText = state.number1 + (state.operation?.symbol ?: "") + state.number2
    val fontSizeDisplay = when {
        displayText.length >= 15 -> 30.sp
        displayText.length >= 10 -> 40.sp
        else -> 60.sp // Default font size
    }
    var isClicked by remember { mutableStateOf(false) }

    androidx.compose.material.Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            AppBar(title = "", showBackArrow = false, showMenuIcon = true, navController = navController)
        },
    ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(15.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter),
                    verticalArrangement = Arrangement.spacedBy(buttonSpacing),
                ) {
                    Text(
                        text = displayText,
                        textAlign = TextAlign.End,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp),
                        fontWeight = FontWeight.Light,
                        fontSize = fontSizeDisplay,
                        color = MaterialTheme.colorScheme.onBackground,
                        fontFamily = GlobalFont
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
                    ) {
                        FilledIconButton(onClick = {viewModel.onAction(CalculatorAction.Clear)}, modifier = Modifier
                            .aspectRatio(1f)
                            .weight(1f)) { Text(text = "AC", color = Color.White, fontSize = 40.sp, fontFamily = GlobalFont) }

                        FilledIconButton(onClick = {viewModel.onAction(CalculatorAction.Calculate)}, modifier = Modifier
                            .aspectRatio(1f)
                            .weight(1f)) { Text(text = "⌫", color = Color.White, fontSize = 40.sp, fontFamily = GlobalFont) }

                        FilledIconButton(onClick = {viewModel.onAction(CalculatorAction.Operation(CalculatorOperation.Percentage))}, modifier = Modifier
                            .aspectRatio(1f)
                            .weight(1f)) { Text(text = "%", color = Color.White, fontSize = 40.sp, fontFamily = GlobalFont) }

                        FilledIconButton(onClick = {viewModel.onAction(CalculatorAction.Operation(CalculatorOperation.Divide))}, modifier = Modifier
                            .aspectRatio(1f)
                            .weight(1f)) { Text(text = "/", color = Color.White, fontSize = 40.sp, fontFamily = GlobalFont) }
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
                    ) {
                        FilledTonalButton(onClick = {viewModel.onAction(CalculatorAction.Number(7))}, modifier = Modifier
                            .aspectRatio(1f)
                            .weight(1f)) { Text(text = "7", color = MaterialTheme.colorScheme.onBackground, fontSize = 40.sp, fontFamily = GlobalFont ) }

                        FilledTonalButton(onClick = {viewModel.onAction(CalculatorAction.Number(8))}, modifier = Modifier
                            .aspectRatio(1f)
                            .weight(1f)) { Text(text = "8", color = MaterialTheme.colorScheme.onBackground, fontSize = 40.sp, fontFamily = GlobalFont ) }

                        FilledTonalButton(onClick = {viewModel.onAction(CalculatorAction.Number(9))}, modifier = Modifier
                            .aspectRatio(1f)
                            .weight(1f)) { Text(text = "9", color = MaterialTheme.colorScheme.onBackground, fontSize = 40.sp, fontFamily = GlobalFont ) }

                        FilledIconButton(onClick = {viewModel.onAction(CalculatorAction.Operation(CalculatorOperation.Multiply))}, modifier = Modifier
                            .aspectRatio(1f)
                            .weight(1f)) { Text(text = "×", color = Color.White, fontSize = 40.sp, fontFamily = GlobalFont) }
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
                    ) {
                        FilledTonalButton(onClick = {viewModel.onAction(CalculatorAction.Number(4))}, modifier = Modifier
                            .aspectRatio(1f)
                            .weight(1f)) { Text(text = "4", color = MaterialTheme.colorScheme.onBackground, fontSize = 40.sp, fontFamily = GlobalFont ) }

                        FilledTonalButton(onClick = {viewModel.onAction(CalculatorAction.Number(5))}, modifier = Modifier
                            .aspectRatio(1f)
                            .weight(1f)) { Text(text = "5", color = MaterialTheme.colorScheme.onBackground, fontSize = 40.sp, fontFamily = GlobalFont ) }

                        FilledTonalButton(onClick = {viewModel.onAction(CalculatorAction.Number(6))}, modifier = Modifier
                            .aspectRatio(1f)
                            .weight(1f)) { Text(text = "6", color = MaterialTheme.colorScheme.onBackground, fontSize = 40.sp, fontFamily = GlobalFont ) }

                        FilledIconButton(onClick = {viewModel.onAction(CalculatorAction.Operation(CalculatorOperation.Subtract))}, modifier = Modifier
                            .aspectRatio(1f)
                            .weight(1f)) { Text(text = "-", color = Color.White, fontSize = 40.sp, fontFamily = GlobalFont) }
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
                    ) {
                        FilledTonalButton(onClick = {viewModel.onAction(CalculatorAction.Number(1))}, modifier = Modifier
                            .aspectRatio(1f)
                            .weight(1f)) { Text(text = "1", color = MaterialTheme.colorScheme.onBackground, fontSize = 40.sp, fontFamily = GlobalFont ) }

                        FilledTonalButton(onClick = {viewModel.onAction(CalculatorAction.Number(2))}, modifier = Modifier
                            .aspectRatio(1f)
                            .weight(1f)) { Text(text = "2", color = MaterialTheme.colorScheme.onBackground, fontSize = 40.sp, fontFamily = GlobalFont ) }

                        FilledTonalButton(onClick = {viewModel.onAction(CalculatorAction.Number(3))}, modifier = Modifier
                            .aspectRatio(1f)
                            .weight(1f)) { Text(text = "3", color = MaterialTheme.colorScheme.onBackground, fontSize = 40.sp, fontFamily = GlobalFont ) }

                        FilledIconButton(onClick = {viewModel.onAction(CalculatorAction.Operation(CalculatorOperation.Add))}, modifier = Modifier
                            .aspectRatio(1f)
                            .weight(1f)) { Text(text = "+", color = Color.White, fontSize = 40.sp, fontFamily = GlobalFont) }
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
                    ) {
                        FilledTonalButton(onClick = {viewModel.onAction(CalculatorAction.Number(0))}, modifier = Modifier
                            .aspectRatio(2f)
                            .weight(2f)) { Text(text = "0", color = MaterialTheme.colorScheme.onBackground, fontSize = 40.sp, fontFamily = GlobalFont ) }

                        FilledTonalButton(onClick = {viewModel.onAction(CalculatorAction.Decimal)}, modifier = Modifier
                            .aspectRatio(1f)
                            .weight(1f)) { Text(text = ".", color = MaterialTheme.colorScheme.onBackground, fontSize = 40.sp, fontFamily = GlobalFont ) }

                        FilledIconButton(onClick = {viewModel.onAction(CalculatorAction.Calculate)}, modifier = Modifier
                            .aspectRatio(1f)
                            .weight(1f)) { Text(text = "=", color = Color.White, fontSize = 40.sp, fontFamily = GlobalFont) }
                    }
                }
            }
        }
    }