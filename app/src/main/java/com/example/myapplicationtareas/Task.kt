package com.example.myapplicationtareas

import java.util.UUID

data class Task(
    var id: String = UUID.randomUUID().toString(),
    val title: String = "",
    val date: String = "",
    val description: String = "",
    val iconResId: Int = R.drawable.ic_task_icon
) {
    // Constructor sin argumentos requerido por Firebase
    constructor() : this("", "", "", "", 0)
}
