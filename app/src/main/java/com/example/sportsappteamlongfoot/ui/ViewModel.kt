package com.example.sportsappteamlongfoot.ui

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.VIEW_MODEL_STORE_OWNER_KEY
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sportsappteamlongfoot.data.DataStoreManager
import com.example.sportsappteamlongfoot.data.RepositoryWorkout
import com.example.sportsappteamlongfoot.data.Workout
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
class MyViewModelSimpleSaved(private val context: Context) : ViewModel() {
    // private UI state (MutableStateFlow)
    private val dataStoreManager = DataStoreManager(context)
    private val repositoryWorkout = RepositoryWorkout(context)
    private val _firstName = MutableStateFlow("")
    val firstName: StateFlow<String> = _firstName

    private val _lastName = MutableStateFlow("")
    val lastName: StateFlow<String> = _lastName

    private val _age = MutableStateFlow("")
    val age: StateFlow<String> = _age

    private val _username = MutableStateFlow("")
    val username: StateFlow<String> = _username

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password

    private val _height = MutableStateFlow("")
    val height: StateFlow<String> = _height

    private val _weight = MutableStateFlow("")
    val weight: StateFlow<String> = _weight
    /* Method called when ViewModel is first created */
    init {
        loadSettings()
        //()
        loadTestWorkouts()  // Ensure workouts are loaded when the ViewModel is initialized
        viewModelScope.launch {
            dataStoreManager.firstNameFlow.collect { _firstName.value = it }
            dataStoreManager.lastNameFlow.collect { _lastName.value = it }
            dataStoreManager.ageFlow.collect { _age.value = it }
        }


    }

    fun saveFirstName(firstName: String) {
        _firstName.value = firstName
        viewModelScope.launch {
            dataStoreManager.saveFirstName(firstName)
        }
    }

    fun saveLastName(lastName: String) {
        _lastName.value = lastName
        viewModelScope.launch {
            dataStoreManager.saveLastName(lastName)
        }
    }
    fun saveAge(age: String) {
        _age.value = age
        viewModelScope.launch {
            dataStoreManager.saveAge(age)
        }
    }
    fun saveHeight(height: String) {
        _height.value = height
        viewModelScope.launch {
            dataStoreManager.saveHeight(height)
        }
    }

    fun saveWeight(weight: String) {
        _weight.value = weight
        viewModelScope.launch {
            dataStoreManager.saveWeight(weight)
        }
    }
    private fun loadSettings() {
        viewModelScope.launch {
            dataStoreManager.usernameFlow.collect { username ->
                _username.value = username
            }
        }
        viewModelScope.launch {
            dataStoreManager.passwordFlow.collect { password ->
                _password.value = password
            }
        }
        viewModelScope.launch {
            dataStoreManager.heightFlow.collect { height ->
                _height.value = height
            }
        }
        viewModelScope.launch {
            dataStoreManager.weightFlow.collect { weight ->
                _weight.value = weight
            }
        }
    }

    /* saves the email */
    fun saveUsername(username: String) {
        _username.value = username
        viewModelScope.launch {
            dataStoreManager.saveUsername(username)
        }
    }

    fun savePassword(password: String) {
        _password.value = password
        viewModelScope.launch {
            dataStoreManager.savePassword(password)
        }
    }

    fun checkLogin(usernameIn: String, passwordIn: String): Boolean {
        if(usernameIn == _username.value && passwordIn == _password.value){
            return true
        }
        return false
    }

    // Fetch workouts from DataStore
    private val _workouts = MutableStateFlow<List<Workout>>(emptyList())
    val workouts: StateFlow<List<Workout>> = _workouts

    // Fetch today's workout
    @RequiresApi(Build.VERSION_CODES.O)
    fun getTodaysWorkout(): Workout? {
        val today = LocalDate.now().toString()
        return _workouts.value.firstOrNull { it.date == today }
    }

    // Calculate burnt calories for the week (for completed workouts)
    fun getBurntCaloriesThisWeek(): Int {
        return _workouts.value.filter { it.isCompleted }.sumOf { it.burntCalories }
    }

    // Get completed workouts for the week
    fun getWorkoutsCompletedThisWeek(): String {
        val totalWorkouts = _workouts.value.size
        val completedWorkouts = _workouts.value.count { it.isCompleted }
        return "$completedWorkouts/$totalWorkouts"
    }

    // Load workouts data from DataStore
    fun loadWorkouts() {
        viewModelScope.launch {
            repositoryWorkout.getWorkouts().collect { workouts ->
                _workouts.value = workouts
            }
        }
    }

    // Save a new workout (or update existing ones)
    fun saveWorkout(workout: Workout) {
        val updatedWorkouts = _workouts.value.toMutableList().apply {
            add(workout)
        }
        viewModelScope.launch {
            repositoryWorkout.saveWorkouts(updatedWorkouts)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun loadTestWorkouts() {
        val today = LocalDate.now().toString()

        // Workout 1: Completed workout
        val completedWorkout = Workout(
            name = "Completed Workout",
            type = "Cardio",
            date = today,
            description = "Morning cardio workout",
            burntCalories = 250,
            isCompleted = true
        )

        // Workout 2: Not completed workout, set to today's date
        val notCompletedWorkout = Workout(
            name = "Not Completed Workout",
            type = "Strength",
            date = today,
            description = "Strength training workout",
            burntCalories = 250,
            isCompleted = false
        )

        // Load both workouts into the list
        _workouts.value = listOf(completedWorkout, notCompletedWorkout)

        // Optionally, save the workouts to DataStore for persistence
        viewModelScope.launch {
            try {
                repositoryWorkout.saveWorkouts(_workouts.value)
            } catch (e: Exception) {
                // Log or handle the exception accordingly
                e.printStackTrace()
            }
        }
    }
}