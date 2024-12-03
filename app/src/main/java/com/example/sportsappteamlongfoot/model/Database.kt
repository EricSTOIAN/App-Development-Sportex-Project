package com.example.sportsappteamlongfoot.model

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

const val WORKOUT_DATASTORE = "settings_datastore"
private val Context.dataStore by preferencesDataStore(name = WORKOUT_DATASTORE)

class RepositoryWorkout(private val context: Context){
    //The set of information that will be stored in the DataStore
    //are indicated using "preferencesKeys
    companion object {
        val DESCRIPTION = stringPreferencesKey("DESCRIPTION")
        val WORKOUT = stringPreferencesKey("WORKOUT")
    }

    /**
     * Update the user settings in the DataStore.
     */
    suspend fun saveWorkout (workout: Workout) {
        context.dataStore.edit {
            it[DESCRIPTION] = workout.description
            it[WORKOUT] = workout.workout
        }
    }

    /**
     * Retrieves the settings data from the Datastore
     */
    fun getWorkouts(): Flow<Workout> = context.dataStore.data.map {
        Workout(
            description = it[DESCRIPTION] ?: "",
            workout = it[WORKOUT] ?: ""
        )
    }

}

class Workout(val description: String, val workout: String)

