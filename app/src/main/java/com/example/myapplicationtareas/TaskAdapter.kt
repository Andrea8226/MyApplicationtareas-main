package com.example.myapplicationtareas

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TaskAdapter(
    private val taskList: MutableList<Task>,
    private val context: Context,
    private val onEditTask: (Task) -> Unit,
    private val onDeleteTask: (Task) -> Unit
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = taskList[position]
        holder.tvTaskTitle.text = task.title
        holder.tvTaskDate.text = task.date
        holder.tvTaskDescription.text = task.description
        holder.ivTaskIcon.setImageResource(task.iconResId)

        holder.ivEditTask.setOnClickListener {
            onEditTask(task)
        }

        holder.ivDeleteTask.setOnClickListener {
            onDeleteTask(task)
        }
    }

    override fun getItemCount(): Int {
        return taskList.size
    }

    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTaskTitle: TextView = itemView.findViewById(R.id.tvTaskTitle)
        val tvTaskDate: TextView = itemView.findViewById(R.id.tvTaskDate)
        val tvTaskDescription: TextView = itemView.findViewById(R.id.tvTaskDescription)
        val ivTaskIcon: ImageView = itemView.findViewById(R.id.ivTaskIcon)
        val ivEditTask: ImageView = itemView.findViewById(R.id.ivEditTask)
        val ivDeleteTask: ImageView = itemView.findViewById(R.id.ivDeleteTask)
    }
}






