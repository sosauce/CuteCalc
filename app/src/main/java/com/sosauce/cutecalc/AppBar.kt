@file:OptIn(ExperimentalMaterial3Api::class)

package com.sosauce.cutecalc

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.automirrored.rounded.Sort
import androidx.compose.material.icons.rounded.History
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
import androidx.compose.ui.unit.dp
import com.sosauce.cutecalc.logic.navigation.Screens
import com.sosauce.cutecalc.logic.rememberSortHistoryASC
import com.sosauce.cutecalc.ui.theme.GlobalFont

@SuppressLint("SuspiciousIndentation")
@Composable
fun AppBar(
    title: String? = null,
    showBackArrow: Boolean,
    showSortButton: Boolean = false,
    onNavigateUp: () -> Unit,
    onNavigate: (Screens) -> Unit
) {
    var dropDownExpanded by remember { mutableStateOf(false) }
    var sortASC by rememberSortHistoryASC()


    TopAppBar(
        title = {
            title?.let {
                Text(
                    text = it,
                    fontFamily = GlobalFont
                )
            }
        },
        navigationIcon = {
            if (showBackArrow) {
                IconButton(onClick = onNavigateUp) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                        contentDescription = "Back arrow"
                    )
                }
            }
        },
        colors = TopAppBarDefaults.largeTopAppBarColors(Color.Transparent),
        actions = {
            if (!showBackArrow) {
                IconButton(onClick = { onNavigate(Screens.History) }) {
                    Icon(
                        imageVector = Icons.Rounded.History,
                        contentDescription = "History",
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }
                IconButton(onClick = { onNavigate(Screens.Settings) }) {
                    Icon(
                        imageVector = Icons.Rounded.Settings,
                        contentDescription = "More",
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
            if (showSortButton) {
                IconButton(
                    onClick = { dropDownExpanded = true }
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Rounded.Sort,
                        contentDescription = null
                    )
                }
                DropdownMenu(
                    expanded = dropDownExpanded,
                    onDismissRequest = { dropDownExpanded = false },
                    modifier = Modifier
                        .width(180.dp)
                        .background(color = MaterialTheme.colorScheme.surface)
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
                                .then(
                                    if (sortASC) {
                                        Modifier.background(
                                            color = MaterialTheme.colorScheme.surfaceContainer,
                                            shape = RoundedCornerShape(12.dp)
                                        )
                                    } else {
                                        Modifier
                                    }
                                ),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Ascending",
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
                                .then(
                                    if (!sortASC) {
                                        Modifier.background(
                                            color = MaterialTheme.colorScheme.surfaceContainer,
                                            shape = RoundedCornerShape(12.dp)
                                        )
                                    } else {
                                        Modifier
                                    }
                                ),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Descending",
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





