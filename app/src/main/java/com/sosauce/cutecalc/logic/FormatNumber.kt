package com.sosauce.cutecalc.logic

import java.text.DecimalFormat

fun formatNumber(numberString: String): String {
    val number = try {
        numberString.toDouble()
    } catch (e: NumberFormatException) {
        return numberString // Return as is if parsing to double fails
    }

    val formatter = DecimalFormat("#,###.##")

    return formatter.format(number)
}