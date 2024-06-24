package com.example.eventmanagementsystem.ui.userAuth

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eventmanagementsystem.MainApplication
import com.example.eventmanagementsystem.database.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterViewModel(): ViewModel() {

    private val userDao = MainApplication.Database.userDao()

    var name by mutableStateOf("")
        private set
    var userName by mutableStateOf("")
        private set
    var email by mutableStateOf("")
        private set
    var password by mutableStateOf("")
        private set

    fun onNameChange(newName: String) {
        name = newName
    }

    fun onUserNameChange(newUserName: String) {
        userName = newUserName
    }

    fun onEmailChange(newEmail: String) {
        email = newEmail
    }

    fun onPasswordChange(newPassword: String) {
        password = newPassword
    }


    fun insertUser() {
        viewModelScope.launch(Dispatchers.IO) {
            userDao.insertUser(User(name = name, username = userName, email = email, password = password))
        }
    }
}