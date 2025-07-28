package com.sosauce.cutecalc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import com.sosauce.cutecalc.data.datastore.rememberAppTheme
import com.sosauce.cutecalc.data.datastore.rememberShowOnLockScreen
import com.sosauce.cutecalc.ui.navigation.Nav
import com.sosauce.cutecalc.ui.theme.CuteCalcTheme
import com.sosauce.cutecalc.utils.CuteTheme
import com.sosauce.cutecalc.utils.showOnLockScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        installSplashScreen()
        enableEdgeToEdge()
        setContent {
            val isSystemInDarkTheme = isSystemInDarkTheme()
            val theme by rememberAppTheme()
            val showOnLockScreen by rememberShowOnLockScreen()

            showOnLockScreen(showOnLockScreen)

            CuteCalcTheme {
                WindowCompat
                    .getInsetsController(window, window.decorView)
                    .apply {

                        val isLight =
                            if (theme == CuteTheme.SYSTEM) !isSystemInDarkTheme else theme == CuteTheme.LIGHT

                        isAppearanceLightStatusBars = isLight
                        isAppearanceLightNavigationBars = isLight
                    }

                Nav()
            }
        }
    }
}

