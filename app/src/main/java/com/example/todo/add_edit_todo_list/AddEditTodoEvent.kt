package com.example.todo.add_edit_todo_list

sealed class AddEditTodoEvent {

    data class OnTitleChange(val title: String): AddEditTodoEvent()
    data class OnDescriptionChange(val description: String): AddEditTodoEvent()
    object SaveTodoClick: AddEditTodoEvent()
}