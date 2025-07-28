package com.sosauce.cutecalc.ui.screens.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.RadioButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEach
import com.sosauce.cutecalc.R
import com.sosauce.cutecalc.data.datastore.rememberDecimal
import com.sosauce.cutecalc.data.datastore.rememberDecimalPrecision
import com.sosauce.cutecalc.ui.screens.settings.components.SettingsDropdownMenu
import com.sosauce.cutecalc.ui.screens.settings.components.SettingsSwitch
import com.sosauce.cutecalc.ui.screens.settings.components.SettingsWithTitle
import com.sosauce.cutecalc.ui.shared_components.CuteDropdownMenuItem
import com.sosauce.cutecalc.ui.shared_components.CuteText
import com.sosauce.cutecalc.ui.shared_components.ScaffoldWithBackArrow
import com.sosauce.cutecalc.utils.formatNumber

@Composable
fun SettingsFormatting(onNavigateUp: () -> Unit) {
    val scrollState = rememberScrollState()
    var shouldFormat by rememberDecimal()
    var decimalPrecision by rememberDecimalPrecision()
    val decimalPrecisionOptions = MutableList(16) { it }.apply { add(1000) }

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
                    checked = shouldFormat,
                    onCheckedChange = { shouldFormat = !shouldFormat },
                    topDp = 24.dp,
                    bottomDp = 4.dp,
                    text = R.string.decimal_formatting
                )
                SettingsDropdownMenu(
                    value = decimalPrecision.toLong(),
                    topDp = 4.dp,
                    bottomDp = 24.dp,
                    text = R.string.decimal_precision,
                    optionalDescription = R.string.decimal_precision_desc
                ) {
                    decimalPrecisionOptions.fastForEach { number ->
                        CuteDropdownMenuItem(
                            onClick = { decimalPrecision = number },
                            text = { CuteText(number.toString().formatNumber(shouldFormat)) },
                            leadingIcon = {
                                RadioButton(
                                    selected = number == decimalPrecision,
                                    onClick = null
                                )
                            }
                        )
                    }
                }
            }
        }
    }
}