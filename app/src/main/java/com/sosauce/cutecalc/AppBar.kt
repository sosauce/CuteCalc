@file:OptIn(
    ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class,
    ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class,
    ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class,
    ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class
)
@file:Suppress("UNUSED_EXPRESSION", "unused")

package com.sosauce.cutecalc

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Palette
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.sosauce.cutecalc.ui.theme.GlobalFont
import components.ThemeRadioButtons
import components.VibrationSwitch

@SuppressLint("SuspiciousIndentation")
@Composable
fun AppBar(
    title: String,
    navController: NavController,
    showBackArrow: Boolean,
    showMenuIcon: Boolean
) {
    var expanded by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }
    val iconsColor = MaterialTheme.colorScheme.onBackground

    TopAppBar(
        title = { Text(text = title, fontFamily = GlobalFont) },
        navigationIcon = {
            if (showBackArrow) {
                IconButton(onClick = { navController.navigate("CalculatorScreen") }) {
                    Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back arrow")
                }
            } else null
        },
        colors = TopAppBarDefaults.largeTopAppBarColors(Color.Transparent),
        actions = {
            if (showMenuIcon) {
                IconButton(onClick = { expanded = true }) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "More",
                        tint = iconsColor
                    )
                }
            } else null

            if (showDialog) {
                ThemeSelector(
                    onDismissRequest = { showDialog = false }
                )
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .width(180.dp)
                    .background(color = MaterialTheme.colorScheme.surface)
            ) {
                DropdownMenuItem(
                    text = { Text(text = "Settings", fontFamily = GlobalFont) },
                    onClick = { showDialog = true },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Outlined.Settings,
                            contentDescription = "Theme Selector"
                        )
                    })
                DropdownMenuItem(
                    text = { Text(text = "About", fontFamily = GlobalFont) },
                    onClick = { navController.navigate("AboutScreen"); expanded = false },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Outlined.Info, contentDescription = "About"
                        )
                    }
                )

            }
        }
    )
}

@Composable
fun ThemeSelector(
    onDismissRequest: () -> Unit
) {
    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
            shape = RoundedCornerShape(16.dp),
        ) {
            ThemeRadioButtons()
            Divider(thickness = 0.2.dp)
            VibrationSwitch()
        }
    }
}


