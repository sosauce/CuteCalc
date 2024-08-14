package com.sosauce.cutecalc.logic

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.preferencesDataStore


val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

data object PreferencesKeys {
    val USE_DARK_MODE = booleanPreferencesKey("use_dark_mode")
    val USE_AMOLED_MODE = booleanPreferencesKey("use_amoled_mode")
    val BUTTON_VIBRATION_ENABLED = booleanPreferencesKey("button_vibration_enabled")
    val DECIMAL_FORMATTING = booleanPreferencesKey("decimal_formatting")
    val FOLLOW_SYS = booleanPreferencesKey("follow_sys")
    val ENABLE_HISTORY = booleanPreferencesKey("enable_history")
}

@Composable
fun rememberVibration() =
    rememberPreference(key = PreferencesKeys.BUTTON_VIBRATION_ENABLED, defaultValue = false)

@Composable
fun rememberUseDarkMode() =
    rememberPreference(key = PreferencesKeys.USE_DARK_MODE, defaultValue = false)

@Composable
fun rememberUseAmoledMode() =
    rememberPreference(key = PreferencesKeys.USE_AMOLED_MODE, defaultValue = false)

@Composable
fun rememberDecimal() =
    rememberPreference(key = PreferencesKeys.DECIMAL_FORMATTING, defaultValue = false)

@Composable
fun rememberFollowSys() =
    rememberPreference(key = PreferencesKeys.FOLLOW_SYS, defaultValue = true)

@Composable
fun rememberUseHistory() =
    rememberPreference(key = PreferencesKeys.ENABLE_HISTORY, defaultValue = true)

