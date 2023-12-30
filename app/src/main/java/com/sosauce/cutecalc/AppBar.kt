@file:OptIn(ExperimentalMaterial3Api::class)

package com.sosauce.cutecalc

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.sosauce.cutecalc.ui.theme.GlobalFont

@SuppressLint("SuspiciousIndentation")
@Composable
fun AppBar(title: String, navController: NavController, showBackArrow: Boolean, showMenuIcon: Boolean) {
    var expanded by remember { mutableStateOf(false) }
    val iconsColor = MaterialTheme.colorScheme.onBackground

    TopAppBar(
        title = { Text(text = title, fontFamily = GlobalFont) },
        modifier = Modifier.background(MaterialTheme.colorScheme.background),
        navigationIcon = {
                         if (showBackArrow) {
                             IconButton(onClick = { navController.navigate("CalculatorScreen") }) {
                                 Icon(imageVector = Icons.Default.ArrowBackIosNew, contentDescription = "Back Arrow")
                             }
                         } else null
        },
        actions = {
            if (showMenuIcon) {
                IconButton(onClick = { expanded = true }) {
                    Icon(imageVector = Icons.Default.MoreVert, contentDescription = "Theme changer", tint = iconsColor)
                }
            } else null
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier
                        .width(180.dp)
                        .background(color = MaterialTheme.colorScheme.surface)
                ) {
                    DropdownMenuItem(text = { Text(text = "Settings", fontFamily = GlobalFont) },onClick = { navController.navigate("SettingsScreen"); expanded = false }, leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "settings"
                        )
                    }, modifier = Modifier.clip(RoundedCornerShape(25.dp)))
                    DropdownMenuItem(text = { Text(text = "About", fontFamily = GlobalFont) }, onClick = { navController.navigate("AboutScreen"); expanded = false }, leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = "about"
                        )
                    }, modifier = Modifier.clip(RoundedCornerShape(25.dp)))
                }
        }
    )
}