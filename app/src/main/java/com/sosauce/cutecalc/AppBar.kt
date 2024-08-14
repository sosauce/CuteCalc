@file:OptIn(ExperimentalMaterial3Api::class)

package com.sosauce.cutecalc

import android.annotation.SuppressLint
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.sosauce.cutecalc.logic.navigation.Screens
import com.sosauce.cutecalc.ui.theme.GlobalFont

@SuppressLint("SuspiciousIndentation")
@Composable
fun AppBar(
    title: String? = null,
    navController: NavController,
    showBackArrow: Boolean,
) {
    TopAppBar(
        title = {
            title?.let {
                Text(
                    text = it,
                    fontFamily = GlobalFont
                )
            }
        },
        navigationIcon = {
            if (showBackArrow) {
                IconButton(onClick = { navController.navigateUp() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back arrow"
                    )
                }
            }
        },
        colors = TopAppBarDefaults.largeTopAppBarColors(Color.Transparent),
        actions = {
            if (!showBackArrow) {
                IconButton(onClick = { navController.navigate(Screens.History) }) {
                    Icon(
                        imageVector = Icons.Outlined.History,
                        contentDescription = "History",
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }
                IconButton(onClick = { navController.navigate(Screens.Settings) }) {
                    Icon(
                        imageVector = Icons.Outlined.Settings,
                        contentDescription = "More",
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
        }
    )
}



