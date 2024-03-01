package com.sosauce.cutecalc.logic
import java.text.DecimalFormat

data class CalcState(var field: String) {
    fun formattedField(): String {
        return formatNumber(field)
    }

    private fun formatNumber(numberString: String): String {
        val number = try {
            numberString.toDouble()
        } catch (e: NumberFormatException) {
            return numberString // Return as is if parsing to double fails
        }
        val formatter = DecimalFormat("#,###.##")
        return formatter.format(number)
    }
}
sealed interface CalcAction {
    object GetResult : CalcAction
    object ResetField : CalcAction
    object RemoveLast : CalcAction
    data class AddToField(val value: String) : CalcAction
}

