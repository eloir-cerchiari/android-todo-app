package com.eloir.todoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.eloir.todoapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), TaskItemClickListener {


    private lateinit var binding: ActivityMainBinding

    private val taskViewModel: TaskViewModel by  viewModels {
        TaskItemModelFactory((application as TodoApplication).repository)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root )

        binding.newTaskButton.setOnClickListener {
            NewTaskSheet(null).showNow(supportFragmentManager, "mewTaskTag")
        }

    setRecyclerView()

    }

    private fun setRecyclerView() {
        val mainActivity = this
        taskViewModel.taskItems.observe(this){
            binding.todoListRecyclerView.apply {
                layoutManager = LinearLayoutManager(applicationContext)
                adapter = TaskItemAdapter(it, mainActivity)
            }
        }
    }

    override fun editTaskItem(taskItem: TaskItem) {
        NewTaskSheet(taskItem).show(supportFragmentManager, "newTaskTag")
    }

    override fun completeTaskItem(taskItem: TaskItem) {
        taskViewModel.setCompleted(taskItem)
    }

    override fun uncompletedTaskItem(taskItem: TaskItem){
        taskViewModel.resetCompleted(taskItem)
    }
}