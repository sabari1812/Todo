package com.example.todo.add_edit_todo_list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todo.data.Todo
import com.example.todo.data.TodoRepository
import com.example.todo.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditTodoList @Inject constructor(
    private val repository: TodoRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    var todo by mutableStateOf<Todo?>(null)
        private set
    var title by mutableStateOf("")
    private set
    var description by mutableStateOf("")
    private set
    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        val todoId = savedStateHandle.get<Int>("todoId")!!
        if(todoId!=-1){
            viewModelScope.launch {
                repository.getTodoByID(todoId)?.let {
                    title = it.title
                    description = it.description?:""
                    this@AddEditTodoList.todo = todo
                }
            }
        }
    }
    fun onEvent(event: AddEditTodoEvent){
        when(event){
            is AddEditTodoEvent.OnDescriptionChange -> {
                description = event.description
            }
            is AddEditTodoEvent.OnTitleChange -> {
                title = event.title
            }
            AddEditTodoEvent.SaveTodoClick -> {
                viewModelScope.launch {
                    repository.insertTodo(
                        Todo(title = title, description = description, isDone = todo?.isDone?:false)
                    )
                }
                sendUiEvent(UiEvent.PopBackStack)
            }
        }
    }
    private fun sendUiEvent(event: UiEvent){
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}