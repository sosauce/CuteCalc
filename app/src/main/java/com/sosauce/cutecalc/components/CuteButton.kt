package com.sosauce.cutecalc.components

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Backspace
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sosauce.cutecalc.logic.rememberVibration
import com.sosauce.cutecalc.ui.theme.GlobalFont

@Composable
inline fun CuteButton(
    modifier: Modifier = Modifier,
    text: String,
    color: ButtonColors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.secondaryContainer),
    crossinline onClick: () -> Unit
) {
    val shouldVibrate by rememberVibration()
    val haptic = LocalHapticFeedback.current

    Button(
        onClick = {
            onClick()
            if (shouldVibrate) haptic.performHapticFeedback(HapticFeedbackType.LongPress)
        },
        colors = color,
        modifier = modifier,
    ) {
        Text(
            text = text,
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 35.sp,
            fontFamily = GlobalFont
        )
    }
}

@Composable
fun CuteIconButton(
    modifier: Modifier,
    onClick: () -> Unit
) {
    val shouldVibrate by rememberVibration()
    val haptic = LocalHapticFeedback.current

    Button(
        onClick = {
            onClick()
            if (shouldVibrate) haptic.performHapticFeedback(HapticFeedbackType.LongPress)
        },
        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.secondaryContainer),
        modifier = modifier
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Outlined.Backspace,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.size(35.dp)
        )
    }
}