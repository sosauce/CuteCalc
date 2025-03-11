package com.sosauce.cutecalc.components

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sosauce.cutecalc.logic.navigation.Screens

@Composable
fun BoxScope.CuteNavigationButton(
    onNavigate: (Screens) -> Unit
) {
    SmallFloatingActionButton(
        onClick = { onNavigate(Screens.MAIN) },
        modifier = Modifier
            .padding(start = 15.dp)
            .align(Alignment.BottomStart)
            .navigationBarsPadding(),
        shape = RoundedCornerShape(14.dp),
        containerColor = MaterialTheme.colorScheme.surfaceContainer
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
            contentDescription = null
        )
    }
}