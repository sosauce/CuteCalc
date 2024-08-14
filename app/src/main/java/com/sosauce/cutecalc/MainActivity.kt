package com.sosauce.cutecalc

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.sosauce.cutecalc.logic.navigation.Nav
import com.sosauce.cutecalc.ui.theme.CuteCalcTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        enableEdgeToEdge()
        setContent {
            CuteCalcTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) {
                    MaterialTheme(
                        content = {
                            Nav()
                        }
                    )
                }
            }
        }
    }
}
