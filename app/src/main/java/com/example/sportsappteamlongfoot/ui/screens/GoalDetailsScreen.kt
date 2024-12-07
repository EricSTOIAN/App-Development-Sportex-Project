package com.example.sportsappteamlongfoot.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.sportsappteamlongfoot.data.Goal
import com.example.sportsappteamlongfoot.ui.MyViewModelSimpleSaved


//For debugging purposes
private val goals: List<Goal> = listOf(
    Goal("Run 25 km","Running","December 19 2029","Preparing for a competition"),
    Goal("Bike 50km ","Biking","March 2 2021","Just for fun"),
    Goal("Do 60 Push-Ups","Exercises","June 7 2027","To be stronger")
)

@Composable
fun GoalDetailsScreen(navController: NavController, viewModel: MyViewModelSimpleSaved){
    val scrollStateBox = rememberScrollState()
    val scrollStateLazyColumnsActiveGoals = rememberScrollState()
    val scrollStateLazyColumnsUpcomingGoals = rememberScrollState()


    Box(Modifier
        .fillMaxSize()
        .background(Color.White)
       ){
        Column(modifier = Modifier.padding(20.dp)){
            Text(text="Goals",
                fontSize = 45.sp)

            Row(modifier = Modifier.padding(top = 10.dp, bottom = 10.dp)){
                Text(text= "Active Goals",
                    fontSize = 30.sp,
                    modifier = Modifier.padding(top = 10.dp, bottom = 10.dp))
            }

            LazyColumn (modifier = Modifier
                .padding(top = 15.dp, bottom = 15.dp)) {
                items(goals){ goal ->
                    Column(modifier =
                        Modifier
                        .padding(top = 15.dp, bottom = 15.dp)
                        .border(
                            border = BorderStroke(8.dp, Color.LightGray),
                            shape = RoundedCornerShape(16.dp)
                        )){
                        Column(modifier =
                        Modifier
                            .padding(start = 20.dp,top = 25.dp, end= 15.dp,bottom= 20.dp)){
                            Text(text = goal.name)
                            Text(text = goal.description, modifier = Modifier.padding(top = 10.dp))
                        }
                    }
                }
            }
            Row(modifier = Modifier.padding(top = 10.dp, bottom = 10.dp)){
                Text(text= "Upcoming Goals",
                    fontSize = 30.sp,
                    modifier = Modifier.padding(top = 10.dp, bottom = 10.dp))
            }

            LazyColumn (modifier = Modifier
                .padding(top = 15.dp, bottom = 15.dp)
            ) {

                items(goals){ goal ->
                    Column(modifier =
                    Modifier
                        .padding(top = 15.dp, bottom = 15.dp)
                        .border(
                            border = BorderStroke(8.dp, Color.LightGray),
                            shape = RoundedCornerShape(16.dp)
                        )){
                        Column(modifier =
                        Modifier
                            .padding(start = 20.dp,top = 25.dp, end= 15.dp,bottom= 20.dp)){
                            Text(text = goal.name)
                            Text(text = goal.description, modifier = Modifier.padding(top = 10.dp))
                        }
                    }
                }
            }
        }
    }
}

@SuppressLint("NewApi")
@Preview
@Composable
fun GoalDetailsPreview(){
    val mockContext = LocalContext.current
    val mockViewModel = remember {MyViewModelSimpleSaved(mockContext)}
    val mockNavController = rememberNavController()
    GoalDetailsScreen(mockNavController,mockViewModel)
}