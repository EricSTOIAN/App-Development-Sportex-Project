package com.example.sportsappteamlongfoot.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.sportsappteamlongfoot.ui.BottomBar
import com.example.sportsappteamlongfoot.ui.MyViewModelSimpleSaved

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ProfileScreen(navController: NavController,
                  viewModel: MyViewModelSimpleSaved,
                  onNavigateToWorkout: () -> Unit,
                  onNavigateToGoal: () -> Unit)
{
    val firstName by viewModel.firstName.collectAsState()
    val lastName by viewModel.lastName.collectAsState()
    val age by viewModel.age.collectAsState()
    val weight by viewModel.weight.collectAsState()
    val height by viewModel.height.collectAsState()

    var isEditing by remember { mutableStateOf(false) }
    var newFirstName by remember { mutableStateOf(firstName) }
    var newLastName by remember { mutableStateOf(lastName) }
    var newAge by remember { mutableStateOf(age) }
    var newWeight by remember { mutableStateOf(weight) }
    var newHeight by remember { mutableStateOf(height) }
    var newGoals by remember { mutableStateOf(List(6) { "Goal ${it + 1}" }) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp) // Padding around the content
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp)
        ) {
            // Header Section
            Row(

                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Profile",
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "Profile Icon",
                    tint = Color.Gray,
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                )
            }

            // Information Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),

                shape = RoundedCornerShape(8.dp),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    if (isEditing) {
                        OutlinedTextField(
                            value = newFirstName,
                            onValueChange = { newFirstName = it },
                            label = { Text("First Name") }
                        )
                        OutlinedTextField(
                            value = newLastName,
                            onValueChange = { newLastName = it },
                            label = { Text("Last Name") }
                        )
                        OutlinedTextField(
                            value = newAge,
                            onValueChange = { newAge = it },
                            label = { Text("Age") },
                        )
                        OutlinedTextField(
                            value = newWeight,
                            onValueChange = { newWeight = it },
                            label = { Text("Weight") }
                        )
                        OutlinedTextField(
                            value = newHeight,
                            onValueChange = { newHeight = it },
                            label = { Text("Height") }
                        )
                    } else {
                        ProfileRow(label = "Name", value = "$firstName $lastName")
                        ProfileRow(label = "Age", value = age)
                        ProfileRow(label = "Weight", value = weight)
                        ProfileRow(label = "Height", value = height)
                    }
                }
            }

            // Achievements Section
            Text(
                text = "Stats/Achievements",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            GridSection(newGoals, isEditing) { index, updatedGoal ->
                newGoals = newGoals.toMutableList().apply { this[index] = updatedGoal }
            }

            Spacer(modifier = Modifier.weight(1f))

            // Edit and Save Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween

            ) {
                if (isEditing) {
                    Button(onClick = {
                        viewModel.saveFirstName(newFirstName)
                        viewModel.saveLastName(newLastName)
                        viewModel.saveAge(newAge)
                        viewModel.saveWeight(newWeight)
                        viewModel.saveHeight(newHeight)
                        // Save goals logic if required
                        isEditing = false
                    }) {
                        Text("Save")
                    }
                } else {
                    Button(onClick = { isEditing = true }) {
                        Text("Edit")
                    }
                }
            }


        }

        // Bottom Bar positioned at the bottom
        BottomBar(navController = navController, modifier = Modifier.align(Alignment.BottomCenter))
    }

}

@Composable
fun GridSection(goals: List<String>, isEditing: Boolean, onGoalChange: (Int, String) -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        for (rowItems in goals.chunked(3)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                for ((index, goal) in rowItems.withIndex()) {
                    if (isEditing) {
                        OutlinedTextField(
                            value = goal,
                            onValueChange = { onGoalChange(index, it) },
                            label = { Text("Goal") },
                            modifier = Modifier
                                .weight(1f)
                                .aspectRatio(1f)
                        )
                    } else {
                        AchievementCard(title = goal)
                    }
                }
            }
        }
    }
}

@Composable
fun ProfileRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurface)
        Text(text = value, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.primary)
    }
}
@Composable
fun AchievementCard(title: String) {
    Card(
        modifier = Modifier
            .size(100.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE0E0E0))
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Black
            )
        }
    }
}

