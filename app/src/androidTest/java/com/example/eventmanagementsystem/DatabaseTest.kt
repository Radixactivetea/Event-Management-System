package com.example.eventmanagementsystem

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.eventmanagementsystem.database.AppDatabase
import com.example.eventmanagementsystem.database.Task
import com.example.eventmanagementsystem.database.TaskDao
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DatabaseTest {
    private lateinit var db: AppDatabase
    private lateinit var dao: TaskDao

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        dao = db.taskDao()
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
     fun testTask() {
        val task = Task(1, "Test Task", "2023-08-31")
        dao.upsertTask(task)
    }
}