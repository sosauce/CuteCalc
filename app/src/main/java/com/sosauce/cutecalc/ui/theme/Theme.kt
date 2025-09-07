@file:OptIn(ExperimentalMaterial3ExpressiveApi::class)

package com.sosauce.cutecalc.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialExpressiveTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import com.sosauce.cutecalc.R
import com.sosauce.cutecalc.data.datastore.rememberAppTheme
import com.sosauce.cutecalc.utils.CuteTheme
import com.sosauce.cutecalc.utils.anyDarkColorScheme
import com.sosauce.cutecalc.utils.anyLightColorScheme

@Composable
fun CuteCalcTheme(
    content: @Composable () -> Unit
) {

    val isSystemInDarkTheme = isSystemInDarkTheme()
    val appTheme by rememberAppTheme()


    val colorScheme = when (appTheme) {
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



    MaterialExpressiveTheme(
        colorScheme = colorScheme,
        content = content
    )
}

val nunitoFontFamily = FontFamily(
    Font(R.font.nunito_black, FontWeight.Black, FontStyle.Normal),
    Font(R.font.nunito_bold, FontWeight.Bold, FontStyle.Normal),
    Font(R.font.nunito_extrabold, FontWeight.ExtraBold, FontStyle.Normal),
    Font(R.font.nunito_extralight, FontWeight.ExtraLight, FontStyle.Normal),
    Font(R.font.nunito_light, FontWeight.Light, FontStyle.Normal),
    Font(R.font.nunito_medium, FontWeight.Medium, FontStyle.Normal),
    Font(R.font.nunito_regular, FontWeight.Normal, FontStyle.Normal),
    Font(R.font.nunito_semibold, FontWeight.SemiBold, FontStyle.Normal)
)