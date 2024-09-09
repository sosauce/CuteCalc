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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.rounded.Delete
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.sosauce.cutecalc.AppBar
import com.sosauce.cutecalc.history.Calculation
import com.sosauce.cutecalc.history.HistoryEvents
import com.sosauce.cutecalc.history.HistoryState
import com.sosauce.cutecalc.logic.rememberUseHistory
import com.sosauce.cutecalc.ui.theme.GlobalFont

@Composable
fun HistoryScreen(
    navController: NavController,
    state: HistoryState,
    onEvents: (HistoryEvents) -> Unit
) {
    HistoryScreenContent(
        navController = navController,
        state = state,
        onEvents = onEvents

    )
}

@Composable
private fun HistoryScreenContent(
    navController: NavController,
    state: HistoryState,
    onEvents: (HistoryEvents) -> Unit
) {
    var isHistoryEnable by rememberUseHistory()

    Scaffold(
        topBar = {
            AppBar(
                navController = navController,
                showBackArrow = true,
                title = "History"
            )
        },
        floatingActionButton = {
            if (isHistoryEnable) {
                FloatingActionButton(
                    onClick = { onEvents(HistoryEvents.DeleteAllCalculation(state.calculation)) }
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Delete,
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
                    text = "It looks like history isn't enabled !",
                    fontFamily = GlobalFont
                )
                Spacer(Modifier.height(10.dp))
                Button(
                    onClick = { isHistoryEnable = !isHistoryEnable }
                ) {
                    Text(
                        text = "Enable History",
                        fontFamily = GlobalFont
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier.padding(values)
            ) {
                itemsIndexed(
                    items = state.calculation,
                    key = { _, item -> item.id }
                ) { index, item ->
                    CalculationItem(
                        calculation = item,
                        onEvents = onEvents,
                        topDp = if (index == 0) 24.dp else 4.dp,
                        bottomDp = if (index == state.calculation.size - 1) 24.dp else 4.dp
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
    bottomDp: Dp
) {
    Card(
        modifier = Modifier
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
                    text = calculation.operation,
                    fontSize = 20.sp,
                    modifier = Modifier.basicMarquee(),
                    fontFamily = GlobalFont
                )
                Text(
                    text = "= ${calculation.result}",
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
                    imageVector = Icons.Default.Delete,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}