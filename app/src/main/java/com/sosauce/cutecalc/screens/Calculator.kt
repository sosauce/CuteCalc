@file:OptIn(ExperimentalFoundationApi::class)

package com.sosauce.cutecalc.screens

import android.annotation.SuppressLint
import android.content.Context
import android.content.Context.VIBRATOR_SERVICE
import android.content.res.Configuration
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Backspace
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.sosauce.cutecalc.AppBar
import com.sosauce.cutecalc.logic.CalcAction
import com.sosauce.cutecalc.logic.CalcState
import com.sosauce.cutecalc.logic.CalcViewModel
import com.sosauce.cutecalc.logic.dataStore
import com.sosauce.cutecalc.logic.getButtonVibrationSetting
import com.sosauce.cutecalc.logic.getDecimalFormattingSetting
import com.sosauce.cutecalc.ui.theme.GlobalFont
import kotlinx.coroutines.flow.Flow

@SuppressLint(
    "UnusedMaterialScaffoldPaddingParameter",
    "UnusedMaterial3ScaffoldPaddingParameter",
    "AutoboxingStateCreation"
)
@Composable
fun CalculatorUI(
    navController: NavController,
    state: CalcState,
    viewModel: CalcViewModel
) {
    val config = LocalConfiguration.current
    val context = LocalContext.current
    val portraitMode = remember { mutableStateOf(config.orientation) }
    val buttonVibrationEnabledFlow: Flow<Boolean> = getButtonVibrationSetting(context.dataStore)
    val buttonVibrationEnabledState: State<Boolean> =
        buttonVibrationEnabledFlow.collectAsState(initial = false)
    val buttonDecimalEnabledFlow: Flow<Boolean> = getDecimalFormattingSetting(context.dataStore)
    val buttonDecimalEnabledState: State<Boolean> =
        buttonDecimalEnabledFlow.collectAsState(initial = false)

    fun vibration() {
        if (!buttonVibrationEnabledState.value) return
        val vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            (context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager).defaultVibrator
        } else {
            @Suppress("DEPRECATION") context.getSystemService(VIBRATOR_SERVICE) as Vibrator
        }
        val vibrationEffect = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            VibrationEffect.createOneShot(100, 90)
        } else {
            TODO("VERSION.SDK_INT < O")
        }
        vibrator.vibrate(vibrationEffect)
    }

    if (portraitMode.value != Configuration.ORIENTATION_PORTRAIT) {
        return LandscapeLayout(navController = navController, state = state)
    }

    Scaffold(
        topBar = {
            AppBar(
                title = "",
                showBackArrow = false,
                showMenuIcon = true,
                navController = navController
            )
        },
    ) {
        val displayText = remember { state.field }
        val scrollState = rememberScrollState()

        LaunchedEffect(displayText) {
            scrollState.animateScrollTo(scrollState.maxValue)
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
                        .horizontalScroll(scrollState)
                        .align(Alignment.End)
                ) {
                    LaunchedEffect(state.field) {
                        scrollState.animateScrollTo(scrollState.maxValue)
                    }

                    Text(
                        text = if (buttonDecimalEnabledState.value) state.formattedField() else state.field,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp),
                        fontWeight = FontWeight.Light,
                        fontSize = 55.sp,
                        fontFamily = GlobalFont
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TextButton(
                        onClick = {
                            viewModel.handleAction(CalcAction.AddToField("!"))
                            if (buttonVibrationEnabledState.value) vibration()
                        },
                        modifier = Modifier
                            .weight(0.15f)
                    ) {
                        Text(
                            text = "!",
                            color = MaterialTheme.colorScheme.onBackground,
                            fontSize = 35.sp,
                            fontFamily = GlobalFont,
                            modifier = Modifier.align(Alignment.CenterVertically)
                        )
                    }
                    TextButton(
                        onClick = {
                            viewModel.handleAction(CalcAction.AddToField("%"))
                            if (buttonVibrationEnabledState.value) vibration()
                        },
                        modifier = Modifier
                            .weight(0.15f)
                    ) {
                        Text(
                            text = "%",
                            color = MaterialTheme.colorScheme.onBackground,
                            fontSize = 35.sp,
                            fontFamily = GlobalFont,
                            modifier = Modifier.align(Alignment.CenterVertically)
                        )
                    }
                    TextButton(
                        onClick = {
                            viewModel.handleAction(CalcAction.AddToField("√"))
                            if (buttonVibrationEnabledState.value) vibration()
                        },
                        modifier = Modifier
                            .weight(0.15f)
                    ) {
                        Text(
                            text = "√",
                            color = MaterialTheme.colorScheme.onBackground,
                            fontSize = 35.sp,
                            fontFamily = GlobalFont,
                            modifier = Modifier.align(Alignment.CenterVertically)
                        )
                    }
                    TextButton(
                        onClick = {
                            viewModel.handleAction(CalcAction.AddToField("PI"))
                            if (buttonVibrationEnabledState.value) vibration()
                        },
                        modifier = Modifier
                            .weight(0.15f)
                    ) {
                        Text(
                            text = "π",
                            color = MaterialTheme.colorScheme.onBackground,
                            fontSize = 35.sp,
                            fontFamily = GlobalFont,
                            modifier = Modifier.align(Alignment.CenterVertically)
                        )
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(9.dp)
                ) {
                    Button(
                        onClick = {
                            viewModel.handleAction(CalcAction.ResetField)
                            if (buttonVibrationEnabledState.value) vibration()
                        },
                        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.tertiaryContainer),
                        modifier = Modifier
                            .aspectRatio(1f)
                            .weight(1f)
                    ) {
                        Text(
                            text = "C",
                            color = MaterialTheme.colorScheme.onBackground,
                            fontSize = 35.sp,
                            fontFamily = GlobalFont
                        )
                    }

                    Button(
                        onClick = {
                            val parenthesis = if (state.field.count { it == '(' } > state.field.count { it == ')' }) ")" else "("

                            viewModel.handleAction(CalcAction.AddToField(parenthesis))
                            if (buttonVibrationEnabledState.value) vibration()
                        },
                        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.outlineVariant),
                        modifier = Modifier
                            .aspectRatio(1f)
                            .weight(1f)
                    ) {
                        Text(
                            text = if (state.field.count { it == '(' } > state.field.count { it == ')' }) ")" else "(",
                            color = MaterialTheme.colorScheme.onBackground,
                            fontSize = 35.sp,
                            fontFamily = GlobalFont,
                        )
                    }



                    Button(
                        onClick = {
                            viewModel.handleAction(CalcAction.AddToField("^"))
                            if (buttonVibrationEnabledState.value) vibration()
                        },
                        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.outlineVariant),
                        modifier = Modifier
                            .aspectRatio(1f)
                            .weight(1f)
                    ) {
                        Text(
                            text = "^",
                            color = MaterialTheme.colorScheme.onBackground,
                            fontSize = 35.sp,
                            fontFamily = GlobalFont
                        )
                    }

                    Button(
                        onClick = {
                            viewModel.handleAction(CalcAction.AddToField("/"))
                            if (buttonVibrationEnabledState.value) vibration()
                        },
                        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.outlineVariant),
                        modifier = Modifier
                            .aspectRatio(1f)
                            .weight(1f)
                    ) {
                        Text(
                            text = "/",
                            color = MaterialTheme.colorScheme.onBackground,
                            fontSize = 35.sp,
                            fontFamily = GlobalFont
                        )
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(9.dp)
                ) {
                    Button(
                        onClick = {
                            viewModel.handleAction(CalcAction.AddToField("7"))
                            if (buttonVibrationEnabledState.value) vibration()
                        },
                        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.secondaryContainer),
                        modifier = Modifier
                            .aspectRatio(1f)
                            .weight(1f)
                    ) {
                        Text(
                            text = "7",
                            color = MaterialTheme.colorScheme.onBackground,
                            fontSize = 35.sp,
                            fontFamily = GlobalFont
                        )
                    }

                    Button(
                        onClick = {
                            viewModel.handleAction(CalcAction.AddToField("8"))
                            if (buttonVibrationEnabledState.value) vibration()
                        },
                        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.secondaryContainer),
                        modifier = Modifier
                            .aspectRatio(1f)
                            .weight(1f)
                    ) {
                        Text(
                            text = "8",
                            color = MaterialTheme.colorScheme.onBackground,
                            fontSize = 35.sp,
                            fontFamily = GlobalFont
                        )
                    }

                    Button(
                        onClick = {
                            viewModel.handleAction(CalcAction.AddToField("9"))
                            if (buttonVibrationEnabledState.value) vibration()
                        },
                        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.secondaryContainer),
                        modifier = Modifier
                            .aspectRatio(1f)
                            .weight(1f)
                    ) {
                        Text(
                            text = "9",
                            color = MaterialTheme.colorScheme.onBackground,
                            fontSize = 35.sp,
                            fontFamily = GlobalFont
                        )
                    }

                    Button(
                        onClick = {
                            viewModel.handleAction(CalcAction.AddToField("×"))
                            if (buttonVibrationEnabledState.value) vibration()
                        },
                        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.outlineVariant),
                        modifier = Modifier
                            .aspectRatio(1f)
                            .weight(1f)
                    ) {
                        Text(
                            text = "×",
                            color = MaterialTheme.colorScheme.onBackground,
                            fontSize = 35.sp,
                            fontFamily = GlobalFont
                        )
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(9.dp)
                ) {
                    Button(
                        onClick = {
                            viewModel.handleAction(CalcAction.AddToField("4"))
                            if (buttonVibrationEnabledState.value) vibration()
                        },
                        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.secondaryContainer),
                        modifier = Modifier
                            .aspectRatio(1f)
                            .weight(1f)
                    ) {
                        Text(
                            text = "4",
                            color = MaterialTheme.colorScheme.onBackground,
                            fontSize = 35.sp,
                            fontFamily = GlobalFont
                        )
                    }

                    Button(
                        onClick = {
                            viewModel.handleAction(CalcAction.AddToField("5"))
                            if (buttonVibrationEnabledState.value) vibration()
                        },
                        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.secondaryContainer),
                        modifier = Modifier
                            .aspectRatio(1f)
                            .weight(1f)
                    ) {
                        Text(
                            text = "5",
                            color = MaterialTheme.colorScheme.onBackground,
                            fontSize = 35.sp,
                            fontFamily = GlobalFont
                        )
                    }

                    Button(
                        onClick = {
                            viewModel.handleAction(CalcAction.AddToField("6"))
                            if (buttonVibrationEnabledState.value) vibration()
                        },
                        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.secondaryContainer),
                        modifier = Modifier
                            .aspectRatio(1f)
                            .weight(1f)
                    ) {
                        Text(
                            text = "6",
                            color = MaterialTheme.colorScheme.onBackground,
                            fontSize = 35.sp,
                            fontFamily = GlobalFont
                        )
                    }

                    Button(
                        onClick = {
                            viewModel.handleAction(CalcAction.AddToField("-"))
                            if (buttonVibrationEnabledState.value) vibration()
                        },
                        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.outlineVariant),
                        modifier = Modifier
                            .aspectRatio(1f)
                            .weight(1f)
                    ) {
                        Text(
                            text = "-",
                            color = MaterialTheme.colorScheme.onBackground,
                            fontSize = 35.sp,
                            fontFamily = GlobalFont
                        )
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(9.dp)
                ) {
                    Button(
                        onClick = {
                            viewModel.handleAction(CalcAction.AddToField("1"))
                            if (buttonVibrationEnabledState.value) vibration()
                        },
                        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.secondaryContainer),
                        modifier = Modifier
                            .aspectRatio(1f)
                            .weight(1f)
                    ) {
                        Text(
                            text = "1",
                            color = MaterialTheme.colorScheme.onBackground,
                            fontSize = 35.sp,
                            fontFamily = GlobalFont
                        )
                    }

                    Button(
                        onClick = {
                            viewModel.handleAction(CalcAction.AddToField("2"))
                            if (buttonVibrationEnabledState.value) vibration()
                        },
                        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.secondaryContainer),
                        modifier = Modifier
                            .aspectRatio(1f)
                            .weight(1f)
                    ) {
                        Text(
                            text = "2",
                            color = MaterialTheme.colorScheme.onBackground,
                            fontSize = 35.sp,
                            fontFamily = GlobalFont
                        )
                    }
                    Button(
                        onClick = {
                            viewModel.handleAction(CalcAction.AddToField("3"))
                            if (buttonVibrationEnabledState.value) vibration()
                        },
                        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.secondaryContainer),
                        modifier = Modifier
                            .aspectRatio(1f)
                            .weight(1f)
                    ) {
                        Text(
                            text = "3",
                            color = MaterialTheme.colorScheme.onBackground,
                            fontSize = 35.sp,
                            fontFamily = GlobalFont
                        )
                    }

                    Button(
                        onClick = {
                            viewModel.handleAction(CalcAction.AddToField("+"))
                            if (buttonVibrationEnabledState.value) vibration()
                        },
                        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.outlineVariant),
                        modifier = Modifier
                            .aspectRatio(1f)
                            .weight(1f)
                    ) {
                        Text(
                            text = "+",
                            color = MaterialTheme.colorScheme.onBackground,
                            fontSize = 35.sp,
                            fontFamily = GlobalFont
                        )
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(9.dp)
                ) {
                    Button(
                        onClick = {
                            viewModel.handleAction(CalcAction.AddToField("0"))
                            if (buttonVibrationEnabledState.value) vibration()
                        },
                        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.secondaryContainer),
                        modifier = Modifier
                            .aspectRatio(1f)
                            .weight(1f)
                    ) {
                        Text(
                            text = "0",
                            color = MaterialTheme.colorScheme.onBackground,
                            fontSize = 35.sp,
                            fontFamily = GlobalFont
                        )
                    }

                    Button(
                        onClick = {
                            viewModel.handleAction(CalcAction.AddToField("."))
                            if (buttonVibrationEnabledState.value) vibration()
                        },
                        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.secondaryContainer),
                        modifier = Modifier
                            .aspectRatio(1f)
                            .weight(1f),
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Text(
                            text = ".",
                            color = MaterialTheme.colorScheme.onBackground,
                            fontSize = 35.sp,
                            fontFamily = GlobalFont
                        )
                    }

                    Button(
                        onClick = {
                            viewModel.handleAction(CalcAction.RemoveLast)
                            if (buttonVibrationEnabledState.value) vibration()
                        },
                        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.secondaryContainer),
                        modifier = Modifier
                            .aspectRatio(1f)
                            .weight(1f)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Outlined.Backspace,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier.size(35.dp)
                        )
                    }


                    Button(
                        onClick = {
                            viewModel.handleAction(CalcAction.GetResult)
                            if (buttonVibrationEnabledState.value) vibration()
                        },
                        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.tertiaryContainer),
                        modifier = Modifier
                            .aspectRatio(1f)
                            .weight(1f)
                    ) {
                        Text(
                            text = "=",
                            color = MaterialTheme.colorScheme.onBackground,
                            fontSize = 35.sp,
                            fontFamily = GlobalFont
                        )
                    }
                }
            }
        }
    }
}