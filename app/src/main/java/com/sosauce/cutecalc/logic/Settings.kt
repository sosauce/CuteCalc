package com.sosauce.cutecalc.logic

import android.content.Context
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
    val THEME = stringPreferencesKey("theme")
    val BUTTON_VIBRATION_ENABLED = booleanPreferencesKey("button_vibration_enabled")
    val CALCULATION_HISTORY = stringPreferencesKey("calculation_history")
}

// Start of the settings for themes
suspend fun saveTheme(dataStore: DataStore<Preferences>, theme: String) {
    dataStore.edit { settings ->
        settings[PreferencesKeys.THEME] = theme
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

