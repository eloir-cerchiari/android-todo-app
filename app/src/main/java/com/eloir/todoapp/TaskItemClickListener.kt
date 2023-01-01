package com.eloir.todoapp

interface TaskItemClickListener {
    fun editTaskItem(taskItem: TaskItem)
    fun completeTaskItem(taskItem: TaskItem)
    fun uncompletedTaskItem(taskItem: TaskItem)
}