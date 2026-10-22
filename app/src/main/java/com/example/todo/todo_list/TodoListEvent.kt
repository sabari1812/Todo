package com.example.todo.todo_list

import com.example.todo.data.Todo

sealed class TodoListEvent {

    data class DeleteTodo(val todo: Todo): TodoListEvent()
    data class OnDoneChange(val todo: Todo,val isDone: Boolean): TodoListEvent()
    object UndoDeleteClick: TodoListEvent()
    data class OnTodoClick(val todo: Todo): TodoListEvent()
    object OnAddTodoClick: TodoListEvent()
}