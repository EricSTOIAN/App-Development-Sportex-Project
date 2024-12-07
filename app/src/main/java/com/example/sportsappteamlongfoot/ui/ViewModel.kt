package com.example.sportsappteamlongfoot.ui

import android.content.Context
import androidx.lifecycle.VIEW_MODEL_STORE_OWNER_KEY
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sportsappteamlongfoot.data.DataStoreManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

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


    /* Method called when ViewModel is first created */
    init {
        viewModelScope.launch {
            dataStoreManager.firstNameFlow.collect { _firstName.value = it }
            dataStoreManager.lastNameFlow.collect { _lastName.value = it }
            dataStoreManager.ageFlow.collect { _age.value = it }

        }
        // Start collecting the data from the data store when the ViewModel is created.
        loadSettings()
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

}