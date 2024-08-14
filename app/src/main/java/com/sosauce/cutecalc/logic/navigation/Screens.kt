package com.sosauce.cutecalc.logic.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Screens {

    @Serializable
    data object Main : Screens()

    @Serializable
    data object Settings : Screens()

    @Serializable
    data object History : Screens()
}