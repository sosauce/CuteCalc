package com.sosauce.cutecalc.logic

import java.text.NumberFormat
import java.util.Locale

// A bit wonky ik
fun formatOrNot(
    expression: String,
    shouldFormat: Boolean
): String {
    val numberFormat = NumberFormat.getNumberInstance(Locale.getDefault())
    val regex = """\d+(\.\d+)?""".toRegex()

    return if (shouldFormat) {
        regex.replace(expression) { matchResult ->
            numberFormat.format(matchResult.value.toDouble())
        }
    } else expression
}