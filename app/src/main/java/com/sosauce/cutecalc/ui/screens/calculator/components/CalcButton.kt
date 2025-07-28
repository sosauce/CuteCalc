package com.sosauce.cutecalc.ui.screens.calculator.components

import androidx.compose.ui.graphics.Color

data class CalcButton(
    val text: String,
    val backgroundColor: Color,
    val onClick: () -> Unit,
    val onLongClick: (() -> Unit)? = null,
)
