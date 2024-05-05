@file:OptIn(ExperimentalMaterial3Api::class)

package com.sosauce.cutecalc

import android.annotation.SuppressLint
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import com.sosauce.cutecalc.ui.theme.GlobalFont

@SuppressLint("SuspiciousIndentation")
@Composable
fun AppBar(
    title: String,
    navController: NavController,
    showBackArrow: Boolean,
    showMenuIcon: Boolean
) {
    TopAppBar(
        title = { Text(text = title, fontFamily = GlobalFont) },
        navigationIcon = {
            if (showBackArrow) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back arrow"
                    )
                }
            }
        },
        colors = TopAppBarDefaults.largeTopAppBarColors(Color.Transparent),
        actions = {
            if (showMenuIcon) {
                IconButton(onClick = { navController.navigate("SettingsScreen") }) {
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



