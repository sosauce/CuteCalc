package com.sosauce.cutecalc.screens

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sosauce.cutecalc.AppBar
import com.sosauce.cutecalc.R
import com.sosauce.cutecalc.history.Calculation
import com.sosauce.cutecalc.history.HistoryEvents
import com.sosauce.cutecalc.history.HistoryState
import com.sosauce.cutecalc.logic.formatOrNot
import com.sosauce.cutecalc.logic.navigation.Screens
import com.sosauce.cutecalc.logic.rememberDecimal
import com.sosauce.cutecalc.logic.rememberSortHistoryASC
import com.sosauce.cutecalc.logic.rememberUseHistory
import com.sosauce.cutecalc.ui.theme.GlobalFont

@Composable
fun HistoryScreen(
    state: HistoryState,
    onEvents: (HistoryEvents) -> Unit,
    onNavigate: (Screens) -> Unit
) {
    var isHistoryEnable by rememberUseHistory()
    val sortASC by rememberSortHistoryASC()
    val sortedCalculations by remember(state.calculation) {
        derivedStateOf {
            if (sortASC) {
                state.calculation.sortedBy { it.id }
            } else {
                state.calculation.sortedByDescending { it.id }
            }
        }
    }

    Scaffold(
        topBar = {
            AppBar(
                showBackArrow = true,
                showSortButton = true,
                onNavigate = onNavigate,
                title = {
                    Text(
                        text = stringResource(R.string.history),
                        fontFamily = GlobalFont
                    )
                }
            )
        },
        floatingActionButton = {
            if (isHistoryEnable) {
                FloatingActionButton(
                    onClick = { onEvents(HistoryEvents.DeleteAllCalculation(state.calculation)) }
                ) {
                    Icon(
                        painter = painterResource(R.drawable.trash_rounded),
                        contentDescription = null
                    )
                }
            }
        }
    ) { values ->
        if (!isHistoryEnable) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.history_not_enabled),
                    fontFamily = GlobalFont
                )
                Spacer(Modifier.height(10.dp))
                Button(
                    onClick = { isHistoryEnable = !isHistoryEnable }
                ) {
                    Text(
                        text = stringResource(R.string.enable_history),
                        fontFamily = GlobalFont
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(values)
            ) {
                itemsIndexed(
                    items = sortedCalculations,
                    key = { _, item -> item.id }
                ) { index, item ->
                    CalculationItem(
                        calculation = item,
                        onEvents = onEvents,
                        topDp = if (index == 0) 24.dp else 4.dp,
                        bottomDp = if (index == state.calculation.size - 1) 24.dp else 4.dp,
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
    topDp: Dp,
    bottomDp: Dp,
    modifier: Modifier = Modifier
) {
    val decimalSetting by rememberDecimal()

    Card(
        modifier = modifier
            .padding(horizontal = 10.dp, vertical = 3.dp)
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
                Text(
                    text = formatOrNot(calculation.operation, decimalSetting),
                    fontSize = 20.sp,
                    modifier = Modifier.basicMarquee(),
                    fontFamily = GlobalFont
                )
                Text(
                    text = "= ${formatOrNot(calculation.result, decimalSetting)}",
                    fontSize = 22.sp,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
                    modifier = Modifier.basicMarquee(),
                    fontFamily = GlobalFont
                )
            }
            IconButton(
                onClick = { onEvents(HistoryEvents.DeleteCalculation(calculation)) }
            ) {
                Icon(
                    painter = painterResource(R.drawable.trash_rounded),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}