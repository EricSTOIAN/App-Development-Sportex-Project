package com.example.sportsappteamlongfoot.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sportsappteamlongfoot.data.Workout


//For debugging purposes
private val workouts: List<Workout> = listOf(
    Workout("Run 5 km","Running","July 4 2024","First day of running"),
    Workout("Bike 10 km ","Biking","July 15 2024","First day of biking. I want to gradually become better"),
    Workout("Do 20 Push-Ups","Exercises","August 6 2024","I will do 20 pushups for today.")
)

@Composable
fun WorkoutDetailsScreen(modifier:Modifier){
    val scrollStateBox = rememberScrollState()
    val scrollStateLazyColumnsActiveGoals = rememberScrollState()
    val scrollStateLazyColumnsUpcomingGoals = rememberScrollState()


    Box(modifier.background(Color.White)
    ){
        Column(modifier = Modifier.padding(20.dp)){
            Text(text="Workouts",
                fontSize = 45.sp)


            LazyColumn (modifier = Modifier
                .padding(top = 15.dp, bottom = 15.dp)) {
                items(workouts){ workout ->
                    Column(modifier
                        .padding(top = 15.dp, bottom = 15.dp)
                        .border(
                            border = BorderStroke(8.dp, Color.LightGray),
                            shape = RoundedCornerShape(16.dp)
                        )){
                        Column(modifier
                            .padding(start = 20.dp,top = 25.dp, end= 15.dp,bottom= 20.dp)){
                            Text(text = workout.name)
                            Text(text = workout.description, modifier.padding(top = 10.dp))
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun WorkoutDetailsPreview(){
    WorkoutDetailsScreen(Modifier.fillMaxSize())
}