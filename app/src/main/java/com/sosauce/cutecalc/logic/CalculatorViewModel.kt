package com.sosauce.cutecalc.logic

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel

class CalcViewModel : ViewModel() {

    var displayText by mutableStateOf(TextFieldValue(""))
    private val processedText by derivedStateOf {
        displayText.text.replace("π", "PI")
    }
    val preview by derivedStateOf {
        when(displayText.text) {
            "" -> ""
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
            is CalcAction.RemoveLast -> {
                val text = displayText.text
                val cursorPos = displayText.selection.start
                if (cursorPos > 0) {
                    val newText = text.removeRange(cursorPos - 1, cursorPos)
                    TextFieldValue(
                        text = newText,
                        selection = TextRange(cursorPos - 1)
                    )
                }

                else {
                    TextFieldValue(text.dropLast(1))
                }
            }
            is CalcAction.AddToField -> TextFieldValue(displayText.text + action.value)
        }
    }
}




