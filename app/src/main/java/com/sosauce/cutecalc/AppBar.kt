package com.sosauce.cutecalc

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun AppBar(title: String, navController: NavController, showBackArrow: Boolean) {
    var expanded by remember { mutableStateOf(false) }
    val dynamicBackground = if (isSystemInDarkTheme()) {androidx.compose.material3.MaterialTheme.colorScheme.background} else { androidx.compose.material3.MaterialTheme.colorScheme.primaryContainer}



    TopAppBar(
        title = { Text(text = title) },
        backgroundColor = dynamicBackground,
        contentColor = MaterialTheme.colors.onPrimary,
        elevation = 0.dp,
        navigationIcon = {
                         if (showBackArrow) {
                             IconButton(onClick = { navController.navigate("CalculatorScreen") }) {
                                 Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back Arrow")
                             }
                         } else {null}
        },
        actions = {
            IconButton(onClick = { expanded = true }) {
                Icon(imageVector = Icons.Default.MoreVert, contentDescription = "Theme changer")
            }
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier
                        .width(180.dp)
                        .background(color = androidx.compose.material3.MaterialTheme.colorScheme.onSecondary)
                    ) {
                    DropdownMenuItem(text = { Text(text = "Settings") }, onClick = { navController.navigate("SettingsScreen") }, leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "settings"
                        )
                    })
                    DropdownMenuItem(text = { Text(text = "About") }, onClick = { navController.navigate("AboutScreen") }, leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = "about"
                        )
                    })
                }
        }
    )
}