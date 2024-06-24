package com.example.eventmanagementsystem.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Task::class, User::class, Events::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        const val NAME = "EventManagementDatabase"
    }

    abstract fun taskDao(): TaskDao
    abstract fun userDao(): UserDao
    abstract fun eventDao(): EventDao
}