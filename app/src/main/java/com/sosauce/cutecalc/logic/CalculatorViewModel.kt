package com.sosauce.cutecalc.logic

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class CalcViewModel : ViewModel() {

    var displayText by mutableStateOf("")

    fun handleAction(action: CalcAction) {
        displayText = when (action) {
            is CalcAction.GetResult -> Evaluator.eval(displayText)
            is CalcAction.ResetField -> ""
            is CalcAction.RemoveLast -> displayText.dropLast(1)
            is CalcAction.AddToField -> displayText + action.value
        }
    }
}




