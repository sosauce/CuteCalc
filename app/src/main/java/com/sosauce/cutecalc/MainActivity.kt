package com.sosauce.cutecalc

import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.sosauce.cutecalc.ecosys.CuteMusicReceiver
import com.sosauce.cutecalc.ecosys.EcosystemViewModel
import com.sosauce.cutecalc.history.HistoryDatabase
import com.sosauce.cutecalc.history.HistoryViewModel
import com.sosauce.cutecalc.logic.navigation.Nav
import com.sosauce.cutecalc.ui.theme.CuteCalcTheme

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

    // I don't its necessary having a di framework just for that
    private val historyDb by lazy {
        Room.databaseBuilder(
            context = application,
            klass = HistoryDatabase::class.java,
            name = "history.db"
        ).build()
    }

    private val historyViewModel by viewModels<HistoryViewModel>(
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return HistoryViewModel(historyDb.dao) as T
                }
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
                                ecosystemViewModel = ecosystemViewModel,
                                historyViewModel = historyViewModel
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
