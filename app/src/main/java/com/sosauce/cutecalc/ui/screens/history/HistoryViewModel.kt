package com.sosauce.cutecalc.ui.screens.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sosauce.cutecalc.domain.model.Calculation
import com.sosauce.cutecalc.domain.repository.HistoryDao
import com.sosauce.cutecalc.domain.repository.HistoryEvents
import com.sosauce.cutecalc.utils.isErrorMessage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HistoryViewModel(
    private val dao: HistoryDao,
) : ViewModel() {


    private var _allCalculations = MutableStateFlow(emptyList<Calculation>())
    val allCalculations = _allCalculations.asStateFlow()
        .stateIn(
            viewModelScope,
            SharingStarted.Companion.WhileSubscribed(5000),
            emptyList()
        )

    init {

        viewModelScope.launch {
            dao.getAllCalculations().collectLatest { calculations ->
                _allCalculations.update { calculations }
            }
        }

    }

    fun onEvent(event: HistoryEvents) {
        when (event) {
            is HistoryEvents.AddCalculation -> {

                val calculation = Calculation(
                    operation = event.operation,
                    result = event.result
                )

                if (event.saveErrors || !event.result.isErrorMessage()) {
                    viewModelScope.launch {
                        if (allCalculations.value.size.toLong() == event.maxHistoryItems) {
                            dao.deleteCalculation(allCalculations.value.first())
                        }
                        dao.insertCalculation(calculation)
                    }
                } else {
                    return
                }

            }

            is HistoryEvents.DeleteCalculation -> {
                viewModelScope.launch { dao.deleteCalculation(event.calculation) }
            }

            is HistoryEvents.DeleteAllCalculation -> {
                viewModelScope.launch { dao.deleteAllCalculations() }
            }
        }
    }
}