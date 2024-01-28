package com.sosauce.cutecalc.logic

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore


val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

object PreferencesKeys {
    val THEME = stringPreferencesKey("theme")
}

// Start of the settings for themes
enum class Theme {
    Dark, Light, Amoled
}

suspend fun saveTheme(dataStore: DataStore<Preferences>, theme: String) {
    dataStore.edit { settings ->
        settings[PreferencesKeys.THEME] = theme
    }
}
// End of the settings for themes