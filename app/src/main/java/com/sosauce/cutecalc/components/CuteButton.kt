@file:OptIn(ExperimentalMaterial3ExpressiveApi::class)

package com.sosauce.cutecalc.components

import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sosauce.cutecalc.R
import com.sosauce.cutecalc.logic.rememberUseButtonsAnimation
import com.sosauce.cutecalc.logic.rememberVibration

@Composable
fun CuteButton(
    modifier: Modifier = Modifier,
    text: String,
    color: ButtonColors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.surfaceContainer),
    onClick: () -> Unit,
    textColor: Color = MaterialTheme.colorScheme.onBackground,
    enabled: Boolean = true
) {
    val haptic = LocalHapticFeedback.current
    val shouldVibrate by rememberVibration()
    val useButtonsAnimation by rememberUseButtonsAnimation()
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val cornerRadius by animateIntAsState(
        targetValue = if (isPressed && useButtonsAnimation) 24 else 50
    )


    Button(
        onClick = {
            onClick()
            if (shouldVibrate) haptic.performHapticFeedback(HapticFeedbackType.Confirm)
        },
        colors = color,
        modifier = modifier,
        shape = RoundedCornerShape(cornerRadius),
        interactionSource = interactionSource,
        enabled = enabled
    ) {
        CuteText(
            text = text,
            color = textColor,
            fontSize = 35.sp
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CuteIconButton(
    modifier: Modifier,
    onClick: () -> Unit,
    onLongClick: () -> Unit = {},
    color: ButtonColors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.surfaceContainer)
) {
    val shouldVibrate by rememberVibration()
    val useButtonsAnimation by rememberUseButtonsAnimation()
    val haptic = LocalHapticFeedback.current

    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val cornerRadius by animateIntAsState(
        targetValue = if (isPressed && useButtonsAnimation) 24 else 50
    )


    LongClickButton(
        onClick = {
            onClick()
            if (shouldVibrate) haptic.performHapticFeedback(HapticFeedbackType.Confirm)
        },
        onLongClick = {
            onLongClick()
            if (shouldVibrate) haptic.performHapticFeedback(HapticFeedbackType.Confirm)
        },
        colors = color,
        modifier = modifier,
        shape = RoundedCornerShape(cornerRadius),
        interactionSource = interactionSource
    ) {
        Icon(
            painter = painterResource(R.drawable.backspace_rounded),
            contentDescription = stringResource(R.string.back),
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.size(45.dp)
        )
    }
}







