@file:OptIn(ExperimentalComposeUiApi::class)

package com.sosauce.cutecalc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.ExperimentalComposeUiApi
import com.sosauce.cutecalc.ui.theme.CuteCalcTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CuteCalcTheme {
                CalculatorUI()
            }
        }
    }
}






