package com.sosauce.cutecalc.components

import android.content.Context
import android.content.Context.VIBRATOR_SERVICE
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sosauce.cutecalc.ui.theme.GlobalFont

@Composable
fun CuteButton(
    text: String,
    color: ButtonColors,
    shouldVibrate: Boolean,
    modifier: Modifier,
    onClick: () -> Unit
) {

    val context = LocalContext.current

    Button(
        onClick = {
            onClick()
            if (shouldVibrate) {
                vibration(context)
            }
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
    shouldVibrate: Boolean,
    modifier: Modifier,
    onClick: () -> Unit
) {

    val context = LocalContext.current

    Button(
        onClick = {
            onClick()
            if (shouldVibrate) vibration(context)
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

private fun vibration(
    context: Context
) {
    val vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        (context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager).defaultVibrator
    } else {
        @Suppress("DEPRECATION") context.getSystemService(VIBRATOR_SERVICE) as Vibrator
    }
    val vibrationEffect = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        VibrationEffect.createOneShot(100, 90)
    } else {
        TODO("VERSION.SDK_INT < O")
    }
    vibrator.vibrate(vibrationEffect)
}