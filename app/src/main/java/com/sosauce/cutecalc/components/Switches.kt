package com.sosauce.cutecalc.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sosauce.cutecalc.R
import com.sosauce.cutecalc.logic.rememberDecimal
import com.sosauce.cutecalc.logic.rememberFollowSys
import com.sosauce.cutecalc.logic.rememberShowClearButton
import com.sosauce.cutecalc.logic.rememberUseAmoledMode
import com.sosauce.cutecalc.logic.rememberUseButtonsAnimation
import com.sosauce.cutecalc.logic.rememberUseDarkMode
import com.sosauce.cutecalc.logic.rememberUseHistory
import com.sosauce.cutecalc.logic.rememberUseSystemFont
import com.sosauce.cutecalc.logic.rememberVibration
import kotlin.collections.component1
import kotlin.collections.component2


//@Composable
//fun History() {
//    var enableHistory by rememberUseHistory()
//
//    Column {
//        CuteText(
//            text = stringResource(R.string.history),
//            color = MaterialTheme.colorScheme.primary,
//            modifier = Modifier.padding(horizontal = 34.dp, vertical = 8.dp)
//        )
//
//        SettingsCards(
//            checked = enableHistory,
//            onCheckedChange = { enableHistory = !enableHistory },
//            topDp = 24.dp,
//            bottomDp = 24.dp,
//            text = stringResource(R.string.use_history)
//        )
//    }
//}

@Composable
fun UI() {
    val settings = mapOf<Int, MutableState<Boolean>>(
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
    val settings = mapOf<Int, MutableState<Boolean>>(
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

    val settings = mapOf<Int, MutableState<Boolean>>(
        R.string.follow_sys to rememberFollowSys(),
        R.string.dark_mode to rememberUseDarkMode(),
        R.string.amoled_mode to rememberUseAmoledMode(),
    )


    Column {
        CuteText(
            text = stringResource(R.string.theme),
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(horizontal = 34.dp, vertical = 8.dp)
        )

        settings.onEachIndexed { index, (text, setting) ->
            AnimatedVisibility(
                visible = text != R.string.dark_mode || !rememberFollowSys().value
            ) {
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
}

@Composable
fun SettingsCards(
    checked: Boolean,
    topDp: Dp,
    bottomDp: Dp,
    text: String,
    onCheckedChange: () -> Unit,
    optionalDescription: @Composable () -> Unit = {}
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
                    optionalDescription()
                }
            }
            Switch(
                checked = checked,
                onCheckedChange = { onCheckedChange() }
            )
        }
    }
}





