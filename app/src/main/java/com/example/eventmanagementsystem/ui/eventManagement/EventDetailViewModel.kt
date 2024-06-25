package com.example.eventmanagementsystem.ui.eventManagement

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eventmanagementsystem.MainApplication
import com.example.eventmanagementsystem.database.Events
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EventDetailViewModel() : ViewModel() {

    private val eventDao = MainApplication.Database.eventDao()

    fun event (id: Int): LiveData<Events> {
        return eventDao.getEventById(id)
    }

    fun deleteEvent(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            eventDao.deleteEvent(id)
        }
    }
}