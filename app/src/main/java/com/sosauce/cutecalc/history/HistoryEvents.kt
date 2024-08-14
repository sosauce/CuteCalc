package com.sosauce.cutecalc.history

sealed interface HistoryEvents {

    data class DeleteCalculation(val calculation: Calculation) : HistoryEvents
    data class AddCalculation(
        val operation: String,
        val result: String
    ) : HistoryEvents
}