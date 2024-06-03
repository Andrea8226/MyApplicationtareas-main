package com.example.myapplicationtareas

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class EditTaskActivity : AppCompatActivity() {

    private lateinit var etTaskTitle: EditText
    private lateinit var etTaskDate: EditText
    private lateinit var btnSave: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_task)

        etTaskTitle = findViewById(R.id.etTaskTitle)
        etTaskDate = findViewById(R.id.etTaskDate)
        btnSave = findViewById(R.id.btnSave)

        val taskTitle = intent.getStringExtra("taskTitle")
        val taskDate = intent.getStringExtra("taskDate")

        etTaskTitle.setText(taskTitle)
        etTaskDate.setText(taskDate)

        etTaskDate.setOnClickListener {
            showDatePicker()
        }

        btnSave.setOnClickListener {
            val updatedTitle = etTaskTitle.text.toString()
            val updatedDate = etTaskDate.text.toString()

            if (updatedTitle.isNotEmpty() && updatedDate.isNotEmpty()) {
                // Save the updated task information (you'll need to implement saving logic)
                Toast.makeText(this, "Task updated", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
            val date = "$selectedDay/${selectedMonth + 1}/$selectedYear"
            etTaskDate.setText(date)
        }, year, month, day)

        datePickerDialog.show()
    }
}
