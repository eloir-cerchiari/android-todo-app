package com.eloir.todoapp

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow


class TaskItemRepo(private val taskItemDao: TaskItemDao) {
    val allTaskItems: Flow<List<TaskItem>> = taskItemDao.allTaskItems()

    @WorkerThread
    suspend fun insertTaskItem(taskItem: TaskItem){
        taskItemDao.insert(taskItem)
    }

    @WorkerThread
    suspend fun updateTaskItem(taskItem: TaskItem){
        taskItemDao.update(taskItem)
    }
}