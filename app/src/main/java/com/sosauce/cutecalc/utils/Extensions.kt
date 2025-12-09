package com.sosauce.cutecalc.utils

import android.app.Activity
import android.os.Build
import android.view.WindowManager
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.text.input.OutputTransformation
import androidx.compose.foundation.text.input.TextFieldBuffer
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.delete
import androidx.compose.foundation.text.input.insert
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.sosauce.cutecalc.domain.model.Calculation
import java.text.DecimalFormatSymbols

fun Modifier.thenIf(
    condition: Boolean,
    modifier: Modifier.() -> Modifier
): Modifier {
    return if (condition) {
        this.then(modifier())
    } else this
}


fun List<Calculation>.sort(
    newestFirst: Boolean
): List<Calculation> {
    return if (newestFirst) {
        this.sortedByDescending { it.id }
    } else {
        this
    }
}

@Composable
fun anyLightColorScheme(): ColorScheme {
    val context = LocalContext.current

    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        dynamicLightColorScheme(context)
    } else {
        lightColorScheme()
    }
}

@Composable
fun anyDarkColorScheme(): ColorScheme {
    val context = LocalContext.current

    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        dynamicDarkColorScheme(context)
    } else {
        darkColorScheme()
    }
}

val LazyListState.showBottomBar
    get() =
        if (layoutInfo.totalItemsCount == 0) {
            true
        } else if (
            layoutInfo.visibleItemsInfo.firstOrNull()?.index == 0 &&
            layoutInfo.visibleItemsInfo.lastOrNull()?.index == layoutInfo.totalItemsCount - 1
        ) {
            true
        } else {
            layoutInfo.visibleItemsInfo.lastOrNull()?.index != layoutInfo.totalItemsCount - 1
        }

fun TextFieldState.insertText(text: String) {

    val textAsChar =
        text.first() // We're not mean to be able to input more than one char at once anyways
    val expression = this.text
    val cursorPosition = selection.start
    val charInfrontCursor = expression.getOrNull(cursorPosition - 1) ?: ' '
    val charBehindCursor = expression.getOrNull(cursorPosition) ?: ' '


    when {
        expression.isEmpty() -> {
            if (textAsChar.canStartExpression()) {
                edit { insert(cursorPosition, text) }
            }
        }

        textAsChar.isOperator() -> {
            if (charInfrontCursor.isChainable() && charBehindCursor.isChainable()) {
                edit { insert(cursorPosition, text) }
            }
        }

        else -> edit { insert(cursorPosition, text) }
    }
}

fun TextFieldState.backspace() {
    val cursorPosition = selection.start
    if (selection.collapsed && cursorPosition > 0) {
        edit {
            delete(cursorPosition - 1, cursorPosition)
        }
    }
}


fun String.isErrorMessage(): Boolean {
    return any { char -> char.isLetter() }
}

fun String.whichParenthesis(): String {
    return if (count { it == '(' } > count { it == ')' }) {
        ")"
    } else {
        "("
    }
}

fun Char.isChainable(): Boolean {

    val unchainableOperators = listOf('×', '/', '^', '.')


    return this !in unchainableOperators
}

fun Char.canStartExpression(): Boolean {
    val startables = listOf('-', '+', '√', 'π', '(')
    return this in startables || this.isDigit()
}

fun Char.isOperator(): Boolean {
    val allOperators = listOf('×', '/', '^', '.', '√', '!', '%')

    return this in allOperators
}

/**
 * Formats a number not an expression !!
 */
fun String.formatNumber(shouldFormat: Boolean): String {
    val number = this
    val localSymbols = DecimalFormatSymbols.getInstance()

    if (number.any { it.isLetter() } || !shouldFormat) return number
    var integer = number.takeWhile { it != '.' }
    val decimal = number.removePrefix(integer).replace('.', localSymbols.decimalSeparator)
    val offset = 3 - integer.length.mod(3)
    val formattedInteger = if (offset != 3) {
        integer = " ".repeat(offset) + integer
        integer.chunked(3).joinToString(localSymbols.groupingSeparator.toString()).drop(offset)
    } else {
        integer.chunked(3).joinToString(localSymbols.groupingSeparator.toString())
    }

    return "${formattedInteger}${decimal}"
}

fun String.formatExpression(shouldFormat: Boolean): String {

    if (!shouldFormat) return this

    var expression = this
    val numberRegex = Regex("[\\d.]+")

    numberRegex.findAll(expression).forEach { result ->
        expression = expression.replace(result.value, result.value.formatNumber(true))
    }

    return expression

}


object FormatTransformation : OutputTransformation {
    override fun TextFieldBuffer.transformOutput() {
        val expression = this.originalText.toString()

        if (expression.isEmpty()) return

        val localSymbols = DecimalFormatSymbols.getInstance()

        expression.formatExpression(true).forEachIndexed { index, char ->
            when (char) {
                localSymbols.groupingSeparator -> insert(
                    index,
                    localSymbols.groupingSeparator.toString()
                )

                localSymbols.decimalSeparator -> replace(
                    index,
                    index + 1,
                    localSymbols.decimalSeparator.toString()
                )
            }
        }
    }
}

fun Activity.showOnLockScreen(show: Boolean) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
        setShowWhenLocked(show)
    } else {
        if (show) {
            window.addFlags(
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED or
                        WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
            )
        }
    }
}

fun Modifier.selfAlignHorizontally(align: Alignment.Horizontal = Alignment.CenterHorizontally): Modifier {
    return this.then(
        Modifier
            .fillMaxWidth()
            .wrapContentWidth(align)
    )
}




