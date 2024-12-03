package com.example.sportsappteamlongfoot.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sportsappteamlongfoot.model.AIModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch



@Composable
fun AIChatBox(modifier: Modifier = Modifier){
    var userInput by rememberSaveable { mutableStateOf("") }
    var aiResponseInUI by rememberSaveable { mutableStateOf("") }
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
            shape = CircleShape,
            label = {
                Text(text = "Type anything",
                modifier = Modifier.align(alignment = Alignment.Center))
                    },
            modifier = Modifier
                .padding(top = 75.dp)
                .border(border = BorderStroke(5.dp,Color.Gray), shape = CircleShape)
                .height(70.dp)
                .width(500.dp)
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


@Preview
@Composable
fun AIChatBoxPreview(){
    AIChatBox(Modifier.fillMaxWidth())
}