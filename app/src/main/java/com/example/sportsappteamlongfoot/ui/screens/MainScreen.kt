package com.example.sportsappteamlongfoot.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainScreen(navController: NavController, modifier: Modifier = Modifier, onProfileClick: () -> Unit, viewModel: MyViewModelSimpleSaved) {
    val workouts by viewModel.workouts.collectAsState()
    val todaysWorkout = viewModel.getTodaysWorkout()
    val caloriesBurnt = viewModel.getBurntCaloriesThisWeek()
    val workoutsCompleted = viewModel.getWorkoutsCompletedThisWeek()

    println("Workouts: $workouts")
    println("Today's Workout: $todaysWorkout")
    println("Calories Burnt This Week: $caloriesBurnt")
    println("Workouts Completed This Week: $workoutsCompleted")

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 70.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Welcome, User",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onBackground
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

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Week Summary",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Pass dynamic values here for CaloriesCard
                CaloriesCard(title = "Burnt Calories", value = "$caloriesBurnt kcal", modifier = Modifier.weight(1f))

                // Pass dynamic values here for WorkoutsCard
                WorkoutsCard(completed = workoutsCompleted, total = workouts.size, modifier = Modifier.weight(1f))
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Today's Plan",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Show today's workout or "Rest Day"
            if (todaysWorkout != null) {
                LargeEmptyCard(modifier = Modifier) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(todaysWorkout.name, style = MaterialTheme.typography.bodyLarge)
                        Text(todaysWorkout.description, style = MaterialTheme.typography.bodyMedium)
                    }
                }
            } else {
                LargeEmptyCard(modifier = Modifier) {
                    Text(
                        text = "Rest Day",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }

        BottomBar(navController = navController, modifier = Modifier.align(Alignment.BottomCenter))
    }
}

@Composable
fun CaloriesCard(title: String, value: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .padding(horizontal = 4.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Optional image or icon can be added here for Calories
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = value,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary,
                fontSize = 20.sp
            )
        }
    }
}

@Composable
fun WorkoutsCard(completed: String, total: Int, modifier: Modifier = Modifier) {
    val progress = if (total > 0) completed.toFloat() / total else 0f

    Card(
        modifier = modifier
            .padding(horizontal = 4.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Workouts Done",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(16.dp))
            CircularProgressBar(progress = progress) // Use dynamic progress
        }
    }
}

@Composable
fun CircularProgressBar(
    progress: Float,
    size: Int = 100,
    strokeWidth: Float = 8f,
    color: Color = MaterialTheme.colorScheme.primary
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.size(size.dp)
    ) {
        Canvas(modifier = Modifier.size(size.dp)) {
            drawCircle(
                color = Color.LightGray.copy(alpha = 0.3f), // Background circle
                style = Stroke(width = strokeWidth)
            )
            drawArc(
                color = color,
                startAngle = -90f,
                sweepAngle = 360 * progress,
                useCenter = false,
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
            )
        }
        Text(
            text = "${(progress * 100).toInt()}%", // Display progress percentage
            style = MaterialTheme.typography.bodyLarge.copy(fontSize = 16.sp),
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun LargeEmptyCard(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(120.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            content() // This allows dynamic content to be injected into the card.
        }
    }
}
//@Preview(showBackground = true)
//@Composable
//fun MainScreenPreview() {
//    MainScreen()
//}
