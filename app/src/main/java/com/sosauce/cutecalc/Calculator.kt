@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class,
    ExperimentalMaterial3Api::class
)

package com.sosauce.cutecalc

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.sosauce.cutecalc.ui.theme.GlobalFont

@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CalculatorUI (navController:  NavController) {
    val viewModel = viewModel<CalculatorViewModel>()
    val state = viewModel.state
    val buttonSpacing = 9.dp
    val displayText = state.number1 + (state.operation?.symbol ?: "") + state.number2
    val config = LocalConfiguration.current
    val portraitMode = remember { mutableStateOf(config.orientation) }

    if (portraitMode.value == Configuration.ORIENTATION_PORTRAIT) {
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
                    Row(
                        modifier = Modifier
                            .horizontalScroll(rememberScrollState())
                            .align(Alignment.End)
                    ) {
                        Text(
                            text = displayText,
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
                        horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
                    ) {
                        Button(
                            onClick = { viewModel.onAction(CalculatorAction.Clear) },
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
                            onClick = { viewModel.onAction(CalculatorAction.Delete) },
                            colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.outlineVariant),
                            modifier = Modifier
                                .aspectRatio(1f)
                                .weight(1f)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.backspace_outline),
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onBackground,
                                modifier = Modifier.size(40.dp)
                            )
                        }

                        Button(
                            onClick = {
                                viewModel.onAction(
                                    CalculatorAction.Operation(
                                        CalculatorOperation.Percentage
                                    )
                                )
                            },
                            colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.outlineVariant),
                            modifier = Modifier
                                .aspectRatio(1f)
                                .weight(1f)
                        ) {
                            Text(
                                text = "%",
                                maxLines = 1,
                                color = MaterialTheme.colorScheme.onBackground,
                                fontSize = 40.sp,
                                fontFamily = GlobalFont
                            )
                        }

                        Button(
                            onClick = {
                                viewModel.onAction(
                                    CalculatorAction.Operation(
                                        CalculatorOperation.Divide
                                    )
                                )
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
                        horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
                    ) {
                        FilledTonalButton(
                            onClick = { viewModel.onAction(CalculatorAction.Number(7)) },
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

                        FilledTonalButton(
                            onClick = { viewModel.onAction(CalculatorAction.Number(8)) },
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

                        FilledTonalButton(
                            onClick = { viewModel.onAction(CalculatorAction.Number(9)) },
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
                                viewModel.onAction(
                                    CalculatorAction.Operation(
                                        CalculatorOperation.Multiply
                                    )
                                )
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
                        horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
                    ) {
                        FilledTonalButton(
                            onClick = { viewModel.onAction(CalculatorAction.Number(4)) },
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

                        FilledTonalButton(
                            onClick = { viewModel.onAction(CalculatorAction.Number(5)) },
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

                        FilledTonalButton(
                            onClick = { viewModel.onAction(CalculatorAction.Number(6)) },
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
                                viewModel.onAction(
                                    CalculatorAction.Operation(
                                        CalculatorOperation.Subtract
                                    )
                                )
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
                        horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
                    ) {
                        FilledTonalButton(
                            onClick = { viewModel.onAction(CalculatorAction.Number(1)) },
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

                        FilledTonalButton(
                            onClick = { viewModel.onAction(CalculatorAction.Number(2)) },
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

                        FilledTonalButton(
                            onClick = { viewModel.onAction(CalculatorAction.Number(3)) },
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
                                viewModel.onAction(
                                    CalculatorAction.Operation(
                                        CalculatorOperation.Add
                                    )
                                )
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
                        horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
                    ) {
                        FilledTonalButton(
                            onClick = { viewModel.onAction(CalculatorAction.Number(0)) },
                            modifier = Modifier
                                .aspectRatio(2f)
                                .weight(2f)
                        ) {
                            Text(
                                text = "0",
                                color = MaterialTheme.colorScheme.onBackground,
                                fontSize = 40.sp,
                                fontFamily = GlobalFont
                            )
                        }

                        FilledTonalButton(
                            onClick = { viewModel.onAction(CalculatorAction.Decimal) },
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
                            onClick = { viewModel.onAction(CalculatorAction.Calculate) },
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
    } else {
        LandscapeLayout(navController = navController)
    }
}