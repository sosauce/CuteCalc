package com.sosauce.cutecalc.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
    Box(Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AboutCard()
            ThemeManagement()
            UI()
            Misc()
        }
        CuteNavigationButton { onNavigate(it) }
    }
}