package com.example.sportsappteamlongfoot.ui.screens

import android.annotation.SuppressLint
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.navigation.NavController
import com.example.sportsappteamlongfoot.data.Workout
import com.example.sportsappteamlongfoot.ui.BottomBar
import com.example.sportsappteamlongfoot.ui.MyViewModelSimpleSaved
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.WeekFields
import java.util.Locale
import androidx.compose.foundation.layout.*


@SuppressLint("NewApi", "StateFlowValueCalledInComposition")
@Composable
fun PlannerScreen(modifier: Modifier = Modifier, viewModel: MyViewModelSimpleSaved, navController: NavController) {
    // Observe the state of workouts from the ViewModel
    val workouts = viewModel.getWeeklyWorkouts()

    // Calculate the start of the current week
    val weekStart = LocalDate.now().with(WeekFields.of(Locale.getDefault()).dayOfWeek(), DayOfWeek.MONDAY.value.toLong())

    // Prepare data to be displayed
    val weeklyWorkouts = mutableListOf<Pair<DayOfWeek, List<Workout>>>()
    DayOfWeek.values().forEachIndexed { index, dayOfWeek ->
        val dayDate = weekStart.plusDays(index.toLong())
        val dayWorkouts = workouts.filter { LocalDate.parse(it.date).dayOfWeek == dayOfWeek }
        weeklyWorkouts.add(Pair(dayOfWeek, dayWorkouts))
    }

    Box(modifier = Modifier.padding(16.dp)) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Display the starting date of the week at the top
            Text(
                text = "Planner",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Spacer(modifier = Modifier.height(8.dp)) // Add spacing between titles

            Text(
                text = "Week of ${weekStart.month} ${weekStart.dayOfMonth}",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            LazyRow(
                modifier = Modifier.fillMaxSize()
            ) {
                items(weeklyWorkouts) { (dayOfWeek, dayWorkouts) ->
                    // Display the day of the week above the card
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .width(100.dp)
                            .padding(horizontal = 8.dp)
                    ) {
                        Text(
                            text = dayOfWeek.toString().substring(0, 3),
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Black,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )

                        // Display the workouts inside cards, change background color based on the presence of workouts
                        Card(
                            modifier = Modifier
                                //.background(if (dayWorkouts.isNotEmpty()) MaterialTheme.colorScheme.primary else Color.Gray)
                                .fillMaxWidth()
                                .height(120.dp), // Adjust height as necessary
                            shape = RoundedCornerShape(8.dp),
                                    colors = CardDefaults.cardColors(
                                    containerColor = if (dayWorkouts.isNotEmpty()) MaterialTheme.colorScheme.primary else Color.Gray
                                    )

                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center,
                                modifier = Modifier.padding(8.dp)
                            ) {
                                if (dayWorkouts.isEmpty()) {
                                    Text(
                                        text = "No workouts",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = Color.White
                                    )
                                } else {
                                    dayWorkouts.forEach { workout ->
                                        Text(
                                            text = workout.name,
                                            style = MaterialTheme.typography.bodySmall,
                                            color = Color.White
                                        )
                                    }
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.width(16.dp)) // Add spacing between days
                }
            }
        }

        // Bottom bar added here
        BottomBar(navController = navController, Modifier.align(Alignment.BottomEnd))
    }
}

@Preview(showBackground = true)
@Composable
fun PlannerScreenPreview() {
    PlannerScreen(
        modifier = TODO(),
        viewModel = TODO(),
        navController = TODO()
    )
}


