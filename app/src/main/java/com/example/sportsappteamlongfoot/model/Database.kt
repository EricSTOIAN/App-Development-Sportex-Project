package com.example.sportsappteamlongfoot.model

import android.content.Context
import android.icu.text.DateFormat
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
        val NAME = stringPreferencesKey("NAME")
        val TYPE = stringPreferencesKey("TYPE")
        val DATE = stringPreferencesKey("DATE")
        val DESCRIPTION = stringPreferencesKey("DESCRIPTION")
    }

    /**
     * Update the user settings in the DataStore.
     */
    suspend fun saveWorkout (workout: Workout) {
        context.dataStore.edit {
            it[NAME] = workout.name
            it[TYPE] = workout.type
            it[DATE] = workout.date
            it[DESCRIPTION] = workout.description
        }
    }

    /**
     * Retrieves the settings data from the Datastore
     */
    fun getWorkouts(): Flow<Workout> = context.dataStore.data.map {
        Workout(
            name = it[NAME] ?: "",
            type = it[TYPE] ?: "",
            date = it[DATE] ?: "",
            description = it[DESCRIPTION] ?: ""
        )
    }

}

class Workout(val name:String, val type: String, val date: String, val description:String)

