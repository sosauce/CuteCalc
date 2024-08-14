package com.sosauce.cutecalc.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.sosauce.cutecalc.AppBar
import com.sosauce.cutecalc.history.HistoryEvents
import com.sosauce.cutecalc.history.HistoryState

@Composable
fun HistoryScreen(
    navController: NavController,
    state: HistoryState,
    onEvents: (HistoryEvents) -> Unit
) {
    HistoryScreenContent(
        navController = navController,
        state = state,
        onEvents = { onEvents(it) }

    )
}

@Composable
private fun HistoryScreenContent(
    navController: NavController,
    state: HistoryState,
    onEvents: (HistoryEvents) -> Unit
) {
    Scaffold(
        topBar = {
            AppBar(
                navController = navController,
                showBackArrow = true,
                title = "History"
            )
        },
//        floatingActionButton = {
//            FloatingActionButton(
//                onClick = { /*TODO*/ }
//            ) {
//                Icon(
//                    imageVector = Icons.Outlined.Delete,
//                    contentDescription = null
//                )
//            }
//        }
    ) { values ->
        LazyColumn(
            modifier = Modifier.padding(values)
        ) {
            items(
                count = state.calculation.size
            ) { index ->
                CalculationItem(
                    index = index,
                    state = state,
                    onEvents = { onEvents(it) },
                    topDp = if (index == 0) 24.dp else 4.dp,
                    bottomDp = if (index == state.calculation.size - 1) 24.dp else 4.dp
                )
            }
        }
    }
}

@Composable
private fun CalculationItem(
    index: Int,
    state: HistoryState,
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
                    text = state.calculation[index].operation,
                    fontSize = 20.sp
                )
                Text(
                    text = "= ${state.calculation[index].result}",
                    fontSize = 22.sp,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)
                )
            }
            IconButton(
                onClick = { onEvents(HistoryEvents.DeleteCalculation(state.calculation[index])) }
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