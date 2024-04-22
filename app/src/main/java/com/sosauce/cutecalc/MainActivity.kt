package com.sosauce.cutecalc

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.sosauce.cutecalc.logic.Nav
import com.sosauce.cutecalc.logic.PreferencesKeys
import com.sosauce.cutecalc.logic.dataStore
import com.sosauce.cutecalc.ui.theme.CuteCalcTheme
import com.sosauce.cutecalc.ui.theme.DarkAmoledColorPalette
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()

        enableEdgeToEdge()

        setContent {
            CuteCalcTheme {

                val context = LocalContext.current
                val themeDataStore = context.dataStore
                val themeFlow: Flow<String?> = themeDataStore.data
                    .map { preferences ->
                        preferences[PreferencesKeys.THEME]
                    }
                val theme by themeFlow.collectAsState(initial = null)

                val colors = when (theme) {
                    "Dark" -> if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) dynamicDarkColorScheme(
                        context
                    ) else darkColorScheme()

                    "Light" -> if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) dynamicLightColorScheme(
                        context
                    ) else lightColorScheme()

                    "Amoled" -> DarkAmoledColorPalette
                    else -> if (isSystemInDarkTheme()) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) dynamicDarkColorScheme(
                            context
                        ) else darkColorScheme()
                    } else {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) dynamicLightColorScheme(
                            context
                        ) else lightColorScheme()
                    }
                }

                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background),
                    contentColor = MaterialTheme.colorScheme.background
                ) {
                    MaterialTheme(
                        colorScheme = colors,
                        content = {
                            Nav()
                        }
                    )
                }
            }
        }
    }
}
