@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class,
    ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class
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

@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "UnusedMaterial3ScaffoldPaddingParameter",
    "AutoboxingStateCreation"
)
@Composable
fun CalculatorUI (navController: NavController, state: CalcState, handleAction: (CalcAction) -> Unit) {
    val viewModel = viewModel<CalcViewModel>()
    val appState = viewModel.state
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
                    verticalArrangement = Arrangement.spacedBy(9.dp),
                ) {
                    Row(
                        modifier = Modifier
                            .horizontalScroll(rememberScrollState())
                            .align(Alignment.End)
                    ) {
                        Text(
                            text = state.field,
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
                        horizontalArrangement = Arrangement.spacedBy(9.dp)
                    ) {
                        Button(
                            onClick = { viewModel.handleAction(CalcAction.ResetField) },
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
                            onClick = { viewModel.handleAction(CalcAction.RemoveLast) },
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
                            onClick = { viewModel.handleAction(CalcAction.AddToField("%")) },
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
                            onClick = { viewModel.handleAction(CalcAction.AddToField("/")) },
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
                        FilledTonalButton(
                            onClick = { viewModel.handleAction(CalcAction.AddToField("7")) },
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
                            onClick = { viewModel.handleAction(CalcAction.AddToField("8")) },
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
                            onClick = { viewModel.handleAction(CalcAction.AddToField("9")) },
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
                            onClick = { viewModel.handleAction(CalcAction.AddToField("*")) },
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
                        FilledTonalButton(
                            onClick = { viewModel.handleAction(CalcAction.AddToField("4")) },
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
                            onClick = { viewModel.handleAction(CalcAction.AddToField("5")) },
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
                            onClick = { viewModel.handleAction(CalcAction.AddToField("6")) },
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
                            onClick = { viewModel.handleAction(CalcAction.AddToField("-")) },
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
                        FilledTonalButton(
                            onClick = { viewModel.handleAction(CalcAction.AddToField("1")) },
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
                            onClick = { viewModel.handleAction(CalcAction.AddToField("2")) },
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
                            onClick = { viewModel.handleAction(CalcAction.AddToField("3")) },
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
                            onClick = { viewModel.handleAction(CalcAction.AddToField("+")) },
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
                        FilledTonalButton(
                            onClick = { viewModel.handleAction(CalcAction.AddToField("0")) },
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
                            onClick = { viewModel.handleAction(CalcAction.AddToField(".")) },
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
                            onClick = { viewModel.handleAction(CalcAction.GetResult) },
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
       LandscapeLayout(navController = navController, state = state)
    }
}