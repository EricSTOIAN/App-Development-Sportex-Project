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
import com.example.sportsappteamlongfoot.data.Goal


@SuppressLint("NewApi", "StateFlowValueCalledInComposition")
@Composable
fun PlannerScreen(
    modifier: Modifier = Modifier,
    onNavigateToGoal: () -> Unit,
    onNavigateToWorkout: () -> Unit,
    viewModel: MyViewModelSimpleSaved,
    navController: NavController,
    onWorkoutClick: () -> Unit,
    onGoalDetailsClick: () -> Unit,
) {
    // Observe the state of workouts and goals from the ViewModel
    val workouts = viewModel.getWeeklyWorkouts()
    val goals = viewModel.getWeeklyGoals()
    val upcomingWorkouts = viewModel.getUpcomingWorkouts();
    val upcomingGoals = viewModel.getUpcomingGoals()
    // Calculate the start of the current week
    val weekStart = LocalDate.now().with(WeekFields.of(Locale.getDefault()).dayOfWeek(), DayOfWeek.MONDAY.value.toLong())

    // Merge workouts and goals for each day of the week
    val weeklyData = mutableListOf<Pair<DayOfWeek, List<Pair<Workout?, Goal?>>>>()
    DayOfWeek.values().forEachIndexed { index, dayOfWeek ->
        val dayDate = weekStart.plusDays(index.toLong())
        val dayWorkouts = workouts.filter { LocalDate.parse(it.date).dayOfWeek == dayOfWeek }
        val dayGoals = goals.filter { LocalDate.parse(it.date).dayOfWeek == dayOfWeek }
        val combinedData = dayWorkouts.map { Pair(it, null) } + dayGoals.map { Pair(null, it) }
        weeklyData.add(Pair(dayOfWeek, combinedData))
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 70.dp) // Reserve space for BottomBar
        ) {
            // Title
            Text(
                text = "Planner",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Spacer(modifier = Modifier.height(50.dp))

            // Display Week Start
            Text(
                text = "Week of ${weekStart.month} ${weekStart.dayOfMonth}",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Vertical layout for Workouts and Goals
            Column {
                // Weekly Data Section (both workouts and goals)
                LazyRow(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(weeklyData) { (dayOfWeek, dayData) ->
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

                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(80.dp),
                                shape = RoundedCornerShape(8.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = if (dayData.isNotEmpty()) MaterialTheme.colorScheme.primary else Color.Gray
                                )
                            ) {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center,
                                    modifier = Modifier.padding(8.dp)
                                ) {
                                    if (dayData.isEmpty()) {
                                        Text(
                                            text = "free day!",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = Color.White
                                        )
                                    } else {
                                        dayData.forEach { (workout, goal) ->
                                            workout?.let {
                                                Text(
                                                    text = it.name,
                                                    style = MaterialTheme.typography.bodySmall,
                                                    color = Color.White
                                                )
                                            }
                                            goal?.let {
                                                Text(
                                                    text = it.name,
                                                    style = MaterialTheme.typography.bodySmall,
                                                    color = Color.White
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            Button(
                    onClick = onNavigateToWorkout,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
            ) {
            Text(text = "Add Workout")
        }
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = onNavigateToGoal,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Text(text = "Add Goal")
            }
            Spacer(modifier = Modifier.height(8.dp))

            // Upcoming Workouts Section
            Column(
                modifier = Modifier.padding(vertical = 16.dp)
            ) {
                Text(
                    text = "Upcoming Workouts",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                LazyRow(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(upcomingWorkouts) { workout ->
                        Card(
                            modifier = Modifier
                                .padding(horizontal = 8.dp)
                                .size(width = 200.dp, height = 100.dp),
                            shape = RoundedCornerShape(8.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.secondary
                            )
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(16.dp),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.Start
                            ) {
                                Text(
                                    text = workout.name,
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = Color.White
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "Date: ${workout.date}",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color.White
                                )
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = onWorkoutClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    Text(text = "View More Workouts")
                }
            }

            //Spacer(modifier = Modifier.height(8.dp))

            // Upcoming Goals Section
            Column(
                modifier = Modifier.padding(vertical = 16.dp)
            ) {
                Text(
                    text = "Upcoming Goals",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                LazyRow(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(upcomingGoals) { goal ->
                        Card(
                            modifier = Modifier
                                .padding(horizontal = 8.dp)
                                .size(width = 200.dp, height = 100.dp),
                            shape = RoundedCornerShape(8.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.secondary
                            )
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(16.dp),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.Start
                            ) {
                                Text(
                                    text = goal.name,
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = Color.White
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "Due: ${goal.date}",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color.White
                                )
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick =onGoalDetailsClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    Text(text = "View More Goals")
                }
            }
        }

        // Bottom Navigation Bar
        BottomBar(navController = navController, modifier = Modifier.align(Alignment.BottomCenter))
    }
}





