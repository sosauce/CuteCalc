package com.sosauce.cutecalc

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class CalculatorViewModel: ViewModel() {

    var state by mutableStateOf(CalculatorState())

    fun onAction(action: CalculatorAction) {
        when(action) {
            is CalculatorAction.Number -> enterNumber(action.number)
            is CalculatorAction.Delete -> delete()
            is CalculatorAction.Clear -> state = CalculatorState()
            is CalculatorAction.Operation -> enterOperation(action.operation)
            is CalculatorAction.Decimal -> enterDecimal()
            is CalculatorAction.Calculate -> calculate()
        }
    }

    private fun enterOperation(operation: CalculatorOperation) {
        if (state.number1.isNotBlank() && state.number2.isNotBlank()) {
            calculate()
            state = state.copy(operation = operation, number2 = "") // Clear number2 for the next input
        } else if (state.number1.isNotBlank() && state.operation != null && state.number2.isBlank()) {
            state = state.copy(operation = operation) // Update the operation if a number and an operation exist
        } else if (state.number1.isBlank() && state.operation == null) {
            state = state.copy(operation = operation) // If no number is entered yet, simply update the operation
        } else if (state.number1.isNotBlank()) {
            state = state.copy(operation = operation) // If number1 is not blank, update the operation
        }
    }


    private fun calculate() {
        val number1 = state.number1.toDoubleOrNull()
        val number2 = state.number2.toDoubleOrNull()

        val result = when (state.operation) {
            is CalculatorOperation.Add -> number1?.plus(number2 ?: 0.0)
            is CalculatorOperation.Subtract -> number1?.minus(number2 ?: 0.0)
            is CalculatorOperation.Multiply -> number1?.times(number2 ?: 1.0)
            is CalculatorOperation.Divide -> number1?.div(number2 ?: 1.0)
            is CalculatorOperation.Percentage -> {
                // Percentage operation for a single number
                number1?.div(100)
            }
            else -> null
        }

        result?.let { calculatedResult ->
            val formattedResult = if (calculatedResult == calculatedResult.toInt().toDouble()) {
                calculatedResult.toInt().toString()
            } else {
                calculatedResult.toString()
            }

            state = state.copy(
                number1 = formattedResult.take(15),
                number2 = "",
                operation = null
            )
        }
    }


    private fun delete() {
        when {
            state.number2.isNotBlank() -> state = state.copy(
                number2 = state.number2.dropLast(1)
            )
            state.operation != null -> state = state.copy(
                operation = null
            )
            state.number1.isNotBlank() -> state = state.copy(
                number1 = state.number1.dropLast(1)
            )
        }
    }

    private fun enterDecimal() {
        if(state.operation == null && !state.number1.contains(".") && state.number1.isNotBlank()) {
            state = state.copy(
                number1 = state.number1 + "."
            )
            return
        } else if(!state.number2.contains(".") && state.number2.isNotBlank()) {
            state = state.copy(
                number2 = state.number2 + "."
            )
        }
    }

    private fun enterNumber(number: Int) {
        if(state.operation == null) {
            state = state.copy(
                number1 = state.number1 + number
            )
            return
        }
        state = state.copy(
            number2 = state.number2 + number
        )
    }
}