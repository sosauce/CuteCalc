@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Backspace
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.sosauce.cutecalc.ui.theme.GlobalFont

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LandscapeLayout(navController: NavController, state: CalcState) {

    val viewModel = viewModel<CalcViewModel>()


    Scaffold(
        topBar = {
            AppBar(
                title = "",
                showBackArrow = false,
                showMenuIcon = true,
                navController = navController
            )
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(15.dp)
        ) {
            val displayText = remember { state.field }
            val scrollState = rememberScrollState()

            LaunchedEffect(displayText) {
                scrollState.animateScrollTo(scrollState.maxValue)
            }

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
                    Text(
                        state.field,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp),
                        fontWeight = FontWeight.Light,
                        fontSize = 35.sp,
                        color = MaterialTheme.colorScheme.onBackground,
                        fontFamily = GlobalFont
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    TextButton(
                        onClick = { viewModel.handleAction(CalcAction.ResetField) },
                        modifier = Modifier
                            .weight(0.15f)
                    ) {
                        Text(
                            text = "",
                            color = MaterialTheme.colorScheme.onBackground,
                            fontSize = 35.sp,
                            fontFamily = GlobalFont,
                            modifier = Modifier.align(Alignment.CenterVertically)
                        )
                    }
                    FilledTonalButton(
                        onClick = { viewModel.handleAction(CalcAction.AddToField("0")) },
                        modifier = Modifier
                            .weight(0.15f)
                    ) {
                        Text(
                            text = "0",
                            color = MaterialTheme.colorScheme.onBackground,
                            fontSize = 35.sp,
                            fontFamily = GlobalFont,
                            modifier = Modifier.align(Alignment.CenterVertically)
                        )
                    }

                    FilledTonalButton(
                        onClick = { viewModel.handleAction(CalcAction.AddToField("1")) },
                        modifier = Modifier
                            .weight(0.15f)
                    ) {
                        Text(
                            text = "1",
                            color = MaterialTheme.colorScheme.onBackground,
                            fontSize = 35.sp,
                            fontFamily = GlobalFont,
                            modifier = Modifier.align(Alignment.CenterVertically)
                        )
                    }


                    FilledTonalButton(
                        onClick = { viewModel.handleAction(CalcAction.AddToField("2")) },
                        modifier = Modifier
                            .weight(0.15f)
                    ) {
                        Text(
                            text = "2",
                            color = MaterialTheme.colorScheme.onBackground,
                            fontSize = 35.sp,
                            fontFamily = GlobalFont,
                            modifier = Modifier.align(Alignment.CenterVertically)
                        )
                    }

                    Button(
                        onClick = { viewModel.handleAction(CalcAction.AddToField("*")) },
                        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.outlineVariant),
                        modifier = Modifier
                            .weight(0.15f)
                    ) {
                        Text(
                            text = "×",
                            color = MaterialTheme.colorScheme.onBackground,
                            fontSize = 35.sp,
                            fontFamily = GlobalFont,
                            modifier = Modifier.align(Alignment.CenterVertically)
                        )
                    }

                    Button(
                        onClick = {
                            val openParenCount = state.field.count { it == '(' }
                            val closeParenCount = state.field.count { it == ')' }
                            val nextParen = if (openParenCount > closeParenCount) ")" else "("

                            viewModel.handleAction(CalcAction.AddToField(nextParen))
                        },
                        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.outlineVariant),
                        modifier = Modifier
                            .weight(0.15f)
                    ) {
                        val openParenCount = state.field.count { it == '(' }
                        val closeParenCount = state.field.count { it == ')' }
                        Text(
                            text = if (openParenCount > closeParenCount) ")" else "(",
                            color = MaterialTheme.colorScheme.onBackground,
                            fontSize = 35.sp,
                            fontFamily = GlobalFont
                        )
                    }

                    Button(
                        onClick = { viewModel.handleAction(CalcAction.ResetField) },
                        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.tertiaryContainer),
                        modifier = Modifier
                            .weight(0.15f)
                    ) {
                        Text(
                            text = "C",
                            color = MaterialTheme.colorScheme.onBackground,
                            fontSize = 35.sp,
                            fontFamily = GlobalFont,
                            modifier = Modifier.align(Alignment.CenterVertically)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    FilledTonalButton(
                        onClick = { viewModel.handleAction(CalcAction.AddToField("3")) },
                        modifier = Modifier
                            .weight(0.15f)
                    ) {
                        Text(
                            text = "3",
                            color = MaterialTheme.colorScheme.onBackground,
                            fontSize = 35.sp,
                            fontFamily = GlobalFont,
                            modifier = Modifier.align(Alignment.CenterVertically)
                        )
                    }

                    FilledTonalButton(
                        onClick = { viewModel.handleAction(CalcAction.AddToField("4")) },
                        modifier = Modifier
                            .weight(0.15f)
                    ) {
                        Text(
                            text = "4",
                            color = MaterialTheme.colorScheme.onBackground,
                            fontSize = 35.sp,
                            fontFamily = GlobalFont,
                            modifier = Modifier.align(Alignment.CenterVertically)
                        )
                    }


                    FilledTonalButton(
                        onClick = { viewModel.handleAction(CalcAction.AddToField("5")) },
                        modifier = Modifier
                            .weight(0.15f)
                    ) {
                        Text(
                            text = "5",
                            color = MaterialTheme.colorScheme.onBackground,
                            fontSize = 35.sp,
                            fontFamily = GlobalFont,
                            modifier = Modifier.align(Alignment.CenterVertically)
                        )
                    }

                    FilledTonalButton(
                        onClick = { viewModel.handleAction(CalcAction.AddToField("6")) },
                        modifier = Modifier
                            .weight(0.15f)
                    ) {
                        Text(
                            text = "6",
                            color = MaterialTheme.colorScheme.onBackground,
                            fontSize = 35.sp,
                            fontFamily = GlobalFont,
                            modifier = Modifier.align(Alignment.CenterVertically)
                        )
                    }

                    Button(
                        onClick = { viewModel.handleAction(CalcAction.AddToField("+")) },
                        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.outlineVariant),
                        modifier = Modifier
                            .weight(0.15f)
                    ) {
                        Text(
                            text = "+",
                            color = MaterialTheme.colorScheme.onBackground,
                            fontSize = 35.sp,
                            fontFamily = GlobalFont,
                            modifier = Modifier.align(Alignment.CenterVertically)
                        )
                    }

                    Button(
                        onClick = { viewModel.handleAction(CalcAction.AddToField("%")) },
                        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.outlineVariant),
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
                    Button(
                        onClick = { viewModel.handleAction(CalcAction.RemoveLast) },
                        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.tertiaryContainer),
                        modifier = Modifier
                            .weight(0.15f)
                            .height(64.dp)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Outlined.Backspace,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier.size(35.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.Bottom
                ) {
                    FilledTonalButton(
                        onClick = { viewModel.handleAction(CalcAction.AddToField("7")) },
                        modifier = Modifier
                            .weight(0.15f)
                    ) {
                        Text(
                            text = "7",
                            color = MaterialTheme.colorScheme.onBackground,
                            fontSize = 35.sp,
                            fontFamily = GlobalFont,
                            modifier = Modifier.align(Alignment.CenterVertically)
                        )
                    }

                    FilledTonalButton(
                        onClick = { viewModel.handleAction(CalcAction.AddToField("8")) },
                        modifier = Modifier
                            .weight(0.15f)
                    ) {
                        Text(
                            text = "8",
                            color = MaterialTheme.colorScheme.onBackground,
                            fontSize = 35.sp,
                            fontFamily = GlobalFont,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.align(Alignment.CenterVertically)
                        )
                    }

                    FilledTonalButton(
                        onClick = { viewModel.handleAction(CalcAction.AddToField("9")) },
                        modifier = Modifier
                            .weight(0.15f)
                    ) {
                        Text(
                            text = "9",
                            color = MaterialTheme.colorScheme.onBackground,
                            fontSize = 35.sp,
                            fontFamily = GlobalFont,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.align(Alignment.CenterVertically)
                        )
                    }


                    FilledTonalButton(
                        onClick = { viewModel.handleAction(CalcAction.AddToField(".")) },
                        modifier = Modifier
                            .weight(0.15f)
                    ) {
                        Text(
                            text = ".",
                            color = MaterialTheme.colorScheme.onBackground,
                            fontSize = 35.sp,
                            fontFamily = GlobalFont,
                            textAlign = TextAlign.Center
                        )
                    }

                    Button(
                        onClick = { viewModel.handleAction(CalcAction.AddToField("-")) },
                        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.outlineVariant),
                        modifier = Modifier
                            .weight(0.15f)
                    ) {
                        Text(
                            text = "-",
                            color = MaterialTheme.colorScheme.onBackground,
                            fontSize = 35.sp,
                            fontFamily = GlobalFont,
                            modifier = Modifier.align(Alignment.CenterVertically)
                        )
                    }

                    Button(
                        onClick = { viewModel.handleAction(CalcAction.AddToField("/")) },
                        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.outlineVariant),
                        modifier = Modifier
                            .weight(0.15f)
                    ) {
                        Text(
                            text = "/",
                            color = MaterialTheme.colorScheme.onBackground,
                            fontSize = 35.sp,
                            fontFamily = GlobalFont,
                            modifier = Modifier.align(Alignment.CenterVertically)
                        )
                    }

                    Button(
                        onClick = { viewModel.handleAction(CalcAction.GetResult) },
                        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.tertiaryContainer),
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(0.15f)
                    ) {
                        Text(
                            text = "=",
                            color = MaterialTheme.colorScheme.onBackground,
                            fontSize = 35.sp,
                            fontFamily = GlobalFont,
                            modifier = Modifier.align(Alignment.CenterVertically)
                        )
                    }
                }
            }
        }
    }
}