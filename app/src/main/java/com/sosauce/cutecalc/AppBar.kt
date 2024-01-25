@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class,
    ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class,
    ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class,
    ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class
)
@file:Suppress("UNUSED_EXPRESSION", "unused")

package com.sosauce.cutecalc

import android.annotation.SuppressLint
import android.content.Context
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.navigation.NavController
import com.sosauce.cutecalc.ui.theme.DarkAmoledColorPalette
import com.sosauce.cutecalc.ui.theme.GlobalFont
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch



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
                                 Icon(imageVector = Icons.Default.ArrowBack, contentDescription = stringResource(
                                     id = R.string.back
                                 ))
                             }
                         } else null
        },
        colors = TopAppBarDefaults.largeTopAppBarColors(Color.Transparent),
        actions = {
            if (showMenuIcon) {
                IconButton(onClick = { expanded = true }) {
                    Icon(imageVector = Icons.Default.MoreVert, contentDescription = stringResource(
                        id = R.string.theme_changer
                    ), tint = iconsColor)
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
                        text = { Text(text = stringResource(id = R.string.theme), fontFamily = GlobalFont) },
                        onClick = { showDialog = true },
                        leadingIcon = { Icon(painterResource(id = R.drawable.palette_outline), contentDescription = stringResource(
                            id = R.string.theme_changer
                        ))})
                    DropdownMenuItem(
                        text = { Text(text = stringResource(id = R.string.about), fontFamily = GlobalFont) },
                        onClick = { navController.navigate("AboutScreen"); expanded = false },
                        leadingIcon = { Icon(imageVector = Icons.Outlined.Info, contentDescription = stringResource(
                            id = R.string.about
                        )) }
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
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
                shape = RoundedCornerShape(16.dp),
            ) {
                ThemeRadioButtons()
            }
        }
    }

enum class Theme {
    Dark, Light, Amoled
}

object PreferencesKeys {
    val THEME = stringPreferencesKey("theme")
}

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")


@Composable
fun ThemeRadioButtons() {
    val options = listOf(stringResource(id = R.string.theme_dark), stringResource(id = R.string.theme_light), stringResource(id = R.string.theme_amoled))
    val context = LocalContext.current
    val dataStore: DataStore<Preferences> = context.dataStore
    val themeFlow: Flow<String?> = dataStore.data
        .map { preferences ->
            preferences[PreferencesKeys.THEME]
        }
    val theme by themeFlow.collectAsState(initial = null)

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
                        selected = theme == option,
                        onClick = {
                            CoroutineScope(Dispatchers.IO).launch {
                                saveTheme(dataStore, option)
                            }
                        }
                    )
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = theme == option,
                    onClick = null
                )
                Text(
                    text = option,
                    modifier = Modifier.padding(start = 16.dp),
                    fontFamily = GlobalFont
                )
            }
        }
    }
}

suspend fun saveTheme(dataStore: DataStore<Preferences>, theme: String) {
    dataStore.edit { settings ->
        settings[PreferencesKeys.THEME] = theme
    }
}

