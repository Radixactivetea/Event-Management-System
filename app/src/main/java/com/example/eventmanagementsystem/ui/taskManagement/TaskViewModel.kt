package com.example.eventmanagementsystem.ui.taskManagement

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eventmanagementsystem.MainApplication
import com.example.eventmanagementsystem.database.Task
import com.example.eventmanagementsystem.database.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TaskViewModel() : ViewModel() {

    val taskDao = MainApplication.Database.taskDao()
    val tasksList: LiveData<List<Task>> = taskDao.getAllTasks()


    //val taskspec: User? = taskDao.getTaskById()

    fun deleteTask(taskId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            taskDao.deleteTask(taskId)
        }
    }

    fun updateTask(taskId: Int, isCompleted: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            taskDao.updateTaskCompletion(taskId, completed = isCompleted)
        }
    }
}