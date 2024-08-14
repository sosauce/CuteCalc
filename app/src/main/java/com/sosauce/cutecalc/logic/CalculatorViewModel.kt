package com.sosauce.cutecalc.logic

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import java.text.DecimalFormat

class CalcViewModel : ViewModel() {

    //var displayText by mutableStateOf(TextFieldValue())
    private val formatter = DecimalFormat("#,###.##")
    var displayText by mutableStateOf("")
    var displayTextFormated: String by mutableStateOf(formatNumber(displayText))


    private fun formatNumber(numberString: String): String {
        val number = try {
            numberString.toDouble()
        } catch (e: NumberFormatException) {
            return numberString // Return as is if parsing to double fails
        }

        val formatter = DecimalFormat("#,###.##")

        return formatter.format(number)
    }


    fun handleAction(action: CalcAction) {
        displayText = when (action) {
//            is CalcAction.GetResult -> TextFieldValue(Evaluator.eval(displayText.text))
//            is CalcAction.ResetField -> TextFieldValue("")
//            is CalcAction.RemoveLast -> displayText.delete()
//            is CalcAction.AddToField -> TextFieldValue(displayText.text + action.value)
            is CalcAction.GetResult -> Evaluator.eval(displayText)
            is CalcAction.ResetField -> ""
            is CalcAction.RemoveLast -> displayText.dropLast(1)
            is CalcAction.AddToField -> displayText + action.value
        }
    }
}

//fun TextFieldValue.delete(): TextFieldValue {
//    if (selection.collapsed && selection.start == 0) return this
//
//    val textBeforeSelection = text.substring(0, selection.start)
//    val textAfterSelection = text.substring(selection.end)
//    val selectedText = text.substring(selection.start, selection.end)
//
//    val (newText, newCursorPosition) = if (selectedText.isEmpty()) {
//        val newTextBeforeSelection = textBeforeSelection.dropLast(1)
//        val newCursorPos = textBeforeSelection.length - 1
//        newTextBeforeSelection + textAfterSelection to newCursorPos
//    } else {
//        textBeforeSelection + textAfterSelection to selection.start
//    }
//
//    val finalCursorPosition = newCursorPosition.coerceAtLeast(0)
//
//    return copy(
//        text = newText,
//        selection = TextRange(finalCursorPosition)
//    )
//}




