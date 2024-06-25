package com.example.eventmanagementsystem.ui.taskManagement

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eventmanagementsystem.MainApplication
import com.example.eventmanagementsystem.database.Task
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddTaskViewModel: ViewModel() {

    private val taskDao = MainApplication.Database.taskDao()

    var taskTitle by mutableStateOf("")
        private set
    var taskNote by mutableStateOf("")
        private set
    var taskDueDate by mutableStateOf("")
        private set

    fun onTitleChange(newTitle: String) {
        taskTitle = newTitle
    }

    fun onNoteChange(newNote: String) {
        taskNote = newNote
    }

    fun onDueDateChange(newDueDate: String) {
        taskDueDate = newDueDate
    }

    fun saveTask(eventId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            taskDao.upsertTask(Task(eventId = eventId, title = taskTitle, note= taskNote, dueDate = taskDueDate))
        }
    }
}