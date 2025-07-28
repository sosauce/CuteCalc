@file:OptIn(ExperimentalMaterial3ExpressiveApi::class)

package com.sosauce.cutecalc.ui.screens.calculator.components

import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.contentColorFor
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.sosauce.cutecalc.R
import com.sosauce.cutecalc.data.datastore.rememberUseButtonsAnimation
import com.sosauce.cutecalc.data.datastore.rememberVibration
import com.sosauce.cutecalc.ui.shared_components.CuteText
import com.sosauce.cutecalc.utils.BACKSPACE
import com.sosauce.cutecalc.utils.thenIf

@Composable
fun RowScope.CuteButton(
    modifier: Modifier = Modifier,
    text: String,
    backgroundColor: Color,
    onClick: () -> Unit,
    onLongClick: (() -> Unit)? = null,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    roundButton: Boolean = true
) {
    val haptic = LocalHapticFeedback.current
    val shouldVibrate by rememberVibration()
    val useButtonsAnimation by rememberUseButtonsAnimation()
    val isPressed by interactionSource.collectIsPressedAsState()
    val cornerRadius by animateIntAsState(
        targetValue = if (isPressed && useButtonsAnimation) 24 else 50
    )

    Box(
        modifier = modifier
            .semantics { role = Role.Button }
            .defaultMinSize(
                minWidth = ButtonDefaults.MinWidth,
                minHeight = ButtonDefaults.MinHeight
            )
            .clip(RoundedCornerShape(cornerRadius))
            .background(backgroundColor)
            .combinedClickable(
                interactionSource = interactionSource,
                indication = ripple(),
                onClick = {
                    onClick()
                    if (shouldVibrate) haptic.performHapticFeedback(HapticFeedbackType.Confirm)
                },
                onLongClick = {
                    onLongClick?.invoke()
                    if (shouldVibrate) haptic.performHapticFeedback(HapticFeedbackType.Confirm)
                }
            )
            .weight(1f)
            .thenIf(roundButton) {
                aspectRatio(1f)
            },
        contentAlignment = Alignment.Center
    ) {
        if (text == BACKSPACE) {
            Icon(
                painter = painterResource(R.drawable.backspace_rounded),
                contentDescription = stringResource(R.string.back),
                tint = MaterialTheme.colorScheme.contentColorFor(backgroundColor),
                modifier = Modifier.size(48.dp)
            )
        } else {
            CuteText(
                text = text,
                color = MaterialTheme.colorScheme.contentColorFor(backgroundColor),
                style = MaterialTheme.typography.displaySmall
            )
        }
    }


//    Button(
//        onClick = {
//            onClick()
//            if (shouldVibrate) haptic.performHapticFeedback(HapticFeedbackType.Confirm)
//        },
//        colors = color,
//        modifier = modifier,
//        shape = RoundedCornerShape(cornerRadius),
//        interactionSource = interactionSource,
//        enabled = enabled
//    ) {
//        CuteText(
//            text = text,
//            color = textColor,
//            fontSize = 35.sp
//        )
//    }
}








