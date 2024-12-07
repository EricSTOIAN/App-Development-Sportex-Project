package com.example.sportsappteamlongfoot.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.painter.Painter

@Composable
fun MainScreen(modifier: Modifier = Modifier,  onProfileClick: () -> Unit ) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        // Welcome Header with Profile Icon
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
                    .clickable { onProfileClick() } // Navigate to Profile when clicked
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Week Summary Header and Cards
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
            CaloriesCard(title = "Burnt Calories", value = "", modifier = Modifier.weight(1f))
            WorkoutsCard(modifier = Modifier.weight(1f))
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Today's Plan Header and Card
        Text(
            text = "Today's Plan",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.height(8.dp))
        LargeEmptyCard()

        Spacer(modifier = Modifier.height(24.dp))

        // Goal Header and Card
        Text(
            text = "Goals",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.height(8.dp))
        LargeEmptyCard()
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
               // Image(
                 //   painter = painterResource(id = R.drawable.calories_icon), // Use your icon resource
                //    contentDescription = "Calories Icon",
                 //   modifier = Modifier.size(24.dp)
                //)
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
fun LargeEmptyCard(modifier: Modifier = Modifier) {
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
            Text(
                text = "", // Placeholder for any future content
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}
@Composable
fun WorkoutsCard(modifier: Modifier = Modifier) {
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
            CircularProgressBar(progress = 0.5f) // Simulated 50% progress
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
            text = "${(progress * 100).toInt()}%", // Progress as a percentage
            style = MaterialTheme.typography.bodyLarge.copy(fontSize = 16.sp),
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center
        )
    }
}

//@Preview(showBackground = true)
//@Composable
//fun MainScreenPreview() {
//    MainScreen()
//}
