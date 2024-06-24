package com.example.eventmanagementsystem.ui.eventManagement

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.eventmanagementsystem.R
import com.example.eventmanagementsystem.database.Events
import com.example.eventmanagementsystem.ui.navigation.NavigationDestination

object EventMainPageDestination: NavigationDestination {
    override val route = "event"
    override val titleRes = R.string.event
}

@Composable
fun EventMainPage(
    //navigateToProfile: () -> Unit = {},
    //navigateToNotifications: () -> Unit = {},
    navigateToDetailEvent: (Int) -> Unit,
    viewModel: EventMainViewModel
) {
    val allEvents by viewModel.allEvents.observeAsState(emptyList())

    val openAddEventDialog = remember { mutableStateOf(false) }

    when {
        openAddEventDialog.value -> {
            AddEventDialog(
                onDismissRequest = { openAddEventDialog.value = false },
                onConfirmation = { openAddEventDialog.value = false },
                viewModel = viewModel,
            )
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF274D76)),
        verticalArrangement = Arrangement.Top
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(77.dp)
        ) {
            Row(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = 20.dp),
            ) {
                Image( //to profile
                    painter = painterResource(id = R.drawable.baseline_person_24),
                    contentDescription = "Person Icon",
                    modifier = Modifier.size(40.dp)
                )
                Image( //to notification
                    painter = painterResource(id = R.drawable.baseline_notifications_24),
                    contentDescription = "Notifications Icon",
                    modifier = Modifier.size(36.dp)
                )
            }
            Box(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(start = 20.dp)
            ) {
                Image( //will search
                    painter = painterResource(id = R.drawable.baseline_search_24),
                    contentDescription = "Search Icon",
                    modifier = Modifier.size(40.dp)
                )
            }
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            shape = RoundedCornerShape(20.dp, 20.dp, 0.dp, 0.dp)
        ) {

            Scaffold (
                floatingActionButton = {
                    FloatingActionButton(
                        onClick = { openAddEventDialog.value = !openAddEventDialog.value },
                        containerColor = Color(0xFF274D76),
                        shape = RoundedCornerShape(20.dp),
                        modifier = Modifier.fillMaxWidth(0.9f)
                    ){
                        Text(
                            text = stringResource(R.string.create_new_event),
                            color = Color.White
                        )
                    }
                },
                floatingActionButtonPosition = FabPosition.Center
            ) {innerPadding ->
                EventBody(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    eventList = allEvents,
                    navigateToDetailEvent = navigateToDetailEvent
                )
            }
        }
    }
}

@Composable
fun AddEventDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    viewModel: EventMainViewModel,

) {

    var eventTitle by remember { mutableStateOf("") }
    var venue by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var time by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }


    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            colors = CardDefaults.cardColors(Color(0xFFE9EDF1)),
        ) {
            Column (
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "CREATE NEW EVENT",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = eventTitle,
                    onValueChange = { eventTitle = it },
                    label = { Text("EVENT TITLE") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = venue,
                    onValueChange = { venue = it },
                    label = { Text("VENUE") },
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 3
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("EVENT DESCRIPTION") },
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 3
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    OutlinedTextField(
                        value = time,
                        onValueChange = { time = it },
                        label = { Text("TIME") },
                        modifier = Modifier.weight(1f)
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    OutlinedTextField(
                        value = date,
                        onValueChange = { date = it },
                        label = { Text("DATE") },
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Button(
                        onClick = { onDismissRequest() },
                        modifier = Modifier.padding(end = 8.dp),
                        colors = ButtonDefaults.buttonColors(Color.LightGray)
                    ) {
                        Text("Cancel", color = Color(0xFF274D76))
                    }

                    Button(onClick = {
                        onConfirmation()
                        viewModel.insert(Events(
                            title = eventTitle,
                            location = venue,
                            description = description,
                            time = time,
                            date = date
                        ))
                        }, colors = ButtonDefaults.buttonColors(Color(0xFF274D76))) {
                        Text("Create")
                    }
                }
            }
        }
    }
}

@Composable
fun EventBody(
    eventList: List<Events>,
    modifier: Modifier = Modifier,
    navigateToDetailEvent: (Int) -> Unit = {}
) {

    Column (
        modifier = modifier.padding(top = 30.dp, start = 20.dp, end = 20.dp)
    ) {
        if (eventList.isEmpty()){
            Column (
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center
            ){
                Image(
                    painter = painterResource(R.drawable.error),
                    contentDescription = "error",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(200.dp)
                )
                Text(
                    text = stringResource(R.string.no_event),
                    fontSize = MaterialTheme.typography.titleLarge.fontSize,
                    color = Color.LightGray,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }else{
            Text(
                text = stringResource(R.string.event_Big),
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp,
            )
            Spacer(modifier = Modifier.height(16.dp))
            EventList(
                eventList = eventList,
                navigateToDetailEvent = navigateToDetailEvent
            )
        }
    }
}

@Composable
fun EventList(
    eventList: List<Events>,
    navigateToDetailEvent: (Int) -> Unit = {}
) {
    eventList.let {
        LazyColumn(
            content ={
                itemsIndexed(it){ _: Int, item : Events ->
                    EventCard(
                        id = item.id,
                        title = item.title,
                        location = item.location,
                        time = item.time,
                        date = item.date,
                        navigateToDetailEvent = navigateToDetailEvent
                    )
                }
            }
        )
    }
}

@Composable
fun EventCard(
    id: Int,
    title: String,
    location: String,
    time: String,
    date: String,
    navigateToDetailEvent: (Int) -> Unit = {}
){

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, bottom = 8.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(Color(0xFFE9EDF1)),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 23.sp)
                )

                IconButton(onClick = { navigateToDetailEvent(id) }) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = "update task",
                        Modifier.size(20.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            EventDetailColumn("LOCATION", location)

            Spacer(modifier = Modifier.height(10.dp))

            Row ( horizontalArrangement = Arrangement.spacedBy(60.dp)) {
                EventDetailColumn("TIME", time)
                EventDetailColumn("DATE", date)
            }
        }
    }
}

@Composable
fun EventDetailColumn(label: String, value: String) {
    Column {
        Text(text = label, style = TextStyle(color = Color.Gray))
        Text(text = value)
    }
}