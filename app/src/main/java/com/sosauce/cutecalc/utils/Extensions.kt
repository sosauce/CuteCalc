package com.sosauce.cutecalc.utils

import androidx.compose.ui.Modifier

fun Modifier.thenIf(
    condition: Boolean,
    modifier: Modifier.() -> Modifier
): Modifier {
    return if (condition) {
        this.then(modifier())
    } else this
}