package com.sosauce.cutecalc

import com.notkamui.keval.keval

data class CalcState(val field: String)
sealed interface CalcAction {
    object GetResult : CalcAction
    object ResetField: CalcAction
    object RemoveLast: CalcAction
    data class AddToField(val value: String) : CalcAction
}

class GetFormulaResultUseCase {
    operator fun invoke(formula: String) : String {
        return formula.keval().toBigDecimal().stripTrailingZeros().toPlainString()
    }
}
