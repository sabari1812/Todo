package com.example.todo.todo_list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.todo.util.Routes
import com.example.todo.util.UiEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoListScreen(
    onNavigate:(UiEvent.Navigate)-> Unit,
    viewModel: TodoListViewModel = hiltViewModel()
) {

    val todos = viewModel.todos.collectAsState(emptyList())
    val scaffoldState = rememberBottomSheetScaffoldState()
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event->
            when(event){
                is UiEvent.Navigate -> {
                    onNavigate(event)
                }
                UiEvent.PopBackStack -> TODO()
                is UiEvent.ShowSnackbar -> {
                    val res = scaffoldState.snackbarHostState.showSnackbar(event.message,event.action)
                    if(res == SnackbarResult.ActionPerformed){
                        viewModel.onEvent(TodoListEvent.UndoDeleteClick)
                    }
                }
            }
        }
    }
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.onEvent(TodoListEvent.OnAddTodoClick)
                }
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "")
            }
        }
    ){paddingValues ->
        LazyColumn(
            contentPadding = paddingValues,
            modifier = Modifier
                .fillMaxSize()
        ) {
            items(todos.value){
                TodoItem(
                    todo = it,
                    onEvent = viewModel::onEvent,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable{viewModel.onEvent(TodoListEvent.OnTodoClick(it))}
                        .padding(16.dp)
                )
            }
        }
    }
}