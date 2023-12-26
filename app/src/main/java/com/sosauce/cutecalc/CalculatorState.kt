package com.sosauce.cutecalc

// this file defines the state of the calc when launched. Shouldn't be modified

data class CalculatorState(
    val number1: String = "",
    val number2: String = "",
    val operation: CalculatorOperation? = null
)
