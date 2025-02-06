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
    val SORT_HISTORY_ASC = booleanPreferencesKey("sort_history_asc")
    val USE_BUTTONS_ANIMATIONS = booleanPreferencesKey("use_buttons_animation")
    val USE_SYSTEM_FONT = booleanPreferencesKey("use_system_font")
    val SHOW_CLEAR_BUTTON = booleanPreferencesKey("show_clear_button")
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

@Composable
fun rememberSortHistoryASC() =
    rememberPreference(key = PreferencesKeys.SORT_HISTORY_ASC, defaultValue = true)

@Composable
fun rememberUseButtonsAnimation() =
    rememberPreference(key = PreferencesKeys.USE_BUTTONS_ANIMATIONS, defaultValue = true)

@Composable
fun rememberUseSystemFont() =
    rememberPreference(key = PreferencesKeys.USE_SYSTEM_FONT, defaultValue = false)

@Composable
fun rememberShowClearButton() =
    rememberPreference(key = PreferencesKeys.SHOW_CLEAR_BUTTON, defaultValue = true)

