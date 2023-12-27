package com.sosauce.cutecalc

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import com.sosauce.cutecalc.ui.theme.GlobalFont

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AboutScreen(navController: NavController) {

    val scaffoldState = rememberScaffoldState()

    androidx.compose.material.Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            AppBar(title = "About", showBackArrow = true, navController = navController)
        },
    ) {
        Box(
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.background)
                .fillMaxSize()
        ) {
            Text(text = "CuteCalc v1.1.0", fontFamily = GlobalFont, fontSize = 25.sp, color = Color.White, modifier = Modifier.align(
                Alignment.TopCenter))
            Text(text = "Made with ❤️ by sosauce", fontFamily = GlobalFont, fontSize = 25.sp, color = Color.White, modifier = Modifier.align(
                Alignment.BottomCenter))
        }
    }
}
