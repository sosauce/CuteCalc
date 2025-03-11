package com.sosauce.cutecalc.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEach
import com.sosauce.cutecalc.R
import com.sosauce.cutecalc.logic.rememberSortHistoryASC
import com.sosauce.cutecalc.utils.thenIf

@Composable
fun BoxScope.HistoryActionButtons(
    onDeleteHistory: () -> Unit
) {
    var dropDownExpanded by remember { mutableStateOf(false) }
    var sortASC by rememberSortHistoryASC()
    val surfaceContainer = MaterialTheme.colorScheme.surfaceContainerHigh
    val sorting = remember {
        listOf(
            R.string.ascending,
            R.string.descending
        )
    }


    SmallFloatingActionButton(
        onClick = {},
        modifier = Modifier
            .padding(end = 15.dp)
            .align(Alignment.BottomEnd)
            .navigationBarsPadding(),
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
                        painter = if (it) painterResource(R.drawable.sort_rounded) else rememberVectorPainter(
                            Icons.Rounded.Close
                        ),
                        contentDescription = null
                    )
                }
            }
            IconButton(
                onClick = onDeleteHistory
            ) {
                Icon(
                    painter = painterResource(R.drawable.trash_rounded),
                    contentDescription = null
                )
            }

            DropdownMenu(
                expanded = dropDownExpanded,
                onDismissRequest = { dropDownExpanded = false },
                shape = RoundedCornerShape(24.dp)
            ) {
                sorting.fastForEach {
                    DropdownMenuItem(
                        text = { CuteText(stringResource(it)) },
                        onClick = { sortASC = it == R.string.descending },
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .thenIf((!sortASC && it == R.string.ascending) || (sortASC && it == R.string.descending)) {
                                Modifier.background(
                                    color = surfaceContainer,
                                    shape = RoundedCornerShape(12.dp)
                                )
                            }
                    )
                }
            }
        }
    }
}