package com.example.todo.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Todo(
    @PrimaryKey(autoGenerate = true)
    val todoId: Int = 0,
    val title: String,
    val description: String? = null,
    val isDone: Boolean = false
)
