package com.example.eventmanagementsystem.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface TaskDao {

    @Query("SELECT * FROM tasks WHERE eventId = :eventId")
    fun getAllTasks(eventId: Int): LiveData<List<Task>>

    @Upsert
    fun upsertTask(task: Task)

    @Query("DELETE FROM tasks WHERE id = :taskId")
    fun deleteTask(taskId: Int)

    @Query("SELECT * FROM tasks WHERE id = :taskId")
    fun getTaskById(taskId: Int): LiveData<Task>

    @Query("UPDATE tasks SET completed = :completed WHERE id = :taskId")
    fun updateTaskCompletion(taskId: Int, completed: Boolean)
}