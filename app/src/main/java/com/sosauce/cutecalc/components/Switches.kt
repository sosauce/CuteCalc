package com.sosauce.cutecalc.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sosauce.cutecalc.R
import com.sosauce.cutecalc.logic.rememberAppTheme
import com.sosauce.cutecalc.logic.rememberDecimal
import com.sosauce.cutecalc.logic.rememberShowClearButton
import com.sosauce.cutecalc.logic.rememberUseButtonsAnimation
import com.sosauce.cutecalc.logic.rememberUseHistory
import com.sosauce.cutecalc.logic.rememberUseSystemFont
import com.sosauce.cutecalc.logic.rememberVibration
import com.sosauce.cutecalc.utils.CuteTheme
import com.sosauce.cutecalc.utils.anyDarkColorScheme
import com.sosauce.cutecalc.utils.anyLightColorScheme


@Composable
fun UI() {
    val settings = mapOf(
        R.string.buttons_anim to rememberUseButtonsAnimation(),
        R.string.show_clear_button to rememberShowClearButton(),
        R.string.use_sys_font to rememberUseSystemFont()
    )

    Column {
        CuteText(
            text = "UI",
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(horizontal = 34.dp, vertical = 8.dp)
        )

        settings.onEachIndexed { index, (text, setting) ->
            SettingsCards(
                checked = setting.value,
                onCheckedChange = { setting.value = !setting.value },
                topDp = if (index == 0) 24.dp else 4.dp,
                bottomDp = if (index == settings.size - 1) 24.dp else 4.dp,
                text = stringResource(text),
                optionalDescription = {
                    if (text == R.string.show_clear_button) {
                        CuteText(
                            text = stringResource(R.string.clear_button_desc),
                            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.85f),
                            fontSize = 12.sp
                        )
                    }
                }
            )
        }
    }
}

@Composable
fun Misc() {
    val settings = mapOf(
        R.string.use_history to rememberUseHistory(),
        R.string.decimal_formatting to rememberDecimal(),
        R.string.haptic_feedback to rememberVibration(),
    )
    Column {
        CuteText(
            text = stringResource(R.string.misc),
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(horizontal = 34.dp, vertical = 8.dp)
        )
        settings.onEachIndexed { index, (text, setting) ->
            SettingsCards(
                checked = setting.value,
                onCheckedChange = { setting.value = !setting.value },
                topDp = if (index == 0) 24.dp else 4.dp,
                bottomDp = if (index == settings.size - 1) 24.dp else 4.dp,
                text = stringResource(text)
            )
        }
    }
}

@Composable
fun ThemeManagement() {
    var theme by rememberAppTheme()

    Column {
        CuteText(
            text = stringResource(id = R.string.theme),
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(horizontal = 34.dp, vertical = 8.dp)
        )
        Card(
            colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surfaceContainer),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 2.dp)
        ) {
            LazyRow {
                item {
                    ThemeSelector(
                        onClick = { theme = CuteTheme.SYSTEM },
                        backgroundColor = if (isSystemInDarkTheme()) anyDarkColorScheme().background else anyLightColorScheme().background,
                        text = stringResource(R.string.follow_sys),
                        isThemeSelected = theme == CuteTheme.SYSTEM,
                        icon = {
                            Icon(
                                painter = painterResource(R.drawable.system_theme),
                                contentDescription = stringResource(R.string.follow_sys),
                                tint = if (isSystemInDarkTheme()) anyDarkColorScheme().onBackground else anyLightColorScheme().onBackground
                            )
                        }
                    )
                }
                item {
                    ThemeSelector(
                        onClick = { theme = CuteTheme.DARK },
                        backgroundColor = anyDarkColorScheme().background,
                        text = stringResource(R.string.dark_mode),
                        isThemeSelected = theme == CuteTheme.DARK,
                        icon = {
                            Icon(
                                painter = painterResource(R.drawable.dark_mode),
                                contentDescription = stringResource(R.string.dark_mode),
                                tint = anyDarkColorScheme().onBackground
                            )
                        }
                    )
                }
                item {
                    ThemeSelector(
                        onClick = { theme = CuteTheme.LIGHT },
                        backgroundColor = anyLightColorScheme().background,
                        text = stringResource(R.string.light_mode),
                        isThemeSelected = theme == CuteTheme.LIGHT,
                        icon = {
                            Icon(
                                painter = painterResource(R.drawable.light_mode),
                                contentDescription = stringResource(R.string.light_mode),
                                tint = anyLightColorScheme().onBackground
                            )
                        }
                    )
                }
                item {
                    ThemeSelector(
                        onClick = { theme = CuteTheme.AMOLED },
                        backgroundColor = Color.Black,
                        text = stringResource(R.string.amoled_mode),
                        isThemeSelected = theme == CuteTheme.AMOLED,
                        icon = {
                            Icon(
                                painter = painterResource(R.drawable.amoled),
                                contentDescription = stringResource(R.string.amoled_mode),
                                tint = Color.White
                            )
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun SettingsCards(
    checked: Boolean,
    topDp: Dp,
    bottomDp: Dp,
    text: String,
    onCheckedChange: () -> Unit,
    optionalDescription: (@Composable () -> Unit)? = null
) {
    Card(
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surfaceContainer),
        modifier = Modifier
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
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .weight(1f)
            ) {
                Column {
                    CuteText(
                        text = text
                    )
                    optionalDescription?.invoke()
                }
            }
            Switch(
                checked = checked,
                onCheckedChange = { onCheckedChange() },
                colors = SwitchDefaults.colors(
                    uncheckedBorderColor = Color.Transparent
                )
            )
        }
    }
}

@Composable
fun ThemeSelector(
    onClick: () -> Unit,
    backgroundColor: Color,
    icon: @Composable () -> Unit,
    text: String,
    isThemeSelected: Boolean
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(10.dp)
            .height(100.dp)
            .clip(RoundedCornerShape(12.dp))
            .clickable { onClick() }
    ) {
        Box(
            modifier = Modifier
                .padding(10.dp)
                .size(50.dp)
                .clip(CircleShape)
                .border(
                    width = 2.dp,
                    color = if (isThemeSelected) MaterialTheme.colorScheme.secondary else Color.Transparent,
                    shape = CircleShape
                )
                .background(backgroundColor),
            contentAlignment = Alignment.Center
        ) {
            icon()
        }
        Spacer(Modifier.weight(1f))
        CuteText(text)
    }
}







