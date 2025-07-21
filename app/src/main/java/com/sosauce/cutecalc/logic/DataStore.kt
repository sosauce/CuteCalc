package com.sosauce.cutecalc.logic

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.sosauce.cutecalc.utils.CuteTheme


val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

data object PreferencesKeys {
    val THEME = stringPreferencesKey("theme")
    val BUTTON_VIBRATION_ENABLED = booleanPreferencesKey("button_vibration_enabled")
    val DECIMAL_FORMATTING = booleanPreferencesKey("decimal_formatting")
    val ENABLE_HISTORY = booleanPreferencesKey("enable_history")
    val HISTORY_MAX_ITEMS = longPreferencesKey("HISTORY_MAX_ITEMS")
    val SAVE_ERRORS_TO_HISTORY = booleanPreferencesKey("SAVE_ERRORS_TO_HISTORY")
    val SORT_HISTORY_ASC = booleanPreferencesKey("sort_history_asc")
    val USE_BUTTONS_ANIMATIONS = booleanPreferencesKey("use_buttons_animation")
    val USE_SYSTEM_FONT = booleanPreferencesKey("use_system_font")
    val SHOW_CLEAR_BUTTON = booleanPreferencesKey("show_clear_button")
    val SHOW_BACK_BUTTON = booleanPreferencesKey("show_back_button")
}

@Composable
fun rememberVibration() =
    rememberPreference(key = PreferencesKeys.BUTTON_VIBRATION_ENABLED, defaultValue = false)

@Composable
fun rememberAppTheme() =
    rememberPreference(key = PreferencesKeys.THEME, defaultValue = CuteTheme.SYSTEM)

@Composable
fun rememberDecimal() =
    rememberPreference(key = PreferencesKeys.DECIMAL_FORMATTING, defaultValue = false)

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

@Composable
fun rememberHistoryMaxItems() =
    rememberPreference(key = PreferencesKeys.HISTORY_MAX_ITEMS, defaultValue = Long.MAX_VALUE)

@Composable
fun rememberShowBackButton() =
    rememberPreference(key = PreferencesKeys.SHOW_BACK_BUTTON, defaultValue = true)


@Composable
fun rememberSaveErrorsToHistory() =
    rememberPreference(key = PreferencesKeys.SAVE_ERRORS_TO_HISTORY, defaultValue = false)


