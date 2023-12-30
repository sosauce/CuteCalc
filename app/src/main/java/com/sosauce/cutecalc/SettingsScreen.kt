package com.sosauce.cutecalc

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.ui.unit.sp
import com.sosauce.cutecalc.ui.theme.GlobalFont

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SettingsScreen(navController: NavController, updateTheme: (UserTheme) -> Unit){

    Scaffold(
        topBar = {
            AppBar(title = "Settings", showBackArrow = true, navController = navController, showMenuIcon = false)
        },
    ) {
        Box(
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.background)
                .fillMaxSize()
        ) {
            Column(modifier = Modifier
                .fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Spacer(modifier = Modifier.height(75.dp))

                Row(horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                ) {
                    ThemeSwitch(updateTheme)
                }
                Spacer(modifier = Modifier.height(300.dp))
                Text(text = "More to come! 🥳", fontSize = 30.sp, color = MaterialTheme.colorScheme.onBackground)
            }
        }
    }
}

@Composable
fun ThemeSwitch(updateTheme: (UserTheme) -> Unit) {

    val darkModeCheck = isSystemInDarkTheme()
    var isChecked by remember { mutableStateOf(darkModeCheck) }

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Dark Mode", fontFamily = GlobalFont, fontSize = 20.sp, color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.weight(1f)
        )
        Switch(
            checked = isChecked,
            onCheckedChange = { isChecked = it; updateTheme(if (it) UserTheme.DARK else UserTheme.LIGHT)}
        )
    }
}