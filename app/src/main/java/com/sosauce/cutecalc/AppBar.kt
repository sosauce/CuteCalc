package com.sosauce.cutecalc

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.sosauce.cutecalc.ui.theme.GlobalFont

@Composable
fun AppBar() {

    TopAppBar(
        title = { Text(text = "CuteCalc (V1.0.0)", fontFamily = GlobalFont) },
        backgroundColor = Color.Black,
        contentColor = MaterialTheme.colors.onPrimary,
        actions = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Default.MoreVert, contentDescription = "Theme changer")
            }
        }

    )
}