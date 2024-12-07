package com.example.sportsappteamlongfoot.ui

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.VIEW_MODEL_STORE_OWNER_KEY
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sportsappteamlongfoot.data.DataStoreManager
import com.example.sportsappteamlongfoot.data.Workout
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.WeekFields
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
class MyViewModelSimpleSaved(private val context: Context) : ViewModel() {
    // private UI state (MutableStateFlow)
    private val dataStoreManager = DataStoreManager(context)

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

    private val _goals = MutableStateFlow<List<String>>(emptyList())
    val goals: StateFlow<List<String>> = _goals

    private val _workouts = MutableStateFlow<List<Workout>>(emptyList())
    val workouts: StateFlow<List<Workout>> = _workouts

    /* Method called when ViewModel is first created */
    init {
        loadSettings()
        loadDataFromDataStore()
        prepopulateTestWorkouts()
        //()
        // loadTestWorkouts()  // Ensure workouts are loaded when the ViewModel is initialized
        viewModelScope.launch {
            dataStoreManager.firstNameFlow.collect { _firstName.value = it }
            dataStoreManager.lastNameFlow.collect { _lastName.value = it }
            dataStoreManager.ageFlow.collect { _age.value = it }
            dataStoreManager.goalsFlow.collect { _goals.value = it }
            dataStoreManager.workoutsFlow.collect { _workouts.value = it }

        }


    }
    private fun loadDataFromDataStore() {
        viewModelScope.launch {
            dataStoreManager.workoutsFlow.collect { loadedWorkouts ->
                if (loadedWorkouts != _workouts.value) {
                    println("Loaded workouts: $loadedWorkouts")
                    _workouts.value = loadedWorkouts
                }
            }
        }
    }

    private fun prepopulateTestWorkouts() {
        val initialWorkouts = listOf(
            Workout(name="workout1", description = "RUN BRO PLEASE RUN NOWWWWW RUN FOR YOU LIFEEEEE",date = LocalDate.now().toString(), type = "Morning Run", burntCalories = 250),
            Workout(name="workout2", description = "test 2",date = LocalDate.now().plusDays(2).toString(), type = "Evening Yoga", burntCalories = 250, isCompleted = true)
                   , Workout(name="workout3", description = "test 4",date = LocalDate.now().    minusDays(2).toString(), type = "Evening Yoga", burntCalories = 250, isCompleted = true)

        )
        _workouts.value = initialWorkouts
        saveWorkouts(initialWorkouts)
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
        if (usernameIn == _username.value && passwordIn == _password.value) {
            return true
        }
        return false
    }


    fun saveGoals(goals: List<String>) {
        viewModelScope.launch {
            dataStoreManager.saveGoals(goals)
        }
    }

    fun saveWorkouts(workouts: List<Workout>) {
        viewModelScope.launch {
            dataStoreManager.saveWorkouts(workouts)
        }
    }

    fun addGoal(goal: String) {
        val updatedGoals = _goals.value.toMutableList().apply { add(goal) }
        _goals.value = updatedGoals
        saveGoals(updatedGoals)
    }

    fun addWorkout(workout: Workout) {
        println("Adding workout: $workout")
        val updatedWorkouts = _workouts.value.toMutableList().apply { add(workout) }
        _workouts.value = updatedWorkouts
        saveWorkouts(updatedWorkouts)
    }

    fun getWorkoutForToday(): Workout? {
       val today = LocalDate.now().toString() // Ensure this matches Workout.date
        println("Today's date: $today")
        return _workouts.value.firstOrNull { it.date == today }
    }

    fun getCaloriesForCurrentWeek(): Int {
        val currentWeekStart = LocalDate.now().with(WeekFields.of(Locale.getDefault()).dayOfWeek(), DayOfWeek.MONDAY.value.toLong())
        return _workouts.value
            .filter { it.isCompleted }
            .filter { LocalDate.parse(it.date).isAfter(currentWeekStart.minusDays(1)) && LocalDate.parse(it.date).isBefore(currentWeekStart.plusWeeks(1)) }
            .sumOf { it.burntCalories }
    }

    fun getWeekWorkoutStats(): Pair<Int, Int> {
        // Current week start
        val currentWeekStart = LocalDate.now().with(WeekFields.of(Locale.getDefault()).dayOfWeek(), DayOfWeek.MONDAY.value.toLong())

        // Total workouts within the current week
        val totalWorkouts = _workouts.value.count {
            it.date != null && LocalDate.parse(it.date).isAfter(currentWeekStart.minusDays(1)) &&
                    LocalDate.parse(it.date).isBefore(currentWeekStart.plusWeeks(1))
        }

        // Completed workouts within the current week
        val completedWorkouts = _workouts.value.count {
            it.date != null && LocalDate.parse(it.date).isAfter(currentWeekStart.minusDays(1)) &&
                    LocalDate.parse(it.date).isBefore(currentWeekStart.plusWeeks(1)) &&
                    it.isCompleted
        }

        println("totalWorkouts: $totalWorkouts, completedWorkouts: $completedWorkouts")
        return Pair(completedWorkouts, totalWorkouts)
    }

    fun getAllWorkouts(): StateFlow<List<Workout>> = _workouts



}