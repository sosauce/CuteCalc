package com.sosauce.cutecalc.logic

import android.content.Context
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

object PreferencesKeys {
    val USE_DARK_MODE = booleanPreferencesKey("use_dark_mode")
    val USE_AMOLED_MODE = booleanPreferencesKey("use_amoled_mode")
    val BUTTON_VIBRATION_ENABLED = booleanPreferencesKey("button_vibration_enabled")
    val DECIMAL_FORMATTING = booleanPreferencesKey("decimal_formatting")
    val CALCULATION_HISTORY = stringPreferencesKey("calculation_history")
}

// Start of the settings for themes
suspend fun saveDarkModeSetting(dataStore: DataStore<Preferences>, enabled: Boolean) {
    dataStore.edit { preferences ->
        preferences[PreferencesKeys.USE_DARK_MODE] = enabled
    }
}

@Composable
fun getDarkModeSetting(dataStore: DataStore<Preferences>): Flow<Boolean> {
    val isSysDark = isSystemInDarkTheme()
    return dataStore.data.map { preferences ->
        preferences[PreferencesKeys.USE_DARK_MODE] ?: isSysDark
    }
}

suspend fun saveAmoledModeSetting(dataStore: DataStore<Preferences>, enabled: Boolean) {
    dataStore.edit { preferences ->
        preferences[PreferencesKeys.USE_AMOLED_MODE] = enabled
    }
}

fun getAmoledModeSetting(dataStore: DataStore<Preferences>): Flow<Boolean> {
    return dataStore.data.map { preferences ->
        preferences[PreferencesKeys.USE_AMOLED_MODE] ?: false
    }
}

// End of the settings for themes

// Start of settings for button vibration

suspend fun saveButtonVibrationSetting(dataStore: DataStore<Preferences>, enabled: Boolean) {
    dataStore.edit { settings ->
        settings[PreferencesKeys.BUTTON_VIBRATION_ENABLED] = enabled
    }
}

fun getButtonVibrationSetting(dataStore: DataStore<Preferences>): Flow<Boolean> {
    return dataStore.data.map { preferences ->
        preferences[PreferencesKeys.BUTTON_VIBRATION_ENABLED] ?: false
    }
}
// End of settings for button vibration

suspend fun saveDecimalFormattingSetting(dataStore: DataStore<Preferences>, enabled: Boolean) {
    dataStore.edit { settings ->
        settings[PreferencesKeys.DECIMAL_FORMATTING] = enabled
    }
}

fun getDecimalFormattingSetting(dataStore: DataStore<Preferences>): Flow<Boolean> {
    return dataStore.data.map { preferences ->
        preferences[PreferencesKeys.DECIMAL_FORMATTING] ?: false
    }
}

