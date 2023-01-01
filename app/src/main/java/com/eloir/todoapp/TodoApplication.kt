package com.eloir.todoapp

import android.app.Application

class TodoApplication: Application() {

    private val database by lazy {
        TaskItemDatabase.getDatabase(this)
    }

    val repository by lazy {
        TaskItemRepo(database.taskItemDao())
    }
}