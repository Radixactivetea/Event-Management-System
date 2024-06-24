package com.example.eventmanagementsystem.ui.userAuth

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eventmanagementsystem.MainApplication
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel(): ViewModel() {

    private val userDao = MainApplication.Database.userDao()

    fun getUser(context: Context, navigateToTask: ()->Unit, username: String, password: String){
        viewModelScope.launch(Dispatchers.IO) {
            val user = userDao.getUser(username, password)
            withContext(Dispatchers.Main) {
                if (user != null) {
                    println("User found: ${user.name}")
                    navigateToTask()
                } else {
                    println("User not found")
                    Toast.makeText(context, "Invalid credentials", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}