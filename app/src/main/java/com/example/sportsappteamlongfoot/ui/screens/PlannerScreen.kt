package com.example.sportsappteamlongfoot.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset

@Composable
fun PlannerScreen(modifier: Modifier = Modifier) {
      Column(
            modifier = modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp)
            ) {
        // Header with Page Title and Profile Icon
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Planner",  // Page Title
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
            Icon(
                imageVector = Icons.Default.AccountCircle, // Profile icon
                contentDescription = "Profile Icon",
                tint = Color.Gray,
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Weekly Calendar with Time Graph
        WeeklyCalendar(modifier = Modifier.fillMaxWidth())

        Spacer(modifier = Modifier.height(16.dp))

        // Button to add a workout
        AddWorkoutButton(modifier = Modifier.fillMaxWidth())

        Spacer(modifier = Modifier.height(16.dp))

        // Upcoming Workouts Carousel
        UpcomingWorkoutsCarousel(modifier = Modifier.fillMaxWidth())
    }
}

@Composable
fun WeeklyCalendar(modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.padding(horizontal = 4.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Weekdays
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                val weekdays = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")
                weekdays.forEach { day ->
                    Text(
                        text = day,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Center
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Time Graph
            Canvas(modifier = Modifier.fillMaxWidth().height(120.dp)) {
                drawLine(
                    color = Color.Blue,  // Blue line in the center
                    strokeWidth = 4f,
                    start = Offset(0f, size.height / 2),
                    end = Offset(size.width, size.height / 2)
                )
            }
        }
    }
}


@Composable
fun AddWorkoutButton(modifier: Modifier = Modifier) {
    Button(
        onClick = { /* Handle workout addition logic */ },
        modifier = modifier
            .padding(horizontal = 4.dp)
            .size(120.dp, 48.dp)  // Adjust size to make it smaller
            .clip(MaterialTheme.shapes.small)  // You can adjust the shape here
    ) {
        Text(
            text = "Add Workout",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.White
        )
    }
}

@Composable
fun UpcomingWorkoutsCarousel(modifier: Modifier = Modifier) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .padding(8.dp)
            .horizontalScroll(scrollState)
    ) {
        Text(
            text = "Upcoming Workouts",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(8.dp))

        // This is a placeholder for the carousel of upcoming workouts
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            UpcomingWorkoutCard()
            UpcomingWorkoutCard()
            UpcomingWorkoutCard()
        }
    }
}

@Composable
fun UpcomingWorkoutCard(modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .size(150.dp, 100.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Workout",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PlannerScreenPreview() {
    PlannerScreen()
}

