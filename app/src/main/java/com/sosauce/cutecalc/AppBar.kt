@file:OptIn(ExperimentalMaterial3Api::class)

package com.sosauce.cutecalc

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.sosauce.cutecalc.logic.navigation.Screens
import com.sosauce.cutecalc.logic.rememberSortHistoryASC
import com.sosauce.cutecalc.ui.theme.GlobalFont
import com.sosauce.cutecalc.utils.thenIf

@Composable
fun AppBar(
    title: @Composable () -> Unit = {},
    showBackArrow: Boolean,
    showSortButton: Boolean = false,
    onNavigate: (Screens) -> Unit,
) {
    var dropDownExpanded by remember { mutableStateOf(false) }
    var sortASC by rememberSortHistoryASC()
    val surfaceContainer = MaterialTheme.colorScheme.surfaceContainer


    TopAppBar(
        title = title,
        navigationIcon = {
            if (showBackArrow) {
                IconButton(onClick = { onNavigate(Screens.MAIN) }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                        contentDescription = "Back arrow"
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(Color.Transparent),
        actions = {
            if (!showBackArrow) {
                Spacer(Modifier.width(5.dp))
                IconButton(onClick = { onNavigate(Screens.HISTORY) }) {
                    Icon(
                        painter = painterResource(R.drawable.history_rounded),
                        contentDescription = stringResource(R.string.history),
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }
                IconButton(onClick = { onNavigate(Screens.SETTINGS) }) {
                    Icon(
                        imageVector = Icons.Rounded.Settings,
                        contentDescription = stringResource(R.string.settings),
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
            if (showSortButton) {
                IconButton(
                    onClick = { dropDownExpanded = true }
                ) {
                    Icon(
                        painter = painterResource(R.drawable.sort_rounded),
                        contentDescription = null
                    )
                }
                DropdownMenu(
                    expanded = dropDownExpanded,
                    onDismissRequest = { dropDownExpanded = false },
                    modifier = Modifier
                        .width(180.dp)
                        .background(MaterialTheme.colorScheme.surface),
                    shape = RoundedCornerShape(24.dp)
                ) {
                    Column(
                        verticalArrangement = Arrangement.Center
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp)
                                .padding(5.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .clickable { sortASC = true }
                                .thenIf(sortASC) {
                                    Modifier.background(
                                        color = surfaceContainer,
                                        shape = RoundedCornerShape(12.dp)
                                    )
                                },
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = stringResource(R.string.ascending),
                                fontFamily = GlobalFont,
                                modifier = Modifier.padding(start = 15.dp)
                            )
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp)
                                .padding(5.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .clickable { sortASC = false }
                                .thenIf(!sortASC) {
                                    Modifier.background(
                                        color = surfaceContainer,
                                        shape = RoundedCornerShape(12.dp)
                                    )
                                },
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = stringResource(R.string.descending),
                                fontFamily = GlobalFont,
                                modifier = Modifier.padding(start = 15.dp)
                            )
                        }
                    }

                }
            }
        }
    )
}





