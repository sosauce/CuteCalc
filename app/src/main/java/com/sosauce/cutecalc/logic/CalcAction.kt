package com.sosauce.cutecalc.logic

sealed interface CalcAction {
    data object GetResult : CalcAction
    data object ResetField : CalcAction
    data object RemoveLast : CalcAction
    data class AddToField(
        val value: String
    ) : CalcAction
}

