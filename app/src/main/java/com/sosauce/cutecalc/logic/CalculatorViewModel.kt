package com.sosauce.cutecalc.logic

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class CalcViewModel : ViewModel() {

    private val getFormulaResult: GetFormulaResultUseCase = GetFormulaResultUseCase()

    private val _state = MutableStateFlow(CalcState(""))
    val state: StateFlow<CalcState>
        get() = _state.asStateFlow()

    private fun setState(reducer: CalcState.() -> CalcState) {
        _state.value = state.value.reducer()
    }


    fun handleAction(action: CalcAction) {
        when (action) {
            CalcAction.GetResult -> {
                setState { copy(field = getFormulaResult(field)) }
            }

            CalcAction.ResetField -> {
                setState { copy(field = "") }
            }

            CalcAction.RemoveLast -> {
                setState { copy(field = field.dropLast(1)) }
            }

            is CalcAction.AddToField -> setState { copy(field = field + action.value) }
        }
    }
}
