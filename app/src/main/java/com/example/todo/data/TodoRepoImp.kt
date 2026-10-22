package com.example.todo.data

import kotlinx.coroutines.flow.Flow

class TodoRepoImp(
    private val dao: TodoDao
): TodoRepository {
    override suspend fun insertTodo(todo: Todo) {
        dao.insertTodo(todo)
    }

    override suspend fun deleteTodo(todo: Todo) {
        dao.deleteTodo(todo)
    }

    override fun getAllTodos(): Flow<List<Todo>> {
        return dao.getAllTodos()
    }

    override suspend fun getTodoByID(id: Int): Todo? {
       return dao.getTodoByID(id)
    }
}