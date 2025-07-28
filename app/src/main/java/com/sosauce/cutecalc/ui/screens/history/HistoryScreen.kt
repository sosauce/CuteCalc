@file:OptIn(ExperimentalMaterial3ExpressiveApi::class)

package com.sosauce.cutecalc.ui.screens.history

import android.content.ClipData
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalClipboard
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.sosauce.cutecalc.R
import com.sosauce.cutecalc.data.datastore.rememberDecimal
import com.sosauce.cutecalc.data.datastore.rememberSortHistoryASC
import com.sosauce.cutecalc.data.datastore.rememberUseHistory
import com.sosauce.cutecalc.domain.model.Calculation
import com.sosauce.cutecalc.domain.repository.HistoryEvents
import com.sosauce.cutecalc.ui.navigation.Screens
import com.sosauce.cutecalc.ui.shared_components.CuteDropdownMenuItem
import com.sosauce.cutecalc.ui.shared_components.CuteText
import com.sosauce.cutecalc.ui.shared_components.ScaffoldWithBackArrow
import com.sosauce.cutecalc.utils.formatExpression
import com.sosauce.cutecalc.utils.formatNumber
import com.sosauce.cutecalc.utils.isErrorMessage
import com.sosauce.cutecalc.utils.showBottomBar
import com.sosauce.cutecalc.utils.sort

@Composable
fun HistoryScreen(
    calculations: List<Calculation>,
    onEvents: (HistoryEvents) -> Unit,
    onPutBackToField: (String) -> Unit,
    onNavigate: (Screens) -> Unit
) {
    val lazyState = rememberLazyListState()
    var isHistoryEnable by rememberUseHistory()
    val sortASC by rememberSortHistoryASC()

    ScaffoldWithBackArrow(
        backArrowVisible = lazyState.showBottomBar,
        showHistoryActions = true,
        onDeleteHistory = { onEvents(HistoryEvents.DeleteAllCalculation) },
        onNavigateUp = { onNavigate(Screens.MAIN) }
    ) { pv ->
        if (!isHistoryEnable) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CuteText(stringResource(R.string.history_not_enabled))
                Spacer(Modifier.height(10.dp))
                Button(
                    onClick = { isHistoryEnable = !isHistoryEnable }
                ) {
                    CuteText(stringResource(R.string.enable_history))
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = pv,
                state = lazyState
            ) {
                itemsIndexed(
                    items = calculations.sort(sortASC) { it.id },
                    key = { _, item -> item.id }
                ) { index, item ->
                    CalculationItem(
                        calculation = item,
                        onEvents = onEvents,
                        onPutBackToField = onPutBackToField,
                        topDp = if (index == 0) 24.dp else 4.dp,
                        bottomDp = if (index == calculations.lastIndex) 24.dp else 4.dp,
                        modifier = Modifier.animateItem()
                    )
                }
            }
        }
    }

}

@Composable
private fun CalculationItem(
    calculation: Calculation,
    onEvents: (HistoryEvents) -> Unit,
    onPutBackToField: (String) -> Unit,
    topDp: Dp,
    bottomDp: Dp,
    modifier: Modifier = Modifier
) {
    val clipboardManager = LocalClipboard.current
    val shouldFormat by rememberDecimal()
    var actionsExpanded by remember { mutableStateOf(false) }
    val actions = arrayOf(
        HistoryAction(
            onClick = { onPutBackToField(calculation.operation) },
            icon = R.drawable.undo,
            text = R.string.put_field
        ),
        HistoryAction(
            onClick = {
                clipboardManager.nativeClipboard.setPrimaryClip(
                    ClipData.newPlainText(
                        "",
                        "${calculation.operation} = ${calculation.result}"
                    )
                )
            },
            icon = R.drawable.copy,
            text = R.string.copy
        )
    )


    Card(
        onClick = { onPutBackToField(calculation.operation) },
        modifier = modifier
            .padding(horizontal = 16.dp, vertical = 2.dp)
            .clip(
                RoundedCornerShape(
                    topStart = topDp,
                    topEnd = topDp,
                    bottomEnd = bottomDp,
                    bottomStart = bottomDp
                )
            ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier
                    .weight(1f),
                horizontalAlignment = Alignment.Start
            ) {
                CuteText(
                    text = calculation.operation.formatExpression(shouldFormat),
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.basicMarquee()
                )
                CuteText(
                    text = calculation.result.formatNumber(shouldFormat),
                    style = MaterialTheme.typography.titleLarge.copy(
                        color = if (calculation.result.isErrorMessage()) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurfaceVariant
                    ),
                    modifier = Modifier.basicMarquee()
                )
            }
            IconButton(
                onClick = { actionsExpanded = true }
            ) {
                Icon(
                    painter = painterResource(R.drawable.more_vert),
                    contentDescription = stringResource(R.string.more_actions)
                )

                DropdownMenu(
                    expanded = actionsExpanded,
                    onDismissRequest = { actionsExpanded = false },
                    shape = RoundedCornerShape(24.dp)
                ) {
                    actions.forEach { action ->
                        CuteDropdownMenuItem(
                            onClick = {
                                action.onClick()
                                actionsExpanded = false
                            },
                            text = { CuteText(stringResource(action.text)) },
                            leadingIcon = {
                                Icon(
                                    painter = painterResource(action.icon),
                                    contentDescription = null
                                )
                            }
                        )
                    }
                    CuteDropdownMenuItem(
                        onClick = { onEvents(HistoryEvents.DeleteCalculation(calculation)) },
                        text = {
                            CuteText(
                                text = stringResource(R.string.delete),
                                color = MaterialTheme.colorScheme.error
                            )
                        },
                        leadingIcon = {
                            Icon(
                                painter = painterResource(R.drawable.delete),
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.error
                            )
                        }
                    )
                }
            }
        }
    }
}

private data class HistoryAction(
    val onClick: () -> Unit,
    val icon: Int,
    val text: Int
)