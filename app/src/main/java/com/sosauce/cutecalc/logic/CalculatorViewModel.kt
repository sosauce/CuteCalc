package com.sosauce.cutecalc.logic

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.getSelectedText
import androidx.compose.ui.text.input.getTextAfterSelection
import androidx.compose.ui.text.input.getTextBeforeSelection
import androidx.lifecycle.ViewModel

class CalcViewModel : ViewModel() {

    var displayText by mutableStateOf(TextFieldValue(""))
    private val processedText by derivedStateOf {
        displayText.text.replace("π", "PI")
    }
    val preview by derivedStateOf {
        when(displayText) {
            TextFieldValue("") -> ""
            else -> "= ${Evaluator.eval(processedText)}"

        }
    }
    val parenthesis by derivedStateOf {
        if (displayText.text.count { it == '(' } > displayText.text.count { it == ')' }) ")" else "("
    }



    fun handleAction(action: CalcAction) {
        displayText = when (action) {
            is CalcAction.GetResult -> TextFieldValue(Evaluator.eval(displayText.text))
            is CalcAction.ResetField -> TextFieldValue("")
            is CalcAction.Backspace -> displayText.backSpace()
            is CalcAction.AddToField -> displayText.insertText(action.value)
        }
    }
}

fun TextFieldValue.insertText(insertText: String): TextFieldValue {
    val maxChars = text.length
    val textBeforeSelection = getTextBeforeSelection(maxChars)
    val textAfterSelection = getTextAfterSelection(maxChars)
    val newText = "$textBeforeSelection$insertText$textAfterSelection"
    val newCursorPosition = textBeforeSelection.length + insertText.length
    return TextFieldValue(
        text = newText,
        selection = TextRange(newCursorPosition)
    )
}

fun TextFieldValue.backSpace(): TextFieldValue {
    val maxChars = text.length
    val textBeforeSelection = getTextBeforeSelection(maxChars)
    val textAfterSelection = getTextAfterSelection(maxChars)
    val selectedText = getSelectedText()
    if (textBeforeSelection.isEmpty() && selectedText.isEmpty()) return this
    val newText =
        if (selectedText.isEmpty()) {
            "${textBeforeSelection.dropLast(1)}$textAfterSelection"
        } else {
            "$textBeforeSelection$textAfterSelection"
        }
    val newCursorPosition = textBeforeSelection.length - if (selectedText.isEmpty()) 1 else 0
    return TextFieldValue(
        text = newText,
        selection = TextRange(newCursorPosition)
    )
}




