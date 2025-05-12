package com.sosauce.cutecalc.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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

    val listState = rememberLazyListState()

    Scaffold(
        contentWindowInsets = WindowInsets.safeDrawing
    ) { pv ->
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                contentPadding = pv,
                state = listState
            ) {
                item {
                    AboutCard()
                    ThemeManagement()
                    UI()
                    Misc()
                }
            }
            AnimatedVisibility(
                visible = listState.canScrollForward,
                modifier = Modifier
                    .padding(start = 15.dp)
                    .align(Alignment.BottomStart),
                enter = slideInVertically { it },
                exit = slideOutVertically { it }
            ) {
                CuteNavigationButton(
                    modifier = Modifier.navigationBarsPadding()
                ) { onNavigate(Screens.MAIN) }
            }
        }

    }
}