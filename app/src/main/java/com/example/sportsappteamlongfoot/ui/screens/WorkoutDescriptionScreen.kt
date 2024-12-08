package com.example.sportsappteamlongfoot.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.sportsappteamlongfoot.data.Workout
import com.example.sportsappteamlongfoot.ui.BottomBar
import com.example.sportsappteamlongfoot.ui.MyViewModelSimpleSaved


//For debugging purposes
private val workouts: List<Workout> = listOf(
    Workout("Run 5 km","Running","July 4 2024","First day of running"),
    Workout("Bike 10 km ","Biking","July 15 2024","First day of biking. I want to gradually become better"),
    Workout("Do 20 Push-Ups","Exercises","August 6 2024","I will do 20 pushups for today.")
)

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WorkoutDetailsScreen(
    navController: NavController,
    onProfileClick: () -> Unit,
    viewModel: MyViewModelSimpleSaved,
    modifier: Modifier = Modifier
) {
    val activeWorkout = viewModel.getWorkoutForToday()
    val upcomingWorkouts = viewModel.getUpcomingWorkouts()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            // Screen Header
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Workouts",
                    style = MaterialTheme.typography.headlineLarge,
                    fontSize = 32.sp,
                    color = Color.Black
                )
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "Profile Icon",
                    tint = Color.Gray,
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .clickable { onProfileClick() }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Active Workout Section
            Text(
                text = "Active Workout",
                style = MaterialTheme.typography.headlineSmall,
                fontSize = 24.sp,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(8.dp))
            if (activeWorkout != null) {
                WorkoutCard(workout = activeWorkout, showCompleteButton = true)
            } else {
                Text(
                    text = "No active workout for today!",
                    style = MaterialTheme.typography.bodyMedium,
                    fontSize = 16.sp,
                    color = Color.Gray
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Upcoming Workouts Section
            Text(
                text = "Upcoming Workouts",
                style = MaterialTheme.typography.headlineSmall,
                fontSize = 24.sp,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(8.dp))
            LazyColumn {
                items(upcomingWorkouts) { workout ->
                    WorkoutCard(workout = workout, showCompleteButton = false)
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }

        // Bottom Navigation Bar
        BottomBar(navController = navController, modifier = Modifier.align(Alignment.BottomCenter))
    }
}

@Composable
fun WorkoutCard(workout: Workout, showCompleteButton: Boolean) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .border(BorderStroke(1.dp, Color.LightGray), shape = RoundedCornerShape(8.dp)),
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Workout Name: ${workout.name}",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Black,
                fontSize = 18.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Type: ${workout.type}",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray,
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Description: ${workout.description}",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray,
                fontSize = 16.sp
            )

            if (showCompleteButton) {
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = { /* Mark workout as completed */ },
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text("Complete")
                }
            } else {
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(
                        onClick = { /* Remove workout */ },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                    ) {
                        Text("Remove", color = Color.White)
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = { /* Start workout */ }
                    ) {
                        Text("Start")
                    }
                }
            }
        }
    }
}

//@Preview
//@Composable
//fun WorkoutDetailsPreview(){
//    WorkoutDetailsScreen(Modifier.fillMaxSize())
//}

