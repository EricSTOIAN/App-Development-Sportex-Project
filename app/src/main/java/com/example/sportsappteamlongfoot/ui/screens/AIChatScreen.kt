package com.example.sportsappteamlongfoot.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.sportsappteamlongfoot.R
import com.example.sportsappteamlongfoot.data.Goal
import com.example.sportsappteamlongfoot.data.Workout
import com.example.sportsappteamlongfoot.model.AIModel
import com.example.sportsappteamlongfoot.ui.MyViewModelSimpleSaved
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

////For debugging purposes
private val sampleGoals: List<Goal> = listOf(
    Goal("Run 25 km","Preparing for a competition","December 19 2029",),
    Goal("Bike 50km ","Just for fun","March 2 2021",),
    Goal("Do 60 Push-Ups","To be stronger","June 7 2027")
)

private val sampleWorkout: List<Workout> = listOf(
    Workout("Run 25 km","Running","December 19 2029","Preparing to run a marathon"),
    Workout("Bike 50km ","Biking","March 2 2021", "Just for fun"),
    Workout("Do 60 Push-Ups","Exercises","June 7 2027", "Need to increase muscular strength")
)

@SuppressLint("NewApi", "StateFlowValueCalledInComposition")
@Composable
fun AIChatBox(navController: NavController, viewModel: MyViewModelSimpleSaved,modifier: Modifier = Modifier.fillMaxWidth()){
    var userInputInUI by rememberSaveable { mutableStateOf("") }
    var aiResponseInUI by rememberSaveable { mutableStateOf("") }
    val aiModel = AIModel()
    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()
    var btnIsEnabled by rememberSaveable { mutableStateOf(true) }
    val focusManager = LocalFocusManager.current
    val goalsData: List<Goal> = sampleGoals
    val workoutData: List<Workout> = sampleWorkout

//    val goalsData: List<Goal> = viewModel.goals.value
//    val workoutData: List<Workout> = viewModel.workouts.value

    Box(modifier = modifier
        .verticalScroll(scrollState)
        .background(Color.Blue)
    ){

        Box(modifier = modifier
            .padding(start = 20.dp, top = 20.dp,end = 20.dp)){
            Text(
                modifier = Modifier.padding(top = 25.dp),
                text = "What can I help you with",
                fontSize = 30.sp
            )
            TextField(
                value = userInputInUI,
                onValueChange = { userInputInUI = it},
                textStyle = TextStyle(textAlign = TextAlign.Center),
                label = {
                    Text(text = "Type anything",
                        modifier = Modifier.align(alignment = Alignment.Center))
                },
                shape = RoundedCornerShape(50.dp),
                modifier = Modifier
                    .padding(top = 80.dp)
                    .border(border = BorderStroke(5.dp, Color.Gray), shape = RoundedCornerShape(50.dp))
                    .height(70.dp)
                    .width(500.dp)
            )

            Button(
                modifier = Modifier.padding(start = 50.dp, top = 160.dp),
                onClick = {
                    navController.popBackStack()
                }
            ){
                Text(
                    text = "Back"
                )
            }

            Button(onClick = {
                aiResponseInUI = " "
                btnIsEnabled = false
                focusManager.clearFocus() //Hides the keyboard
                coroutineScope.launch{
                    var userInput = userInputInUI;
                    var response = "";

                    //Feed AI Workout and Goals data if user specifies anything related
                    if (userInputInUI.contains("workout") && userInputInUI.contains("goal")){
                        userInput += userInput+"Here are my current workouts"

                        for (item in workoutData){
                            userInput += userInput +
                                    "Name: ${item.name}," +
                                    "Description: ${item.description}," +
                                    "Date: ${item.date},"+
                                    "Type: ${item.type}"  +
                                    "\n"
                        }
                        userInput += userInput+"Here are my current goals"
                        for(item in goalsData){
                            userInput += "Name: ${item.name}"
                            "Date: ${item.date}"
                            "Description: ${item.description}"+
                                    "\n"
                        }
                    }
                    else if (userInputInUI.contains("workout")){
                        userInput += "Here are my current workouts"
                        for (item in workoutData){
                            userInput += userInput +
                            "Name: ${item.name}," +
                            "Description: ${item.description}," +
                            "Date: ${item.date},"+
                            "Type: ${item.type}"  +
                            "\n"
                        }
                    }
                    else if (userInputInUI.contains("goal")){
                        userInput += "Here are my current goals"
                        for(item in goalsData){
                            userInput += "Name: ${item.name}"
                            "Date: ${item.date}"
                            "Description: ${item.description}"+
                            "\n"
                        }
                    }

                    response = aiModel.GenerateAIResponse(userInput).toString()

                    //Gives typing effect
                    for(i in response.indices){
                        aiResponseInUI = response.substring(0, i + 1)
                        delay(20) // Delay to simulate typing
                    }

                    btnIsEnabled = true
                }
            },
                modifier= Modifier.padding(start = 150.dp, top = 160.dp),
                enabled = btnIsEnabled
            ) {
                Text(
                    text = if (!btnIsEnabled) "Typing..." else "Generate"
                )
            }
        }

        Box(modifier = Modifier
            .padding(top=250.dp))
        {

            Image(painter = painterResource(R.drawable.google_gemini_icon),
                contentDescription = "Gemini",
                modifier = Modifier
                    .size(45.dp)
            )

            Box(
                modifier
                    .padding(start = 50.dp, end = 20.dp)
                    .border(
                        border = BorderStroke(8.dp, Color.Gray),
                        shape = RoundedCornerShape(16.dp)
                    )
                    .background(Color.LightGray, shape = RoundedCornerShape(16.dp))

                ){
                Text(
                    text = aiResponseInUI,
                    fontSize = 30.sp,
                    lineHeight = 35.sp,
                    color = Color.Black,
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(25.dp)
                )
            }
        }
    }
}


@SuppressLint("NewApi")
@Preview
@Composable
fun AIChatBoxPreview(){
    val mockContext = LocalContext.current
    val mockViewModel = remember {MyViewModelSimpleSaved(mockContext)}
    val mockNavController = rememberNavController()
    AIChatBox(mockNavController, mockViewModel, Modifier.fillMaxWidth())
}