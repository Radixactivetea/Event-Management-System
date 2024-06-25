package com.example.eventmanagementsystem.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface EventDao {

    @Query("SELECT * FROM events ORDER BY id DESC")
    fun getAllEvents(): LiveData<List<Events>>

    @Query("SELECT * FROM events WHERE id = :id")
    fun getEventById(id: Int): LiveData<Events>

    @Upsert
    fun upsertEvent(events: Events)

    @Query("DELETE FROM events WHERE id = :id")
    fun deleteEvent(id: Int)
}