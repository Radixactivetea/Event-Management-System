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
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Card
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
    eventId: Int = 0,
    //navigateToProfile: () -> Unit = {},
    //navigateToNotifications: () -> Unit = {},
    navigateBack: () -> Unit,
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
            shape = RoundedCornerShape(20.dp, 20.dp, 0.dp, 0.dp)
        ) {
            Column (
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.event_Big),
                    fontWeight = FontWeight.Bold,
                    fontSize = 30.sp,
                )

                Spacer(modifier = Modifier.height(16.dp))

/*                event?.let {
                    Details("Event Title : ", it.title)
                    Details("Event Venue : ", it.location)
                    Details("Event Description : ", it.description)
                    Details("Time : ", it.time)
                    Details("Date : ", it.date)
                }*/
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