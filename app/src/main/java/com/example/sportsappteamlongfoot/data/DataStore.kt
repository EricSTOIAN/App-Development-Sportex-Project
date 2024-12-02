package com.example.sportsappteamlongfoot.data

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

const val PROFILE_DATASTORE ="profile_datastore"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = PROFILE_DATASTORE)

class DataStoreManager (private val context: Context){
    companion object {
        val PASSWORD = stringPreferencesKey("PASSWORD")
        val USERNAME = stringPreferencesKey("USERNAME")
        val HEIGHT = stringPreferencesKey("HEIGHT")
        val WEIGHT = stringPreferencesKey("WEIGHT")
        val GOAL = stringPreferencesKey("GOAL")
    }

    suspend fun savePassword(password: String) {
        context.dataStore.edit { preferences ->
            preferences[PASSWORD] = password
        }
    }

    suspend fun saveUsername(username: String) {
        context.dataStore.edit { preferences ->
            preferences[USERNAME] = username
        }
    }

    suspend fun saveHeight(height: String) {
        context.dataStore.edit { preferences ->
            preferences[HEIGHT] = height
        }
    }

    suspend fun saveWeight(weight: String) {
        context.dataStore.edit { preferences ->
            preferences[WEIGHT] = weight
        }
    }

    suspend fun saveGoal(goal: String) {
        context.dataStore.edit { preferences ->
            preferences[GOAL] = goal
        }
    }

    val passwordFlow: Flow<String> = context.dataStore.data
        .map { preferences ->
            preferences[PASSWORD] ?: ""
        }

    val usernameFlow: Flow<String> = context.dataStore.data
        .map { preferences ->
            preferences[USERNAME] ?: ""
        }

    val heightFlow: Flow<String> = context.dataStore.data
        .map { preferences ->
            preferences[HEIGHT] ?: ""
        }

    val weightFlow: Flow<String> = context.dataStore.data
        .map { preferences ->
            preferences[WEIGHT] ?: ""
        }

    val goalFlow: Flow<String> = context.dataStore.data
        .map { preferences ->
            preferences[GOAL] ?: ""
        }
}