package com.example.eventmanagementsystem.ui.eventManagement

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eventmanagementsystem.MainApplication
import com.example.eventmanagementsystem.database.Events
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EventMainViewModel() : ViewModel() {

    private val eventDao = MainApplication.Database.eventDao()

    val allEvents: LiveData<List<Events>> = eventDao.getAllEvents()

    fun insert(event: Events) = viewModelScope.launch(Dispatchers.IO) {
        eventDao.upsertEvent(event)
    }
}