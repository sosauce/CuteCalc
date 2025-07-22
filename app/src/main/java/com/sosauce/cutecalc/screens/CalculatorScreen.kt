package com.sosauce.cutecalc.screens

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.InterceptPlatformTextInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sosauce.cutecalc.R
import com.sosauce.cutecalc.components.CuteButton
import com.sosauce.cutecalc.components.CuteIconButton
import com.sosauce.cutecalc.components.CuteText
import com.sosauce.cutecalc.history.HistoryEvents
import com.sosauce.cutecalc.history.HistoryViewModel
import com.sosauce.cutecalc.logic.CalcAction
import com.sosauce.cutecalc.logic.CalcViewModel
import com.sosauce.cutecalc.logic.Evaluator
import com.sosauce.cutecalc.logic.formatOrNot
import com.sosauce.cutecalc.logic.navigation.Screens
import com.sosauce.cutecalc.logic.rememberDecimal
import com.sosauce.cutecalc.logic.rememberHistoryMaxItems
import com.sosauce.cutecalc.logic.rememberSaveErrorsToHistory
import com.sosauce.cutecalc.logic.rememberShowClearButton
import com.sosauce.cutecalc.logic.rememberUseHistory
import com.sosauce.cutecalc.logic.rememberUseSystemFont
import com.sosauce.cutecalc.ui.theme.GlobalFont
import kotlinx.coroutines.awaitCancellation


@SuppressLint("NewApi")
@Composable
fun CalculatorScreen(
    viewModel: CalcViewModel,
    historyViewModel: HistoryViewModel,
    onNavigate: (Screens) -> Unit,
) {
    val config = LocalConfiguration.current
    val portraitMode by remember { mutableIntStateOf(config.orientation) }
    val saveToHistory by rememberUseHistory()
    val useSystemFont by rememberUseSystemFont()
    val showClearButton by rememberShowClearButton()
    val historyMaxItems by rememberHistoryMaxItems()
    val saveErrorsToHistory by rememberSaveErrorsToHistory()
    val firstRow = arrayOf("!", "%", "√", "π")
    val secondRow = arrayOf(
        if (showClearButton) "C" else "(",
        if (showClearButton) viewModel.parenthesis else ")",
        "^",
        "/"
    )
    val thirdRow = arrayOf("7", "8", "9", "×")
    val fourthRow = arrayOf("4", "5", "6", "-")
    val fifthRow = arrayOf("1", "2", "3", "+")
    val sixthRow = arrayOf("0", ".")
    val decimalSetting by rememberDecimal()


    if (portraitMode != Configuration.ORIENTATION_PORTRAIT) {
        return CalculatorScreenLandscape(
            historyViewModel = historyViewModel,
            viewModel = viewModel
        )
    }


    Scaffold { pv ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(pv)
                .padding(horizontal = 10.dp)
        ) {
            Row(
                modifier = Modifier.align(Alignment.TopEnd)
            ) {
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
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter),
                verticalArrangement = Arrangement.spacedBy(9.dp),
            ) {


                Row(
                    modifier = Modifier
                        .align(Alignment.End)
                        .horizontalScroll(rememberScrollState())
                ) {
                    CuteText(
                        text = formatOrNot(viewModel.preview, decimalSetting),
                        fontSize = 32.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Row(
                    modifier = Modifier
                        .align(Alignment.End)
                ) {
                    DisableSoftKeyboard {
                        BasicTextField(
                            value = viewModel.displayText.copy(
                                text = formatOrNot(
                                    viewModel.displayText.text,
                                    decimalSetting
                                )
                            ), // Use copy function to keep the correct range, or else the cursor will just stick to the start everytime
                            onValueChange = { viewModel.displayText = it },
                            singleLine = true,
                            textStyle = TextStyle(
                                textAlign = TextAlign.End,
                                color = MaterialTheme.colorScheme.onBackground,
                                fontSize = 53.sp,
                                fontFamily = if (!useSystemFont) GlobalFont else null
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
                            color = if (it == "C") ButtonDefaults.buttonColors(MaterialTheme.colorScheme.inversePrimary)
                            else ButtonDefaults.buttonColors(MaterialTheme.colorScheme.secondaryContainer)
                        )
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(9.dp)
                ) {
                    thirdRow.forEach {

                        val isX = it == "×"

                        CuteButton(
                            text = it,
                            modifier = Modifier
                                .aspectRatio(1f)
                                .weight(1f),
                            onClick = {
                                viewModel.handleAction(CalcAction.AddToField(it))
                            },
                            color = if (isX) ButtonDefaults.buttonColors(MaterialTheme.colorScheme.secondaryContainer)
                            else ButtonDefaults.buttonColors(MaterialTheme.colorScheme.surfaceContainer),
                            textColor = if (isX) MaterialTheme.colorScheme.onSecondaryContainer
                            else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(9.dp)
                ) {
                    fourthRow.forEach {

                        val isMinus = it == "-"

                        CuteButton(
                            text = it,
                            modifier = Modifier
                                .aspectRatio(1f)
                                .weight(1f),
                            onClick = {
                                viewModel.handleAction(CalcAction.AddToField(it))
                            },
                            color =
                                if (isMinus) ButtonDefaults.buttonColors(MaterialTheme.colorScheme.secondaryContainer)
                                else ButtonDefaults.buttonColors(MaterialTheme.colorScheme.surfaceContainer),
                            textColor = if (isMinus) MaterialTheme.colorScheme.onSecondaryContainer
                            else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(9.dp)
                ) {
                    fifthRow.forEach {

                        val isPlus = it == "+"

                        CuteButton(
                            text = it,
                            modifier = Modifier
                                .aspectRatio(1f)
                                .weight(1f),
                            onClick = {
                                viewModel.handleAction(CalcAction.AddToField(it))
                            },
                            color =
                                if (isPlus) ButtonDefaults.buttonColors(MaterialTheme.colorScheme.secondaryContainer)
                                else ButtonDefaults.buttonColors(MaterialTheme.colorScheme.surfaceContainer),
                            textColor = if (isPlus) MaterialTheme.colorScheme.onSecondaryContainer
                            else MaterialTheme.colorScheme.onSurfaceVariant
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
                            },
                            textColor = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    CuteIconButton(
                        modifier = Modifier
                            .aspectRatio(1f)
                            .weight(1f),
                        onClick = {
                            viewModel.handleAction(CalcAction.Backspace)
                        },
                        onLongClick = {
                            viewModel.handleAction(CalcAction.ResetField)
                        }
                    )
                    CuteButton(
                        text = "=",
                        color = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.inversePrimary),
                        modifier = Modifier
                            .aspectRatio(1f)
                            .weight(1f),
                        onClick = {
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
                        }
                    )
                }
            }
        }
    }
}

// https://stackoverflow.com/a/78720287
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun DisableSoftKeyboard(
    content: @Composable () -> Unit
) {
    InterceptPlatformTextInput(
        interceptor = { _, _ ->
            awaitCancellation()
        },
        content = content
    )
}