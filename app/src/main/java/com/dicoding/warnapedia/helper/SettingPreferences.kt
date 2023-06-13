package com.dicoding.warnapedia.helper

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingPreferences private constructor(private val dataStore: DataStore<Preferences>) {

    private val isFirstAppOpened = booleanPreferencesKey("is_first_app_opened")
    private val isColorBlind = intPreferencesKey("is_color_blind")

    fun getIsColorBlind(): Flow<Int> {
        return dataStore.data.map { preferences ->
            preferences[isColorBlind] ?: 0
        }
    }

    suspend fun setIsColorBlind(number: Int) {
        dataStore.edit { preferences ->
            preferences[isColorBlind] = number
        }
    }
    fun getIsFirstOpened(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[isFirstAppOpened] ?: true
        }
    }

    suspend fun setIsFirstOpened(boolean: Boolean) {
        dataStore.edit { preferences ->
            preferences[isFirstAppOpened] = boolean
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: SettingPreferences? = null

        fun getInstance(dataStore: DataStore<Preferences>): SettingPreferences {
            return INSTANCE ?: synchronized(this) {
                val instance = SettingPreferences(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}