package com.eloir.todoapp

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.Editable

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.eloir.todoapp.databinding.FragmentNewTaskSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.time.LocalDate
import java.time.LocalTime

class NewTaskSheet(var taskItem: TaskItem?) : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentNewTaskSheetBinding
    private lateinit var taskViewModel: TaskViewModel
    private var dueTime: LocalTime? = null
    private var dueDate: LocalDate? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = requireActivity()

        binding.taskTitle.text = if (taskItem != null) "Edit Task" else "New Task"
        if (taskItem != null) {
            val editable = Editable.Factory.getInstance()
            binding.name.text = editable.newEditable(taskItem!!.name)
            binding.desc.text = editable.newEditable(taskItem!!.desc)
            if (taskItem!!.dueTime() != null && taskItem!!.dueDate() != null) {
                dueDate = taskItem!!.dueDate()!!
                dueTime = taskItem!!.dueTime()!!
                updateDateButtonText()
            }
        }
        taskViewModel = ViewModelProvider(activity).get(TaskViewModel::class.java)
        binding.saveButton.setOnClickListener {
            saveAction()
        }

        binding.dateTimePickerButton.setOnClickListener {
            openDatePicker()
        }

    }


    private fun openDatePicker() {
        if (dueDate == null)
            dueDate = LocalDate.now()

        val dateSetListener =
            DatePickerDialog.OnDateSetListener { _, selectedYear, selectedMonth, selectedDayOfMonth ->

                dueDate = LocalDate.of(selectedYear, selectedMonth + 1, selectedDayOfMonth)
                openTimePicker()

            }


        val dialog = activity?.let {
            DatePickerDialog(
                it,
                dateSetListener,
                dueDate!!.year,
                dueDate!!.monthValue - 1,
                dueDate!!.dayOfMonth
            )
        }
        dialog?.setTitle("Task Due")
        dialog?.show()

    }

    private fun openTimePicker() {
        if (dueTime == null)
            dueTime = LocalTime.now()

        val listener = TimePickerDialog.OnTimeSetListener { _, selectedHour, selectedMinute ->
            dueTime = LocalTime.of(selectedHour, selectedMinute)
            updateDateButtonText()
        }

        val dialog = TimePickerDialog(activity, listener, dueTime!!.hour, dueTime!!.minute, true)
        dialog.setTitle("Task Due")
        dialog.show()
    }


    private fun updateDateButtonText() {
        binding.dateTimePickerButton.text = String.format(
            "%02d/%02d/%04d %02d:%02d",
            dueDate!!.dayOfMonth,
            dueDate!!.monthValue,
            dueDate!!.year,
            dueTime!!.hour,
            dueTime!!.minute
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewTaskSheetBinding.inflate(inflater, container, false)
        return binding.root

    }

    private fun saveAction() {
        val name = binding.name.text.toString()
        val desc = binding.desc.text.toString()
        val dueTimeString = if(dueTime == null) null else TaskItem.timeFormatter.format(dueTime)
        val dueDateString = if(dueDate == null) null else TaskItem.dateFormatter.format(dueDate)


        if (taskItem == null) {
            val newTask = TaskItem(name, desc, dueTimeString, dueDateString, null)
            taskViewModel.addTaskItem(newTask)
        } else {
            taskItem!!.name = name
            taskItem!!.desc = desc
            taskItem!!.dueDateString = dueDateString
            taskItem!!.dueTimeString = dueTimeString
            taskViewModel.updateTaskItem(taskItem!!)
        }
        binding.name.setText("")
        binding.desc.setText("")
        dismiss()
    }

}