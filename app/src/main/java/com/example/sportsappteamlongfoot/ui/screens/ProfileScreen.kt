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
import com.example.sportsappteamlongfoot.ui.MyViewModelSimpleSaved

@Composable
fun ProfileScreen(viewModel: MyViewModelSimpleSaved) {
    val firstName by viewModel.firstName.collectAsState()
    val lastName by viewModel.lastName.collectAsState()
    val age by viewModel.age.collectAsState()
    val weight by viewModel.weight.collectAsState()
    val height by viewModel.height.collectAsState()

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
                ProfileRow(label = "Name", value = "$firstName $lastName")
                ProfileRow(label = "Age", value = age)
                ProfileRow(label = "Weight", value = weight)
                ProfileRow(label = "Height", value = height)
            }
        }

        // Achievements Section
        Text(
            text = "Stats/Achievements",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        GridSection()

        Spacer(modifier = Modifier.weight(1f))

        // Bottom Navigation Bar
        BottomNavigationBar()
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
fun GridSection() {
    val items = List(6) { " " } // Placeholder items
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        for (rowItems in items.chunked(3)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                for (item in rowItems) {
                    AchievementCard(title = item)
                }
            }
        }
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


@Composable
fun BottomNavigationBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(Color.LightGray, RoundedCornerShape(8.dp)),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.AccountCircle, // Replace with actual icons
            contentDescription = "Home",
            modifier = Modifier.size(24.dp)
        )
        Icon(
            imageVector = Icons.Default.AccountCircle, // Replace with Calendar icon
            contentDescription = "Calendar",
            modifier = Modifier.size(24.dp)
        )
        Icon(
            imageVector = Icons.Default.AccountCircle, // Replace with Star icon
            contentDescription = "Achievements",
            modifier = Modifier.size(24.dp)
        )
        Icon(
            imageVector = Icons.Default.AccountCircle, // Replace with Profile icon
            contentDescription = "Profile",
            modifier = Modifier.size(24.dp)
        )
    }
}
