package com.example.sportsappteamlongfoot.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


const val GOALS_DATASTORE ="goals_datastore"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = GOALS_DATASTORE)

class RepositoryGoal(private val context: Context){
    //The set of information that will be stored in the DataStore
    //are indicated using "preferencesKeys

    companion object {
        val NAME = stringPreferencesKey("NAME")
        val TYPE = stringPreferencesKey("TYPE")
        val ENDDATE = stringPreferencesKey("STARTDATE")
        val DESCRIPTION = stringPreferencesKey("DESCRIPTION")
    }

    /**
     * Update the user settings in the DataStore.
     */
    suspend fun saveWorkout (goal: Goal) {
        context.dataStore.edit {
            it[NAME] = goal.name
            it[TYPE] = goal.type
            it[ENDDATE] = goal.endDate
            it[DESCRIPTION] = goal.description
        }
    }

    /**
     * Retrieves the settings data from the Datastore
     */
    fun getWorkouts(): Flow<Goal> = context.dataStore.data.map {
        Goal(
            name = it[NAME] ?: "",
            type = it[TYPE] ?: "",
            endDate = it[ENDDATE] ?: "",
            description = it[DESCRIPTION] ?: ""
        )
    }
}

class Goal(val name :String, val type:String, val endDate:String, val description:String)