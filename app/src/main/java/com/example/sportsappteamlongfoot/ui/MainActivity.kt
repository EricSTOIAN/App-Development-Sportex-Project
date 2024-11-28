package com.example.sportsappteamlongfoot.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sportsappteamlongfoot.model.AIModel
import com.example.sportsappteamlongfoot.ui.theme.SportsAppTeamLongFootTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SportsAppTeamLongFootTheme {
                AIChatBox(modifier = Modifier.fillMaxSize())
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}


@Composable
fun AIChatBox(modifier: Modifier = Modifier){
    var userInput by rememberSaveable { mutableStateOf("") }
    var aiResponseInUI by rememberSaveable {mutableStateOf("")}
    val aiModel = AIModel()
    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()
    var btnIsEnabled by rememberSaveable { mutableStateOf(true) }
    val focusManager = LocalFocusManager.current


    Box(modifier = modifier.padding(top = 50.dp).verticalScroll(scrollState)){
        Text(
            modifier = Modifier.padding(top = 25.dp),
            text = "What can I help you with",
            fontSize = 30.sp
        )
        
        TextField(
            value = userInput,
            onValueChange = { userInput = it},
            textStyle = TextStyle(textAlign = TextAlign.Center),
            label = { Text(text = "Type anything")},
            modifier = Modifier.padding(top = 75.dp)
        )


        Button(onClick = {
            aiResponseInUI = " "
            btnIsEnabled = false
            focusManager.clearFocus() //Hides the keyboard
            coroutineScope.launch{
                    val response = aiModel.GenerateAIResponse(userInput).toString()

                    //Gives typing effect
                    for(i in response.indices){
                        aiResponseInUI = response.substring(0, i + 1)
                        delay(20) // Delay to simulate typing
                    }

                    btnIsEnabled = true
                }
            },
            modifier= Modifier.padding(start = 150.dp, top = 150.dp),
            enabled = btnIsEnabled
        ) {
            Text(
                text = if (!btnIsEnabled) "Typing..." else "Generate"
            )
        }

        Text(
            modifier = Modifier.padding(top = 260.dp),
            text = aiResponseInUI,
            fontSize = 30.sp,
            lineHeight = 35.sp
        )
    }
}

@Composable
fun AIResponse(aiModel: AIModel, userInput: String){
    var aiResponseInUI by rememberSaveable {mutableStateOf("")}
    LaunchedEffect(Unit){
        aiResponseInUI = aiModel.GenerateAIResponse(userInput).toString()
    }

    Text(
        modifier = Modifier.padding(top = 260.dp),
        text = aiResponseInUI,
        fontSize = 30.sp,
        lineHeight = 35.sp
    )
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SportsAppTeamLongFootTheme {
        //val mockAIModel = AIModel()
        AIChatBox(modifier = Modifier.fillMaxSize())
    }
}
