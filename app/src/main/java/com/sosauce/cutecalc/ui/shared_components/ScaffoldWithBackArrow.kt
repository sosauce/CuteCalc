package com.sosauce.cutecalc.ui.shared_components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sosauce.cutecalc.data.datastore.rememberShowBackButton
import com.sosauce.cutecalc.ui.screens.history.components.DeletionConfirmationDialog
import com.sosauce.cutecalc.ui.screens.history.components.HistoryActionButtons

@Composable
fun ScaffoldWithBackArrow(
    backArrowVisible: Boolean,
    onNavigateUp: () -> Unit,
    showHistoryActions: Boolean = false,
    onDeleteHistory: () -> Unit = {},
    content: @Composable (PaddingValues) -> Unit
) {
    val showBackButton by rememberShowBackButton()
    var showDeleteConfirmation by remember { mutableStateOf(false) }

    if (showDeleteConfirmation) {
        DeletionConfirmationDialog(
            onDismissRequest = { showDeleteConfirmation = false },
            onDelete = onDeleteHistory
        )
    }

    Scaffold(
        contentWindowInsets = WindowInsets.safeDrawing
    ) { pv ->
        Box(Modifier.fillMaxSize()) {
            content(pv)
            AnimatedVisibility(
                visible = backArrowVisible,
                modifier = Modifier
                    .navigationBarsPadding()
                    .padding(start = 15.dp)
                    .align(Alignment.BottomStart),
                enter = slideInVertically { it },
                exit = slideOutVertically { it }
            ) {
                if (showBackButton) {
                    CuteNavigationButton { onNavigateUp() }
                }
            }
            AnimatedVisibility(
                visible = backArrowVisible,
                modifier = Modifier
                    .navigationBarsPadding()
                    .padding(end = 15.dp)
                    .align(Alignment.BottomEnd),
                enter = slideInVertically { it },
                exit = slideOutVertically { it }
            ) {
                if (showHistoryActions) {
                    HistoryActionButtons { showDeleteConfirmation = true }
                }
            }
        }
    }
}