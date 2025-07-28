@file:OptIn(ExperimentalMaterial3ExpressiveApi::class)

package com.sosauce.cutecalc.ui.screens.settings.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sosauce.cutecalc.R
import com.sosauce.cutecalc.data.datastore.rememberDecimal
import com.sosauce.cutecalc.ui.shared_components.CuteText


@Composable
fun SettingsSwitch(
    checked: Boolean,
    onCheckedChange: () -> Unit,
    topDp: Dp,
    bottomDp: Dp,
    text: Int,
    optionalDescription: Int? = null
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
                    CuteText(stringResource(text))
                    optionalDescription?.let {
                        CuteText(
                            text = stringResource(it),
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            fontSize = 12.sp
                        )
                    }
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
fun SettingsDropdownMenu(
    value: Long,
    topDp: Dp,
    bottomDp: Dp,
    text: Int,
    optionalDescription: Int? = null,
    dropdownContent: @Composable (ColumnScope.() -> Unit)
) {

    var expanded by remember { mutableStateOf(false) }
    val useDecimalFormatting by rememberDecimal()


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
                    CuteText(stringResource(text))
                    optionalDescription?.let {
                        CuteText(
                            text = stringResource(it),
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            fontSize = 12.sp
                        )
                    }
                }
            }
            TextButton(
                onClick = { expanded = true }
            ) {
                AnimatedContent(
                    targetState = value
                ) {
                    CuteText(
                        text = if (it == Long.MAX_VALUE) stringResource(R.string.no_limit) else it.toString(),
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontSize = 15.sp
                    )
                }

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    shape = RoundedCornerShape(24.dp)
                ) { dropdownContent() }
            }
        }
    }
}








