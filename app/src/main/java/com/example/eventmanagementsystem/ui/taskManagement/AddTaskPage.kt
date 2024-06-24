package com.example.eventmanagementsystem.ui.taskManagement

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.eventmanagementsystem.EventManagementTopAppBar
import com.example.eventmanagementsystem.R
import com.example.eventmanagementsystem.ui.navigation.NavigationDestination

object AddTaskDestination : NavigationDestination {
    override val route = "addTask"
    override val titleRes = R.string.add_new_task
}

@Composable
fun AddTaskPage(
    navigateBack: () -> Unit,
    onNavigateUp: () -> Unit,
    canNavigateBack: Boolean = true,
    viewModel: AddTaskViewModel
) {
    Scaffold(
        topBar = {
            EventManagementTopAppBar(
                title = stringResource(AddTaskDestination.titleRes),
                canNavigateBack = canNavigateBack,
                navigateUp = navigateBack
            )
        }
    ) {innerPadding ->
        AddTaskBody(
            viewModel = viewModel,
            onNavigateUp = onNavigateUp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding)
        )
    }
}

@Composable
fun AddTaskBody(
    viewModel: AddTaskViewModel,
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier
){
    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        OutlinedTextField(
            value = viewModel.taskTitle,
            onValueChange = viewModel::onTitleChange,
            label = { Text(text = "Task Name") },
            modifier = Modifier.fillMaxWidth(0.9f)
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = viewModel.taskNote,
            onValueChange = viewModel::onNoteChange,
            label = { Text(text = "Note") },
            singleLine = false,
            modifier = Modifier.fillMaxWidth(0.9f)
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = viewModel.taskDueDate,
            onValueChange = viewModel::onDueDateChange,
            label = { Text(text = "Due Date") },
            modifier = Modifier.fillMaxWidth(0.9f)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            viewModel.saveTask()
            onNavigateUp()
        },
            shape = MaterialTheme.shapes.small,
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF274D76)),
            modifier = Modifier
                .fillMaxWidth(0.9f)) {
            Text("Add")
        }
    }
}