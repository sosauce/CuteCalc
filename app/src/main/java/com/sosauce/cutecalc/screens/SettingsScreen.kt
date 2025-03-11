package com.sosauce.cutecalc.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.sosauce.cutecalc.components.AboutCard
import com.sosauce.cutecalc.components.CuteNavigationButton
import com.sosauce.cutecalc.components.Misc
import com.sosauce.cutecalc.components.ThemeManagement
import com.sosauce.cutecalc.components.UI
import com.sosauce.cutecalc.logic.navigation.Screens

@Composable
fun SettingsScreen(
    onNavigate: (Screens) -> Unit
) {

    Scaffold(
        contentWindowInsets = WindowInsets.safeDrawing
    ) { pv ->
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                contentPadding = pv
            ) {
                item {
                    AboutCard()
                    ThemeManagement()
                    UI()
                    Misc()
                }
            }
            CuteNavigationButton { onNavigate(it) }
        }

    }
}