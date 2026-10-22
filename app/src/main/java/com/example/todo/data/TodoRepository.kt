package com.example.todo.data

import androidx.room.Delete
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

interface TodoRepository {

    suspend fun insertTodo(todo: Todo)

    suspend fun deleteTodo(todo: Todo)
    fun getAllTodos(): Flow<List<Todo>>

    suspend fun getTodoByID(id: Int): Todo?
}