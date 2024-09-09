package com.sosauce.cutecalc.history

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HistoryViewModel(
    private val dao: HistoryDao
) : ViewModel() {

    private var allCalculations = dao.getAllCalculations()
    private val _state = MutableStateFlow(HistoryState())
    val state = combine(_state, allCalculations) { state, calculation1 ->
        state.copy(
            calculation = calculation1
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), HistoryState())

    fun onEvent(event: HistoryEvents) {
        when (event) {
            is HistoryEvents.AddCalculation -> {
                val calculation = Calculation(
                    operation = state.value.operation.value,
                    result = state.value.result.value
                )
                viewModelScope.launch { dao.insertCalculation(calculation) }
                _state.update {
                    it.copy(
                        operation = mutableStateOf(""),
                        result = mutableStateOf("")
                    )
                }
            }

            is HistoryEvents.DeleteCalculation -> {
                viewModelScope.launch { dao.deleteCalculation(event.calculation) }
            }
            is HistoryEvents.DeleteAllCalculation -> {
                viewModelScope.launch { dao.deleteAllCalculations(event.calculations) }
            }
        }
    }
}