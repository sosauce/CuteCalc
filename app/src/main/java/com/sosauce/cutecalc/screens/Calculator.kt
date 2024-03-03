package com.sosauce.cutecalc.screens

import android.annotation.SuppressLint
import android.content.Context
import android.content.Context.VIBRATOR_SERVICE
import android.content.res.Configuration
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.sosauce.cutecalc.AppBar
import com.sosauce.cutecalc.logic.CalcAction
import com.sosauce.cutecalc.logic.CalcState
import com.sosauce.cutecalc.logic.CalcViewModel
import com.sosauce.cutecalc.logic.dataStore
import com.sosauce.cutecalc.logic.getButtonVibrationSetting
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
    state: CalcState
) {
    val viewModel = viewModel<CalcViewModel>()
    val config = LocalConfiguration.current
    val context = LocalContext.current
    val portraitMode = remember { mutableStateOf(config.orientation) }
    val buttonVibrationEnabledFlow: Flow<Boolean> = getButtonVibrationSetting(context.dataStore)
    val buttonVibrationEnabledState: State<Boolean> =
        buttonVibrationEnabledFlow.collectAsState(initial = false)

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
                .background(MaterialTheme.colorScheme.background)
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
                        text = state.formattedField(),
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp),
                        fontWeight = FontWeight.Light,
                        fontSize = 55.sp,
                        color = MaterialTheme.colorScheme.onBackground,
                        fontFamily = GlobalFont
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = " ! ",
                        color = MaterialTheme.colorScheme.onBackground,
                        fontSize = 30.sp,
                        fontFamily = GlobalFont,
                        modifier = Modifier
                            .clickable {
                                viewModel.handleAction(CalcAction.AddToField("!"))
                            }
                    )
                    Text(
                        text = " % ",
                        color = MaterialTheme.colorScheme.onBackground,
                        fontSize = 30.sp,
                        fontFamily = GlobalFont,
                        modifier = Modifier
                            .clickable {
                                viewModel.handleAction(CalcAction.AddToField("%"))
                            }
                    )
                    Text(
                        text = " √ ",
                        color = MaterialTheme.colorScheme.onBackground,
                        fontSize = 30.sp,
                        fontFamily = GlobalFont,
                        modifier = Modifier
                            .clickable {
                                viewModel.handleAction(CalcAction.AddToField("√"))
                            }
                    )
                    Text(
                        text = " π ",
                        color = MaterialTheme.colorScheme.onBackground,
                        fontSize = 30.sp,
                        fontFamily = GlobalFont,
                        modifier = Modifier
                            .clickable {
                                viewModel.handleAction(CalcAction.AddToField("PI"))
                            }
                    )


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
                            fontSize = 40.sp,
                            fontFamily = GlobalFont
                        )
                    }

                    Button(
                        onClick = {
                            val openParenCount = state.field.count { it == '(' }
                            val closeParenCount = state.field.count { it == ')' }
                            val nextParen = if (openParenCount > closeParenCount) ")" else "("

                            viewModel.handleAction(CalcAction.AddToField(nextParen))
                            if (buttonVibrationEnabledState.value) vibration()
                        },
                        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.outlineVariant),
                        modifier = Modifier
                            .aspectRatio(1f)
                            .weight(1f)
                    ) {
                        val openParenCount = state.field.count { it == '(' }
                        val closeParenCount = state.field.count { it == ')' }
                        Text(
                            text = if (openParenCount > closeParenCount) ")" else "(",
                            color = MaterialTheme.colorScheme.onBackground,
                            fontSize = 40.sp,
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
                            fontSize = 40.sp,
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
                            fontSize = 40.sp,
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
                            fontSize = 40.sp,
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
                            fontSize = 40.sp,
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
                            fontSize = 40.sp,
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
                            fontSize = 40.sp,
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
                            fontSize = 40.sp,
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
                            fontSize = 40.sp,
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
                            fontSize = 40.sp,
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
                            fontSize = 40.sp,
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
                            fontSize = 40.sp,
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
                            fontSize = 40.sp,
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
                            fontSize = 40.sp,
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
                            fontSize = 40.sp,
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
                            fontSize = 40.sp,
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
                            .weight(1f)
                    ) {
                        Text(
                            text = ".",
                            color = MaterialTheme.colorScheme.onBackground,
                            fontSize = 40.sp,
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
                            fontSize = 40.sp,
                            fontFamily = GlobalFont
                        )
                    }
                }
            }
        }
    }
}
