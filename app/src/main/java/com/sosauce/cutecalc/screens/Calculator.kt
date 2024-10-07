package com.sosauce.cutecalc.screens

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.InterceptPlatformTextInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.sosauce.cutecalc.AppBar
import com.sosauce.cutecalc.components.CuteButton
import com.sosauce.cutecalc.components.CuteIconButton
import com.sosauce.cutecalc.history.HistoryEvents
import com.sosauce.cutecalc.history.HistoryState
import com.sosauce.cutecalc.history.HistoryViewModel
import com.sosauce.cutecalc.logic.CalcAction
import com.sosauce.cutecalc.logic.CalcViewModel
import com.sosauce.cutecalc.logic.Evaluator
import com.sosauce.cutecalc.logic.formatNumber
import com.sosauce.cutecalc.logic.navigation.Screens
import com.sosauce.cutecalc.logic.rememberDecimal
import com.sosauce.cutecalc.logic.rememberUseHistory
import com.sosauce.cutecalc.logic.rememberVibration
import com.sosauce.cutecalc.ui.theme.GlobalFont
import kotlinx.coroutines.awaitCancellation


@Composable
fun CalculatorUI(
    viewModel: CalcViewModel,
    historyViewModel: HistoryViewModel,
    historyState: HistoryState,
    onNavigateUp: () -> Unit,
    onNavigate: (Screens) -> Unit
) {
    val config = LocalConfiguration.current
    val portraitMode by remember { mutableIntStateOf(config.orientation) }
    val decimal by rememberDecimal()
    val saveToHistory by rememberUseHistory()
    val scrollState = rememberScrollState(0)
    val previewScrollState = rememberScrollState(0)
    val firstRow = listOf("!", "%", "√", "π")
    val secondRow = listOf("C", viewModel.parenthesis, "^", "/")
    val thirdRow = listOf("7", "8", "9", "×")
    val fourthRow = listOf("4", "5", "6", "-")
    val fifthRow = listOf("1", "2", "3", "+")
    val sixthRow = listOf("0", ".")


    if (portraitMode != Configuration.ORIENTATION_PORTRAIT) {
        return LandscapeLayout(
            historyState = historyState,
            historyViewModel = historyViewModel,
            viewModel = viewModel
        )
    }


    Scaffold(
        topBar = {
            AppBar(
                showBackArrow = false,
                onNavigate = onNavigate,
                onNavigateUp = onNavigateUp
            )
        },
    ) { _ ->
        LaunchedEffect(viewModel.displayText) {
            scrollState.animateScrollTo(scrollState.maxValue)
        }
        LaunchedEffect(viewModel.preview) {
            previewScrollState.animateScrollTo(previewScrollState.maxValue)
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(15.dp)
                .navigationBarsPadding()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter),
                verticalArrangement = Arrangement.spacedBy(9.dp),
            ) {
                Row(
                    modifier = Modifier
                        .horizontalScroll(previewScrollState)
                        .align(Alignment.End)
                ) {

                    Text(
                        text = viewModel.preview,
                        textAlign = TextAlign.End,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
                        fontSize = 32.sp,
                        fontFamily = GlobalFont,
                        maxLines = 1
                    )
                }
                Row(
                    modifier = Modifier
                        .horizontalScroll(scrollState)
                        .align(Alignment.End)
                ) {
//                    Text(
//                        text = if (decimal) formatNumber(viewModel.displayText) else viewModel.displayText,
//                        textAlign = TextAlign.End,
//                        modifier = Modifier
//                            .fillMaxWidth(),
//                        fontSize = 55.sp,
//                        fontFamily = GlobalFont
//                    )
                    DisableSoftKeyboard {
                        BasicTextField(
                            value = viewModel.displayText,
                            onValueChange = { viewModel.displayText = it },
                            singleLine = true,
                            textStyle = TextStyle(
                                textAlign = TextAlign.End,
                                color = MaterialTheme.colorScheme.onBackground,
                                fontSize = 53.sp
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
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(9.dp)
                ) {
                    secondRow.forEach {
                        CuteButton(
                            text = it,
                            modifier = Modifier
                                .aspectRatio(1f)
                                .weight(1f),
                            onClick = {
                                if (it == "C") viewModel.handleAction(CalcAction.ResetField)
                                else viewModel.handleAction(CalcAction.AddToField(it))
                            },
                            color = if (it == "C") ButtonDefaults.buttonColors(MaterialTheme.colorScheme.tertiaryContainer)
                            else ButtonDefaults.buttonColors(MaterialTheme.colorScheme.outlineVariant)
                        )
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(9.dp)
                ) {
                    thirdRow.forEach {
                        CuteButton(
                            text = it,
                            modifier = Modifier
                                .aspectRatio(1f)
                                .weight(1f),
                            onClick = {
                                viewModel.handleAction(CalcAction.AddToField(it))
                            },
                            color =
                            if (it == "×") ButtonDefaults.buttonColors(MaterialTheme.colorScheme.outlineVariant)
                            else ButtonDefaults.buttonColors(MaterialTheme.colorScheme.secondaryContainer),
                        )
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(9.dp)
                ) {
                    fourthRow.forEach {
                        CuteButton(
                            text = it,
                            modifier = Modifier
                                .aspectRatio(1f)
                                .weight(1f),
                            onClick = {
                                viewModel.handleAction(CalcAction.AddToField(it))
                            },
                            color =
                            if (it == "-") ButtonDefaults.buttonColors(MaterialTheme.colorScheme.outlineVariant)
                            else ButtonDefaults.buttonColors(MaterialTheme.colorScheme.secondaryContainer),
                        )
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(9.dp)
                ) {
                    fifthRow.forEach {
                        CuteButton(
                            text = it,
                            modifier = Modifier
                                .aspectRatio(1f)
                                .weight(1f),
                            onClick = {
                                viewModel.handleAction(CalcAction.AddToField(it))
                            },
                            color =
                            if (it == "+") ButtonDefaults.buttonColors(MaterialTheme.colorScheme.outlineVariant)
                            else ButtonDefaults.buttonColors(MaterialTheme.colorScheme.secondaryContainer),
                        )
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(9.dp)
                ) {
                    sixthRow.forEach {
                        CuteButton(
                            text = it,
                            modifier = Modifier
                                .aspectRatio(1f)
                                .weight(1f),
                            onClick = {
                                viewModel.handleAction(CalcAction.AddToField(it))
                            }
                        )
                    }
                    CuteIconButton(
                        modifier = Modifier
                            .aspectRatio(1f)
                            .weight(1f),
                        onClick = {
                            viewModel.handleAction(CalcAction.RemoveLast)
                        }
                    )
                    CuteButton(
                        text = "=",
                        color = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.tertiaryContainer),

                        modifier = Modifier
                            .aspectRatio(1f)
                            .weight(1f),
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

// https://stackoverflow.com/a/78720287
// Goated stackoverflow moment

@OptIn(ExperimentalComposeUiApi::class)
@Composable fun DisableSoftKeyboard(
    disable: Boolean = true,
    content: @Composable () -> Unit,
) {
    InterceptPlatformTextInput(
        interceptor = { request, nextHandler ->
            if (!disable) {
                nextHandler.startInputMethod(request)
            } else {
                awaitCancellation()
            }
        },
        content = content,
    )
}
