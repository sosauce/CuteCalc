package com.sosauce.cutecalc

import android.annotation.SuppressLint
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.sosauce.cutecalc.ecosys.CuteMusicReceiver
import com.sosauce.cutecalc.ecosys.EcosystemViewModel
import com.sosauce.cutecalc.logic.navigation.Nav
import com.sosauce.cutecalc.ui.theme.CuteCalcTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val ecosystemViewModel by viewModels<EcosystemViewModel>()
    private val cmReceiver = CuteMusicReceiver(
        action = {
            if (it.isEmpty()) {
                ecosystemViewModel.currentlyPlaying = null
            } else {
                ecosystemViewModel.currentlyPlaying = it
            }
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            registerReceiver(
                cmReceiver,
                IntentFilter("CM_CUR_PLAY_CHANGED"),
                RECEIVER_EXPORTED
            )
        }

        installSplashScreen()
        enableEdgeToEdge()
        setContent {
            CuteCalcTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { _ ->
                    MaterialTheme(
                        content = {
                            Nav(
                                ecosystemViewModel
                            )
                        }
                    )
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(cmReceiver)
    }
}
