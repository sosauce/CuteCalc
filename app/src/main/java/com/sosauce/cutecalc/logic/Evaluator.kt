package com.sosauce.cutecalc.logic

import com.notkamui.keval.Keval
import com.notkamui.keval.KevalException
import com.notkamui.keval.KevalInvalidArgumentException
import com.notkamui.keval.KevalZeroDivisionException
import kotlin.math.floor
import kotlin.math.pow

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
                if (it < 0) throw KevalInvalidArgumentException("factorial of a negative number")
                if (floor(it) != it) throw KevalInvalidArgumentException("factorial of a non-integer")
                (1..it.toInt()).fold(1.0) { acc, i -> acc * i }
            }
        }
    }

    @JvmStatic
    fun eval(formula: String): String = try {
        KEVAL.eval(formula).toBigDecimal().stripTrailingZeros().toPlainString()
    } catch (e: KevalZeroDivisionException) {
        "Undefined (zero division)"
    } catch (e: KevalException) {
        "Error"
    }
}