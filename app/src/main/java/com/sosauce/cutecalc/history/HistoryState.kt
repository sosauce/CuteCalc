package com.sosauce.cutecalc.history

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

data class HistoryState(
    val calculation: List<Calculation> = emptyList(),
    val operation: MutableState<String> = mutableStateOf(""),
    val result: MutableState<String> = mutableStateOf("")
)