package com.example.myapplicationtareas

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import java.util.Calendar

class AddTaskDialogFragment(private val onTaskAdded: (Task) -> Unit) : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.dialog_add_task, container, false)

        val etTaskTitle: EditText = view.findViewById(R.id.etTaskTitle)
        val etTaskDate: EditText = view.findViewById(R.id.etTaskDate)
        val etTaskDescription: EditText = view.findViewById(R.id.etTaskDescription)
        val btnAddTask: Button = view.findViewById(R.id.btnAddTask)

        etTaskDate.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(requireContext(), { _, selectedYear, selectedMonth, selectedDay ->
                val selectedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                etTaskDate.setText(selectedDate)
            }, year, month, day)

            datePickerDialog.show()
        }

        btnAddTask.setOnClickListener {
            val title = etTaskTitle.text.toString()
            val date = etTaskDate.text.toString()
            val description = etTaskDescription.text.toString()

            if (title.isNotEmpty() && date.isNotEmpty() && description.isNotEmpty()) {
                val newTask = Task(title = title, date = date, description = description, iconResId = R.drawable.ic_task_icon)
                onTaskAdded(newTask)
                dismiss()
            } else {
                Toast.makeText(requireContext(), "Por favor, llena todos los campos", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }
}
