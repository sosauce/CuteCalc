package com.sosauce.cutecalc.data.calculator

import com.notkamui.keval.Keval
import com.notkamui.keval.KevalInvalidArgumentException
import com.notkamui.keval.KevalZeroDivisionException
import java.math.RoundingMode
import kotlin.math.floor
import kotlin.math.pow
import kotlin.math.sqrt

class NegativeSquareRootException : RuntimeException("Negative square root")
class ValueTooLargeException : RuntimeException("Value too large")
object Evaluator {

    private val KEVAL = Keval.create {
        binaryOperator {
            symbol = '+'
            precedence = 2
            isLeftAssociative = true
            implementation = { a, b -> a + b }
        }
        unaryOperator {
            symbol = '+'
            isPrefix = true
            implementation = { it }
        }
        binaryOperator {
            symbol = '-'
            precedence = 2
            isLeftAssociative = true
            implementation = { a, b -> a - b }
        }
        unaryOperator {
            symbol = '-'
            isPrefix = true
            implementation = { -it }
        }

        binaryOperator {
            symbol = '×'
            precedence = 3
            isLeftAssociative = true
            implementation = { a, b -> a * b }
        }

        binaryOperator {
            symbol = '/'
            precedence = 3
            isLeftAssociative = true
            implementation = { a, b ->
                if (b == 0.0) throw KevalZeroDivisionException()
                a / b
            }
        }

        binaryOperator {
            symbol = '^'
            precedence = 4
            isLeftAssociative = false
            implementation = { a, b -> a.pow(b) }
        }

        unaryOperator {
            symbol = '!'
            isPrefix = false
            implementation = {
                if (it < 0) throw KevalInvalidArgumentException("Factorial of a negative number")
                if (floor(it) != it) throw KevalInvalidArgumentException("Factorial of a non-integer")
                (1..it.toInt()).fold(1.0) { acc, i -> acc * i }
            }
        }

        unaryOperator {
            symbol = '√'
            isPrefix = true
            implementation =
                { arg -> if (arg < 0) throw NegativeSquareRootException() else sqrt(arg) }
        }

        unaryOperator {
            symbol = '%'
            isPrefix = false
            implementation = { arg -> arg / 100 }
        }

        constant {
            name = "PI"
            value = Math.PI
        }

    }

    @JvmStatic
    fun eval(
        formula: String,
        precision: Int
    ): String = try {
        val result = KEVAL
            .eval(formula.replace("π", "PI").handleRelativePercentage())

        if (result > Double.MAX_VALUE) {
            throw ValueTooLargeException()
        } else {
            result
                .toBigDecimal()
                .setScale(precision, RoundingMode.HALF_UP)
                .stripTrailingZeros()
                .toPlainString()

        }
    } catch (e: Exception) {
        e.message ?: "Undetermined error"
    }


    // We don't call "handleRelativePercentage" here to avoid recursive
    @JvmStatic
    private fun evalParenthesis(formula: String): String {
        val result = KEVAL.eval(formula)
        return if (result > Double.MAX_VALUE) {
            throw ValueTooLargeException()
        } else {
            result.toBigDecimal().stripTrailingZeros().toPlainString()
        }
    }

    private fun String.handleRelativePercentage(): String {
        val regex = Regex("""(\d+(?:\.\d+)?)\s*([+\-*])\s*(\d+(?:\.\d+)?)%""")

        return regex.replace(this.processParenthesisExpression()) { match ->
            val firstOperand = match.groupValues[1].toDouble()
            val operator = match.groupValues[2]
            val percentage = match.groupValues[3].toDouble()

            when (operator) {
                "+" -> "$firstOperand + ($firstOperand * $percentage / 100)"
                "-" -> "$firstOperand - ($firstOperand * $percentage / 100)"
                "*" -> "$firstOperand * ($percentage / 100)"
                else -> "$firstOperand"
            }

        }

    }

    private fun String.processParenthesisExpression(): String {
        val parenthesisRegex = Regex("""\(([^()]+)\)""")
        var expression = this


        parenthesisRegex.findAll(this).forEach { matchResult ->
            val calculated = evalParenthesis(matchResult.value)
            val replaceWith = if (this.contains("%")) calculated else "($calculated)"
            expression = expression.replace(matchResult.value, replaceWith)
        }
        return expression
    }


}
