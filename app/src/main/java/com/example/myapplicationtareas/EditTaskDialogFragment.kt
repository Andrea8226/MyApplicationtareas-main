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

class EditTaskDialogFragment(
    private val task: Task,
    private val onTaskUpdated: (Task) -> Unit
) : DialogFragment() {

    private lateinit var etTaskTitle: EditText
    private lateinit var etTaskDescription: EditText
    private lateinit var etTaskDate: EditText
    private lateinit var btnUpdateTask: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.dialog_edit_task, container, false)

        etTaskTitle = view.findViewById(R.id.etTaskTitle)
        etTaskDescription = view.findViewById(R.id.etTaskDescription)
        etTaskDate = view.findViewById(R.id.etTaskDate)
        btnUpdateTask = view.findViewById(R.id.btnUpdateTask)

        etTaskTitle.setText(task.title)
        etTaskDescription.setText(task.description)
        etTaskDate.setText(task.date)

        etTaskDate.setOnClickListener {
            showDatePickerDialog()
        }

        btnUpdateTask.setOnClickListener {
            val title = etTaskTitle.text.toString()
            val description = etTaskDescription.text.toString()
            val date = etTaskDate.text.toString()

            if (title.isNotEmpty() && description.isNotEmpty() && date.isNotEmpty()) {
                val updatedTask = task.copy(title = title, description = description, date = date)
                onTaskUpdated(updatedTask)
                dismiss()
            } else {
                Toast.makeText(context, "Por favor completa todos los campos", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(requireContext(), { _, selectedYear, selectedMonth, selectedDay ->
            etTaskDate.setText("$selectedDay/${selectedMonth + 1}/$selectedYear")
        }, year, month, day)

        datePickerDialog.show()
    }
}
