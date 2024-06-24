package com.example.eventmanagementsystem

import android.app.Application
import androidx.room.Room
import com.example.eventmanagementsystem.database.AppDatabase

class MainApplication : Application() {

    companion object {
        lateinit var Database: AppDatabase
    }

    override fun onCreate() {
        super.onCreate()
        Database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            AppDatabase.NAME
        ).build()
    }
}