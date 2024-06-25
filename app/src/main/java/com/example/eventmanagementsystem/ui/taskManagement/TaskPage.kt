package com.example.eventmanagementsystem.ui.taskManagement

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.eventmanagementsystem.EventManagementTopAppBar
import com.example.eventmanagementsystem.R
import com.example.eventmanagementsystem.database.Task
import com.example.eventmanagementsystem.ui.eventManagement.DetailEventDestination
import com.example.eventmanagementsystem.ui.navigation.NavigationDestination

object TaskPageDestination: NavigationDestination {
    override val route = "task"
    override val titleRes = R.string.task
    const val eventIdArg = "eventId"
    val routeWithArgs = "$route/{$eventIdArg}"
}

@Composable
fun TaskPage(
    navigateToAddTask: (Int) -> Unit,
    navigateBack: () -> Unit,
    eventId: Int,
    viewModel: TaskViewModel,
    modifier: Modifier = Modifier
) {
    val tasks by viewModel.tasksList(eventId).observeAsState(emptyList())

    println(eventId)

    Scaffold(
        topBar = {
            EventManagementTopAppBar(
                title = stringResource(TaskPageDestination.titleRes),
                canNavigateBack = true,
                navigateUp = navigateBack
            )
        },
        floatingActionButton ={
            FloatingActionButton(
                onClick = { navigateToAddTask(eventId) },
                containerColor = Color(0xFF274D76)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add",
                    tint = Color.LightGray
                )
            }
        },
        modifier = Modifier.background(color = Color.White)
    ) { innerPadding ->
        TaskBody(
            tasksList = tasks,
            modifier = modifier
                .fillMaxSize(),
            contentPadding = innerPadding,
            viewModel = viewModel
        )
    }
}

@Composable
fun TaskBody(
    tasksList: List<Task>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    viewModel: TaskViewModel
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.background(Color.White)
    ) {
        if (tasksList.isEmpty()) {
            Column (
                verticalArrangement = Arrangement.Center,
                modifier = modifier
            ) {
                Image(
                    painter = painterResource(R.drawable.error),
                    contentDescription = "error",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(200.dp)
                        .padding(contentPadding)
                )
                Text(
                    text = "NO TASK YET",
                    fontSize = MaterialTheme.typography.titleLarge.fontSize,
                    color = Color.LightGray,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        } else {
            TaskList(
                tasksList = tasksList,
                contentPadding = contentPadding,
                viewModel = viewModel
            )
        }
    }
}

@Composable
fun TaskList(
    tasksList: List<Task>,
    contentPadding: PaddingValues,
    viewModel: TaskViewModel
) {
    tasksList.let {
        LazyColumn(
            contentPadding = contentPadding,
            content ={
                itemsIndexed(it){ _: Int, item : Task ->
                    TaskCard(
                        item = item,
                        viewModel = viewModel
                    )
                }
            }
        )
    }
}

@Composable
fun TaskCard(
    item: Task,
    viewModel: TaskViewModel
) {
    val openMinimalDialog = remember { mutableStateOf(false) }

    when {
        openMinimalDialog.value -> {
            TaskDetailDialog(
                onDismissRequest = { openMinimalDialog.value = false },
                onConfirmation = { openMinimalDialog.value = false },
                viewModel = viewModel,
                item = item
            )
        }
    }

    var isComplete by remember { mutableStateOf(item.completed) }

    Card (
        modifier = Modifier.padding(8.dp),
        colors = CardDefaults.cardColors(Color(0xFFE9EDF1)),) {
        Row {
            Checkbox(
                checked = isComplete,
                onCheckedChange = {
                    isComplete = it
                    viewModel.updateTask(item.id, isComplete)
                },
                modifier = Modifier.align(Alignment.CenterVertically)
            )
            Column (
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth(0.9f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = item.title,
                    textDecoration = if (isComplete) TextDecoration.LineThrough else TextDecoration.None,
                )
                Text(text = item.dueDate)
            }
            IconButton(
                onClick = { openMinimalDialog.value = !openMinimalDialog.value },
                modifier = Modifier.align(Alignment.CenterVertically)
            ) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "update task",
                )
            }
        }
    }
}

@Composable
fun TaskDetailDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    viewModel: TaskViewModel,
    item: Task
) {
    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFD2E4FF))
        ) {
            Column (
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(id = R.string.task),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(16.dp),
                )
                TaskDetail(
                    title = item.title,
                    note = item.note,
                    dueDate = item.dueDate,
                    completed = item.completed
                )
                IconButton(
                    onClick = {
                        onConfirmation()
                        viewModel.deleteTask(taskId = item.id)
                              },
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Edit Task",
                    )
                }
            }
        }
    }
}

@Composable
fun TaskDetail(
    title: String,
    note: String,
    dueDate: String,
    completed: Boolean
){
    Column {
        Row (modifier = Modifier.padding(8.dp)){
            Text(text = "Task        : ")
            Text(text = title)
        }
        Row (modifier = Modifier.padding(8.dp)){
            Text(text = "Note        : ")
            Text(text = note)
        }
        Row (modifier = Modifier.padding(8.dp)){
            Text(text = "Due Date : ")
            Text(text = dueDate)
        }
        Row (modifier = Modifier.padding(8.dp)){
            Text(text = "Status     : ")
            if (completed) {
                Text(text = "Completed")
            } else {
                Text(text = "Not Completed")
            }
        }
    }
}

@Preview
@Composable
fun TaskDetailPreview() {
    TaskDetail(title = "Task 1", note = "Note 1", dueDate = "21/05/2023", completed = false)
}