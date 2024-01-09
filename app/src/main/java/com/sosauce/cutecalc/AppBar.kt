@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class,
    ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class,
    ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class
)
@file:Suppress("UNUSED_EXPRESSION")

package com.sosauce.cutecalc

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.sosauce.cutecalc.ui.theme.GlobalFont

@SuppressLint("SuspiciousIndentation")
@Composable
fun AppBar(title: String, navController: NavController, showBackArrow: Boolean, showMenuIcon: Boolean) {
    var expanded by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }
    val iconsColor = MaterialTheme.colorScheme.onBackground

    TopAppBar(
        title = { Text(text = title, fontFamily = GlobalFont) },
        navigationIcon = {
                         if (showBackArrow) {
                             IconButton(onClick = { navController.navigate("CalculatorScreen") }) {
                                 Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back Arrow")
                             }
                         } else null
        },
        actions = {
            if (showMenuIcon) {
                IconButton(onClick = { expanded = true }) {
                    Icon(imageVector = Icons.Default.MoreVert, contentDescription = "Theme changer", tint = iconsColor)
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
                        text = { Text(text = "Theme", fontFamily = GlobalFont) },
                        onClick = { showDialog = true },
                        leadingIcon = { Icon(painterResource(id = R.drawable.palette_outline), contentDescription = "theme selector")})

                    /*DropdownMenuItem(
                        text = { Text(text = "Settings", fontFamily = GlobalFont) },
                        onClick = { navController.navigate("SettingsScreen"); expanded = false },
                        leadingIcon = { Icon(imageVector = Icons.Outlined.Settings, contentDescription = "settings")})
                    */
                    DropdownMenuItem(
                        text = { Text(text = "About", fontFamily = GlobalFont) },
                        onClick = { navController.navigate("AboutScreen"); expanded = false },
                        leadingIcon = { Icon(imageVector = Icons.Outlined.Info, contentDescription = "about") }
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
                    .height(250.dp)
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp),
            ) {
                ThemeRadioButtons()
            }
        }
    }

@Composable
fun ThemeRadioButtons() {
    val options = listOf("System Default", "Dark", "Light")
    var selectedOption by remember { mutableStateOf(options[0]) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        options.forEach { option ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .selectable(
                        selected = selectedOption == option,
                        onClick = { selectedOption = option }
                    )
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = selectedOption == option,
                    onClick = null
                )
                Text(
                    text = option,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
        }
    }
}