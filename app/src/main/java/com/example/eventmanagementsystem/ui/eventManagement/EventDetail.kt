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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.eventmanagementsystem.R
import com.example.eventmanagementsystem.ui.navigation.NavigationDestination

object DetailEventDestination: NavigationDestination {
    override val route = "eventDetail"
    override val titleRes = R.string.task
    const val eventIdArg = "eventId"
    val routeWithArgs = "$route/{$eventIdArg}"
}

@Composable
fun DetailEvent(
    eventId: Int,
    //navigateToProfile: () -> Unit = {},
    //navigateToNotifications: () -> Unit = {},
    navigateBack: () -> Unit,
    navigateToTask: (Int) -> Unit,
    viewModel: EventDetailViewModel
) {

    val event by viewModel.event(eventId).observeAsState(null)

    println(event?.title)

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
                IconButton(onClick = { navigateBack() }) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowLeft ,
                        contentDescription = "backToEvent",
                        modifier = Modifier.size(40.dp),
                        tint = Color.White
                    )
                }
            }
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            shape = RoundedCornerShape(20.dp, 20.dp, 0.dp, 0.dp),
            colors = CardDefaults.cardColors(Color.White)
        ) {
            Column (
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box (
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    Text(
                        text = stringResource(R.string.event_Big),
                        fontWeight = FontWeight.Bold,
                        fontSize = 30.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )

                    IconButton(
                        onClick = {
                            viewModel.deleteEvent(eventId)
                            navigateBack()
                            /*delete event (viewmodel) and navigateback*/
                        }) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete Event",
                            modifier = Modifier.size(20.dp),
                            tint = Color(0xFF274D76)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                event?.let {
                    Details("Event Title : ", it.title)
                    Details("Event Venue : ", it.location)
                    Details("Event Description : ", it.description)
                    Details("Time : ", it.time)
                    Details("Date : ", it.date)
                }

                Row (
                    Modifier.padding(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(40.dp)
                ){
                    Button(
                        onClick = { /*Navigate to Vendor*/ },
                        colors = ButtonDefaults.buttonColors(Color(0xFF274D76))) {
                        Text(text = stringResource(R.string.vendor))
                    }

                    Button(
                        onClick = { navigateToTask(eventId)  },
                        colors = ButtonDefaults.buttonColors(Color(0xFF274D76))
                    ) {
                        Text(text = stringResource(id = R.string.task))
                    }
                }
            }
        }
    }
}

@Composable
fun Details(
    title: String,
    details: String
) {
    Row (
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = title,
            fontWeight = FontWeight.Bold,
            fontSize = 15.sp,
            modifier = Modifier.height(40.dp)
        )
        Text(text = details)
    }

}

@Preview
@Composable
fun DetailsPreview(){

}