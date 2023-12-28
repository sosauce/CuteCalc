package com.sosauce.cutecalc

// don't modify (i mean, u can, but u will break the app)

sealed class CalculatorOperation(val symbol: String) {
    object Add: CalculatorOperation("+")
    object Subtract: CalculatorOperation("-")
    object Multiply: CalculatorOperation("x")
    object Divide: CalculatorOperation("/")
    object Percentage: CalculatorOperation("%")
}
