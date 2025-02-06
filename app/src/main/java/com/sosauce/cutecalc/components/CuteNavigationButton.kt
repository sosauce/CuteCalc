package com.sosauce.cutecalc.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sosauce.cutecalc.logic.navigation.Screens

@Composable
fun BoxScope.CuteNavigationButton(
    onNavigate: (Screens) -> Unit
) {
    OutlinedIconButton(
        onClick = { onNavigate(Screens.MAIN) },
        modifier = Modifier
            .padding(start = 15.dp)
            .align(Alignment.BottomStart)
            .navigationBarsPadding(),
        colors = IconButtonDefaults.iconButtonColors(
            containerColor = MaterialTheme.colorScheme.background,
        ),
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colorScheme.onBackground.copy(0.2f)
        )
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
            contentDescription = null
        )
    }
}