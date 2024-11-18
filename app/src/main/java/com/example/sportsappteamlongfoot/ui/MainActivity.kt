package com.example.sportsappteamlongfoot.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sportsappteamlongfoot.model.AIModel
import com.example.sportsappteamlongfoot.ui.theme.SportsAppTeamLongFootTheme
import kotlinx.coroutines.runBlocking

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
    var aiResponse by rememberSaveable {mutableStateOf("")}
    val aiModel = AIModel()

    Box(modifier){
        Text(
            modifier = Modifier.padding(5.dp),
            text = "What can I help you with",
            fontSize = 30.sp
        )

        TextField(
            value = userInput,
            onValueChange = { userInput = it},
            textStyle = TextStyle(textAlign = TextAlign.Center),
            label = { Text(text = "Please enter your name")},
            modifier = Modifier.padding(top = 75.dp)
        )

        Button(onClick = { aiResponse = runBlocking { aiModel.GenerateAIResponse(userInput).toString() } }) {
            Text(
                text = "Generate"
            )
        }

        Text(
            modifier = Modifier.padding(5.dp),
            text = aiResponse,
            fontSize = 30.sp
        )
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SportsAppTeamLongFootTheme {
        AIChatBox(modifier = Modifier.fillMaxSize())
    }
}