@file:Suppress("PrivatePropertyName")

package com.sosauce.cutecalc.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import com.sosauce.cutecalc.R
import com.sosauce.cutecalc.logic.rememberAppTheme
import com.sosauce.cutecalc.utils.CuteTheme
import com.sosauce.cutecalc.utils.anyDarkColorScheme
import com.sosauce.cutecalc.utils.anyLightColorScheme

@Composable
fun CuteCalcTheme(
    content: @Composable () -> Unit
) {

    val isSystemInDarkTheme = isSystemInDarkTheme()
    val appTheme by rememberAppTheme()


    val colorScheme = when(appTheme) {
        CuteTheme.AMOLED -> anyDarkColorScheme().copy(
            surface = Color.Black,
            inverseSurface = Color.White,
            background = Color.Black,
        )
        CuteTheme.SYSTEM -> if (isSystemInDarkTheme) anyDarkColorScheme() else anyLightColorScheme()
        CuteTheme.DARK -> anyDarkColorScheme()
        CuteTheme.LIGHT -> anyLightColorScheme()
        else -> anyDarkColorScheme()
    }



    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}

val GlobalFont = FontFamily(Font(R.font.nunito))