package com.sosauce.cutecalc.screens.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sosauce.cutecalc.R
import com.sosauce.cutecalc.components.ScaffoldWithBackArrow
import com.sosauce.cutecalc.components.SettingsSwitch
import com.sosauce.cutecalc.logic.rememberDecimal
import com.sosauce.cutecalc.screens.settings.components.SettingsWithTitle

@Composable
fun SettingsMisc(
    onNavigateUp: () -> Unit
) {
    val scrollState = rememberScrollState()
    var useDecimalFormatting by rememberDecimal()

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
                title = R.string.formatting
            ) {
                SettingsSwitch(
                    checked = useDecimalFormatting,
                    onCheckedChange = { useDecimalFormatting = !useDecimalFormatting },
                    topDp = 24.dp,
                    bottomDp = 24.dp,
                    text = R.string.decimal_formatting
                )
            }
        }
    }
}