package com.example.sportsappteamlongfoot.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun GoalDetailsScreen(modifier:Modifier){
    Box(modifier.background(Color.White)){
        Column(){
            Text(text="Goals")

            Row(modifier = Modifier.padding(top = 10.dp, bottom = 10.dp)){
                Text(text= "Active Goals",Modifier.padding(top = 10.dp, bottom = 10.dp))
            }


            Row(modifier = Modifier.padding(top = 10.dp, bottom = 10.dp)){
                Text(text= "Upcoming Goals", Modifier.padding(top = 10.dp, bottom = 10.dp))
            }
        }
    }
}

@Preview
@Composable
fun GoalDetailsPreview(){
    GoalDetailsScreen(Modifier.fillMaxSize())
}