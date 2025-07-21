package com.sosauce.cutecalc.screens.settings

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.sosauce.cutecalc.R
import com.sosauce.cutecalc.components.ScaffoldWithBackArrow
import com.sosauce.cutecalc.components.SettingsSwitch
import com.sosauce.cutecalc.logic.rememberAppTheme
import com.sosauce.cutecalc.logic.rememberShowBackButton
import com.sosauce.cutecalc.logic.rememberShowClearButton
import com.sosauce.cutecalc.logic.rememberUseButtonsAnimation
import com.sosauce.cutecalc.logic.rememberUseSystemFont
import com.sosauce.cutecalc.logic.rememberVibration
import com.sosauce.cutecalc.screens.settings.components.FontSelector
import com.sosauce.cutecalc.screens.settings.components.LazyRowWithScrollButton
import com.sosauce.cutecalc.screens.settings.components.SettingsWithTitle
import com.sosauce.cutecalc.screens.settings.components.ThemeItem
import com.sosauce.cutecalc.screens.settings.components.ThemeSelector
import com.sosauce.cutecalc.ui.theme.GlobalFont
import com.sosauce.cutecalc.utils.CuteTheme
import com.sosauce.cutecalc.utils.anyDarkColorScheme
import com.sosauce.cutecalc.utils.anyLightColorScheme

@Composable
fun SettingsLookAndFeel(
    onNavigateUp: () -> Unit,
) {
    val scrollState = rememberScrollState()
    var theme by rememberAppTheme()
    var useSystemFont by rememberUseSystemFont()
    var useButtonsAnimation by rememberUseButtonsAnimation()
    var useHapticFeedback by rememberVibration()
    var showClearButton by rememberShowClearButton()
    var showBackButton by rememberShowBackButton()
    val themeItems = listOf(
        ThemeItem(
            onClick = { theme = CuteTheme.SYSTEM },
            backgroundColor = if (isSystemInDarkTheme()) anyDarkColorScheme().background else anyLightColorScheme().background,
            text = stringResource(R.string.follow_sys),
            isSelected = theme == CuteTheme.SYSTEM,
            iconAndTint = Pair(
                painterResource(R.drawable.system_theme),
                if (isSystemInDarkTheme()) anyDarkColorScheme().onBackground else anyLightColorScheme().onBackground
            )
        ),
        ThemeItem(
            onClick = { theme = CuteTheme.DARK },
            backgroundColor = anyDarkColorScheme().background,
            text = stringResource(R.string.dark_mode),
            isSelected = theme == CuteTheme.DARK,
            iconAndTint = Pair(
                painterResource(R.drawable.dark_mode),
                anyDarkColorScheme().onBackground
            )
        ),
        ThemeItem(
            onClick = { theme = CuteTheme.LIGHT },
            backgroundColor = anyLightColorScheme().background,
            text = stringResource(R.string.light_mode),
            isSelected = theme == CuteTheme.LIGHT,
            iconAndTint = Pair(
                painterResource(R.drawable.light_mode),
                anyLightColorScheme().onBackground
            )
        ),
        ThemeItem(
            onClick = { theme = CuteTheme.AMOLED },
            backgroundColor = Color.Black,
            text = stringResource(R.string.amoled_mode),
            isSelected = theme == CuteTheme.AMOLED,
            iconAndTint = Pair(painterResource(R.drawable.amoled), Color.White)
        )
    )
    val fontItems = listOf(
        FontItem(
            onClick = { useSystemFont = false },
            fontStyle = FontStyle.DEFAULT,
            borderColor = if (!useSystemFont) MaterialTheme.colorScheme.primary else Color.Transparent,
            text = {
                Text(
                    text = "Tt",
                    fontFamily = GlobalFont
                )
            },
        ),
        FontItem(
            onClick = { useSystemFont = true },
            fontStyle = FontStyle.SYSTEM,
            borderColor = if (useSystemFont) MaterialTheme.colorScheme.primary else Color.Transparent,
            text = { Text("Tt") }
        )
    )

    ScaffoldWithBackArrow(
        backArrowVisible = !scrollState.canScrollBackward,
        onNavigateUp = onNavigateUp
    ) { pv ->
        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .padding(pv)
        ) {
            SettingsWithTitle(
                title = R.string.theme
            ) {
                Card(
                    colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surfaceContainer),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 2.dp)
                ) {
                    LazyRowWithScrollButton(
                        items = themeItems
                    ) { item ->
                        ThemeSelector(
                            onClick = item.onClick,
                            backgroundColor = item.backgroundColor,
                            text = item.text,
                            isThemeSelected = item.isSelected,
                            icon = {
                                Icon(
                                    painter = item.iconAndTint.first,
                                    contentDescription = null,
                                    tint = item.iconAndTint.second,
                                )
                            }
                        )
                    }
                }
            }
            SettingsWithTitle(
                title = R.string.font
            ) {
                Card(
                    colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surfaceContainer),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 2.dp)
                ) {
                    LazyRowWithScrollButton(
                        items = fontItems
                    ) { item ->
                        FontSelector(item)
                    }
                }
            }
            SettingsWithTitle(
                title = R.string.ui
            ) {
                SettingsSwitch(
                    checked = useButtonsAnimation,
                    onCheckedChange = { useButtonsAnimation = !useButtonsAnimation },
                    topDp = 24.dp,
                    bottomDp = 4.dp,
                    text = R.string.buttons_anim
                )
                SettingsSwitch(
                    checked = useHapticFeedback,
                    onCheckedChange = { useHapticFeedback = !useHapticFeedback },
                    topDp = 4.dp,
                    bottomDp = 4.dp,
                    text = R.string.haptic_feedback
                )
                SettingsSwitch(
                    checked = showClearButton,
                    onCheckedChange = { showClearButton = !showClearButton },
                    topDp = 4.dp,
                    bottomDp = 4.dp,
                    text = R.string.show_clear_button,
                    optionalDescription = R.string.clear_button_desc
                )
                SettingsSwitch(
                    checked = showBackButton,
                    onCheckedChange = { showBackButton = !showBackButton },
                    topDp = 4.dp,
                    bottomDp = 24.dp,
                    text = R.string.show_back_button
                )
            }
        }
    }
}

@Immutable
data class FontItem(
    val onClick: () -> Unit,
    val fontStyle: FontStyle,
    val borderColor: Color,
    val text: @Composable () -> Unit
)

enum class FontStyle {
    DEFAULT,
    SYSTEM
}