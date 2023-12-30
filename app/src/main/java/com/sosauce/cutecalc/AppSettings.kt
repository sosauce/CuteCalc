package com.sosauce.cutecalc

import kotlinx.serialization.Serializable


@Serializable
data class AppSettings(
    val userTheme: UserTheme = UserTheme.SYSTEM_DEFAULT
)

enum class UserTheme {
    DARK, LIGHT, SYSTEM_DEFAULT
}