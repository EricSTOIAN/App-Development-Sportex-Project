package com.example.sportsappteamlongfoot.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


const val WORKOUT_DATASTORE ="workout_datastore"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = WORKOUT_DATASTORE)

class RepositoryWorkout(private val context: Context) {
    companion object {
        val WORKOUTS = stringPreferencesKey("WORKOUTS")
    }

    // Save multiple workouts
    suspend fun saveWorkouts(workouts: List<Workout>) {
        val workoutsJson = Gson().toJson(workouts)
        context.dataStore.edit {
            it[WORKOUTS] = workoutsJson
        }
    }

    // Retrieve the list of workouts
    fun getWorkouts(): Flow<List<Workout>> = context.dataStore.data
        .map {
            val workoutsJson = it[WORKOUTS] ?: "[]"
            Gson().fromJson(workoutsJson, Array<Workout>::class.java).toList()
        }
}

class Workout(
    val name: String,
    val type: String,
    val date: String,
    val description: String,
    val burntCalories: Int,
    val isCompleted: Boolean
)