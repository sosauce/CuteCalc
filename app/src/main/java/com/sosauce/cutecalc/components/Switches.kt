package com.sosauce.cutecalc.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.sosauce.cutecalc.logic.rememberDecimal
import com.sosauce.cutecalc.logic.rememberFollowSys
import com.sosauce.cutecalc.logic.rememberUseAmoledMode
import com.sosauce.cutecalc.logic.rememberUseDarkMode
import com.sosauce.cutecalc.logic.rememberUseHistory
import com.sosauce.cutecalc.logic.rememberVibration
import com.sosauce.cutecalc.ui.theme.GlobalFont


@Composable
fun History() {
    var enableHistory by rememberUseHistory()

    Column {
        Text(
            text = "History",
            fontFamily = GlobalFont,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(horizontal = 34.dp, vertical = 8.dp)
        )

        SettingsCards(
            checked = enableHistory,
            onCheckedChange = { enableHistory = !enableHistory },
            topDp = 24.dp,
            bottomDp = 24.dp,
            text = "Enable History"
        )
    }
}

@Composable
fun Misc() {
    var decimalSetting by rememberDecimal()
    var buttonVibrationSetting by rememberVibration()

    Column {
        Text(
            text = "Misc",
            fontFamily = GlobalFont,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(horizontal = 34.dp, vertical = 8.dp)
        )

        SettingsCards(
            checked = decimalSetting,
            onCheckedChange = { decimalSetting = !decimalSetting },
            topDp = 24.dp,
            bottomDp = 4.dp,
            text = "Decimal Formatting"
        )
        SettingsCards(
            checked = buttonVibrationSetting,
            onCheckedChange = { buttonVibrationSetting = !buttonVibrationSetting },
            topDp = 4.dp,
            bottomDp = 24.dp,
            text = "Haptic Feedback"
        )
    }
}

@Composable
fun ThemeManagement() {
    var darkMode by rememberUseDarkMode()
    var amoledMode by rememberUseAmoledMode()
    var followSys by rememberFollowSys()

    Column {
        Text(
            text = "Theme",
            fontFamily = GlobalFont,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(horizontal = 34.dp, vertical = 8.dp)
        )
        SettingsCards(
            checked = followSys,
            onCheckedChange = { followSys = !followSys },
            topDp = 24.dp,
            bottomDp = 4.dp,
            text = "Follow System"
        )
        AnimatedVisibility(
            visible = !followSys,
            enter = slideInHorizontally() + fadeIn(),
            exit = slideOutHorizontally() + fadeOut()
        ) {
            SettingsCards(
                checked = darkMode,
                onCheckedChange = { darkMode = !darkMode },
                topDp = 4.dp,
                bottomDp = 4.dp,
                text = "Dark Mode"
            )
        }
        SettingsCards(
            checked = amoledMode,
            onCheckedChange = { amoledMode = !amoledMode },
            topDp = 4.dp,
            bottomDp = 24.dp,
            text = "Amoled Mode"
        )
    }
}

@Composable
private fun SettingsCards(
    hasInfoDialog: Boolean = false,
    checked: Boolean,
    onCheckedChange: () -> Unit,
    onClick: (() -> Unit)? = null,
    topDp: Dp,
    bottomDp: Dp,
    text: String
) {
    Card(
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surfaceContainer),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 2.dp),
        shape = RoundedCornerShape(
            topStart = topDp,
            topEnd = topDp,
            bottomStart = bottomDp,
            bottomEnd = bottomDp
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(15.dp)
                .fillMaxWidth()
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = text,
                    fontFamily = GlobalFont
                )
                if (hasInfoDialog) {
                    IconButton(
                        onClick = { onClick?.invoke() }
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Info,
                            contentDescription = "Info Button"
                        )
                    }
                }
            }
            Switch(
                checked = checked,
                onCheckedChange = { onCheckedChange() }
            )
        }
    }
}





