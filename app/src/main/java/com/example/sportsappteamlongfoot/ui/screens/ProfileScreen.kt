package com.example.sportsappteamlongfoot.ui.screens

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

@Composable
fun ProfileScreen() {
    // Local state for profile data
    var name by remember { mutableStateOf("Naomie Edward") }
    var age by remember { mutableStateOf(20) }
    var height by remember { mutableStateOf(180) }
    var weight by remember { mutableStateOf(70) }
    var goals by remember { mutableStateOf(listOf("Goal 1", "Goal 2", "Goal 3", "Goal 4", "Goal 5", "Goal 6")) }
    var isEditing by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        Icon(
            imageVector = Icons.Default.AccountCircle,
            contentDescription = "Profile Icon",
            tint = Color.Gray,
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .align(Alignment.End)
        )
        // Profile Header
        Text(
            text = "Profile",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.align(Alignment.CenterHorizontally)

        )

        Spacer(modifier = Modifier.height(16.dp))

        // User Information Section
        Text(
            text = "Information",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.height(8.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF0F0F0))
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                ProfileRow(label = "Name:", value = name)
                ProfileRow(label = "Age:", value = "$age")
                ProfileRow(label = "Height:", value = "$height cm")
                ProfileRow(label = "Weight:", value = "$weight kg")
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        // Achievements Section
        Text(
            text = "Stats/Achievements",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Achievements Grid
        Column(modifier = Modifier.fillMaxWidth()) {
            for (chunk in goals.chunked(3)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    chunk.forEach { AchievementCard(it) }
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        // Edit Button
        Button(
            onClick = { isEditing = true },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(16.dp)
        ) {
            Text("Edit")
        }

        // Show Edit Dialog
        if (isEditing) {
            EditProfileDialog(
                name = name,
                age = age,
                height = height,
                weight = weight,
                goals = goals,
                onSave = { newName, newAge, newHeight, newWeight, newGoals ->
                    name = newName
                    age = newAge
                    height = newHeight
                    weight = newWeight
                    goals = newGoals
                    isEditing = false
                },
                onDismiss = { isEditing = false }
            )
        }
    }
}

@Composable
fun ProfileRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, fontSize = 16.sp, color = Color.Black)
        Text(text = value, fontSize = 16.sp, color = Color.Gray)
    }
    Spacer(modifier = Modifier.height(8.dp))
}

@Composable
fun EditProfileDialog(
    name: String,
    age: Int,
    height: Int,
    weight: Int,
    goals: List<String>,
    onSave: (String, Int, Int, Int, List<String>) -> Unit,
    onDismiss: () -> Unit
) {
    var newName by remember { mutableStateOf(name) }
    var newAge by remember { mutableStateOf(age.toString()) }
    var newHeight by remember { mutableStateOf(height.toString()) }
    var newWeight by remember { mutableStateOf(weight.toString()) }
    var newGoals by remember { mutableStateOf(goals.joinToString(", ")) }

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(onClick = {
                onSave(
                    newName,
                    newAge.toIntOrNull() ?: age,
                    newHeight.toIntOrNull() ?: height,
                    newWeight.toIntOrNull() ?: weight,
                    newGoals.split(",").map { it.trim() }
                )
            }) {
                Text("Save")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancel")
            }
        },
        title = { Text("Edit Profile") },
        text = {
            Column {
                OutlinedTextField(
                    value = newName,
                    onValueChange = { newName = it },
                    label = { Text("Name") }
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = newAge,
                    onValueChange = { newAge = it },
                    label = { Text("Age") },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = newHeight,
                    onValueChange = { newHeight = it },
                    label = { Text("Height (cm)") },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = newWeight,
                    onValueChange = { newWeight = it },
                    label = { Text("Weight (kg)") },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = newGoals,
                    onValueChange = { newGoals = it },
                    label = { Text("Goals (comma-separated)") }
                )
            }
        }
    )
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

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    ProfileScreen()
}
