@file:OptIn(ExperimentalMaterial3Api::class)

package com.sosauce.cutecalc

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.sosauce.cutecalc.ui.theme.CuteCalcTheme
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            var userTheme by remember { mutableStateOf(UserTheme.SYSTEM_DEFAULT) }
            CuteCalcTheme(
                darkTheme = when(userTheme) {
                    UserTheme.SYSTEM_DEFAULT -> isSystemInDarkTheme()
                    UserTheme.DARK -> true
                    UserTheme.LIGHT -> false
                }
            ) {
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background),
                    contentColor = MaterialTheme.colorScheme.background
                ) { _ -> Nav(updateTheme = {userTheme = it})}
            }
        }
    }
}

