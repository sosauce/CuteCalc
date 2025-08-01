package com.sosauce.cutecalc.ui.screens.history.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.sosauce.cutecalc.R
import com.sosauce.cutecalc.data.datastore.rememberSortHistoryASC
import com.sosauce.cutecalc.ui.shared_components.CuteDropdownMenuItem
import com.sosauce.cutecalc.ui.shared_components.CuteText

@Composable
fun HistoryActionButtons(
    modifier: Modifier = Modifier,
    onDeleteHistory: () -> Unit
) {
    var dropDownExpanded by remember { mutableStateOf(false) }
    var sortASC by rememberSortHistoryASC()

    SmallFloatingActionButton(
        onClick = {},
        modifier = modifier,
        shape = RoundedCornerShape(14.dp),
        containerColor = MaterialTheme.colorScheme.surfaceContainer
    ) {
        Row {
            IconButton(
                onClick = { dropDownExpanded = true }
            ) {
                AnimatedContent(
                    targetState = !dropDownExpanded
                ) {
                    Icon(
                        painter = if (it) painterResource(R.drawable.sort_rounded) else painterResource(
                            R.drawable.close
                        ),
                        contentDescription = stringResource(R.string.sort)
                    )
                }
            }
            IconButton(
                onClick = onDeleteHistory
            ) {
                Icon(
                    painter = painterResource(R.drawable.trash_rounded),
                    contentDescription = stringResource(R.string.delete),
                    tint = MaterialTheme.colorScheme.error
                )
            }

            DropdownMenu(
                expanded = dropDownExpanded,
                onDismissRequest = { dropDownExpanded = false },
                shape = RoundedCornerShape(24.dp)
            ) {
                CuteDropdownMenuItem(
                    onClick = { sortASC = true },
                    text = { CuteText(stringResource(R.string.ascending)) },
                    leadingIcon = {
                        RadioButton(
                            selected = sortASC,
                            onClick = null
                        )
                    }
                )
                CuteDropdownMenuItem(
                    onClick = { sortASC = false },
                    text = { CuteText(stringResource(R.string.descending)) },
                    leadingIcon = {
                        RadioButton(
                            selected = !sortASC,
                            onClick = null
                        )
                    }
                )
            }
        }
    }
}