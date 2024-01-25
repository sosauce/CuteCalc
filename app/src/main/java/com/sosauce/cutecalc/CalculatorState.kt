package com.sosauce.cutecalc

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.notkamui.keval.KevalInvalidExpressionException
import com.notkamui.keval.KevalZeroDivisionException
import com.notkamui.keval.keval

data class CalcState(var field: String)
sealed interface CalcAction {
    object GetResult : CalcAction
    object ResetField : CalcAction
    object RemoveLast : CalcAction
    data class AddToField(val value: String) : CalcAction
}

class GetFormulaResultUseCase {
    @Composable
    operator fun invoke(formula: String): String {
        // Handle back-to-back operators by replacing them with a single operator
        val cleanedFormula = handleBackToBackOperators(formula)
        return try {
            val result = cleanedFormula.keval().toBigDecimal().stripTrailingZeros().toPlainString()

            // Check for division by zero
            if (result == "Infinity" || result == "-Infinity") {
                throw KevalZeroDivisionException()
            }

            result
        } catch (e: KevalZeroDivisionException) {
            // Handle zero division, e.g., by returning a specific error message
            stringResource(id = R.string.div_zero_error)
        } catch (e: KevalInvalidExpressionException) {
            // Handle invalid expression, e.g., by returning an error message
            "Error"
        }
    }

    private fun handleBackToBackOperators(formula: String): String {
        // Define the set of allowed operators
        val allowedOperators = setOf('+', '-', '*', '/')

        // Replace back-to-back occurrences with a single operator
        val cleanedFormula = StringBuilder()
        var lastChar: Char? = null

        for (char in formula) {
            if (lastChar != null && allowedOperators.contains(lastChar) && allowedOperators.contains(char)) {
                // If both current and last characters are operators, skip the current one
                continue
            }
            cleanedFormula.append(char)
            lastChar = char
        }

        return cleanedFormula.toString()
    }
}
