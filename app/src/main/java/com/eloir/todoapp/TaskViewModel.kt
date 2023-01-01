package com.eloir.todoapp

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import java.time.LocalDate

class TaskViewModel(private val repository: TaskItemRepo): ViewModel() {
    var taskItems:LiveData<List<TaskItem>> = repository.allTaskItems.asLiveData( )

    fun addTaskItem(newTaskItem: TaskItem) = viewModelScope.launch {
        repository.insertTaskItem(newTaskItem)
    }


    fun updateTaskItem(taskItem: TaskItem) = viewModelScope.launch {
        repository.updateTaskItem(taskItem)

    }

    fun setCompleted(taskItem: TaskItem) = viewModelScope.launch {
        if(!taskItem.isCompleted())
            taskItem.completeDateString = TaskItem.dateFormatter.format(LocalDate.now())
        repository.updateTaskItem(taskItem)
    }

    fun resetCompleted(taskItem: TaskItem) = viewModelScope.launch {
        if(taskItem.isCompleted())
            taskItem.completeDateString = null
        repository.updateTaskItem(taskItem)
    }

}

class TaskItemModelFactory(private val repository: TaskItemRepo):ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TaskViewModel::class.java)) {
            return TaskViewModel(repository) as T
        }

        throw java.lang.IllegalArgumentException("Unknown Class for View Model")
    }
}