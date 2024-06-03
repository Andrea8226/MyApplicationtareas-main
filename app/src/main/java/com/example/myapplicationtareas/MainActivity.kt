package com.example.myapplicationtareas

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot

class MainActivity : AppCompatActivity() {

    private lateinit var taskAdapter: TaskAdapter
    private val taskList = mutableListOf<Task>()
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize Firebase
        firestore = FirebaseFirestore.getInstance()

        // Set up RecyclerView
        val recyclerView: RecyclerView = findViewById(R.id.recyclerViewTasks)
        recyclerView.layoutManager = LinearLayoutManager(this)
        taskAdapter = TaskAdapter(taskList, this,
            onEditTask = { task -> showEditTaskDialog(task) },
            onDeleteTask = { task -> deleteTask(task) }
        )
        recyclerView.adapter = taskAdapter

        // Load tasks from Firestore
        loadTasksFromFirestore()

        // Floating action button to add new task
        val fabAddTask: FloatingActionButton = findViewById(R.id.fabAddTask)
        fabAddTask.setOnClickListener {
            val dialog = AddTaskDialogFragment { newTask ->
                taskList.add(newTask)
                taskAdapter.notifyItemInserted(taskList.size - 1)
                saveTaskToFirestore(newTask)
            }
            dialog.show(supportFragmentManager, "AddTaskDialogFragment")
        }

        val btnLogout: Button = findViewById(R.id.btnLogout)
        btnLogout.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }

    private fun loadTasksFromFirestore() {
        firestore.collection("tasks")
            .get()
            .addOnSuccessListener { result ->
                taskList.clear()
                for (document: QueryDocumentSnapshot in result) {
                    val task = document.toObject(Task::class.java)
                    taskList.add(task)
                }
                taskAdapter.notifyDataSetChanged()
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting tasks.", exception)
            }
    }

    private fun saveTaskToFirestore(task: Task) {
        firestore.collection("tasks")
            .add(task)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "Task added with ID: ${documentReference.id}")
                task.id = documentReference.id // Update the task ID
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding task", e)
            }
    }

    private fun deleteTask(task: Task) {
        val index = taskList.indexOf(task)
        if (index != -1) {
            taskList.removeAt(index)
            taskAdapter.notifyItemRemoved(index)
            taskAdapter.notifyItemRangeChanged(index, taskList.size)
            deleteTaskFromFirestore(task)
        }
    }

    private fun deleteTaskFromFirestore(task: Task) {
        firestore.collection("tasks").document(task.id)
            .delete()
            .addOnSuccessListener {
                Log.d(TAG, "Task successfully deleted!")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error deleting task", e)
            }
    }

    private fun showEditTaskDialog(task: Task) {
        val dialog = EditTaskDialogFragment(task) { updatedTask ->
            val index = taskList.indexOf(task)
            if (index != -1) {
                taskList[index] = updatedTask
                taskAdapter.notifyItemChanged(index)
                updateTaskInFirestore(updatedTask)
            }
        }
        dialog.show(supportFragmentManager, "EditTaskDialogFragment")
    }

    private fun updateTaskInFirestore(task: Task) {
        firestore.collection("tasks").document(task.id)
            .set(task)
            .addOnSuccessListener {
                Log.d(TAG, "Task successfully updated!")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error updating task", e)
            }
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}
