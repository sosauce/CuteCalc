package com.sosauce.cutecalc.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.sosauce.cutecalc.AppBar
import com.sosauce.cutecalc.R
import com.sosauce.cutecalc.components.AboutCard
import com.sosauce.cutecalc.components.History
import com.sosauce.cutecalc.components.Misc
import com.sosauce.cutecalc.components.ThemeManagement
import com.sosauce.cutecalc.logic.navigation.Screens
import com.sosauce.cutecalc.ui.theme.GlobalFont

@Composable
fun SettingsScreen(
    onNavigate: (Screens) -> Unit
) {

    Scaffold(
        topBar = {
            AppBar(
                showBackArrow = true,
                onNavigate = onNavigate,
                title = {
                    Text(
                        text = stringResource(R.string.settings),
                        fontFamily = GlobalFont
                    )
                }
            )
        },
    ) { values ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(values),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AboutCard()
            ThemeManagement()
            History()
            Misc()
        }
    }

}