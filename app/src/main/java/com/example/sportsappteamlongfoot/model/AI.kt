package com.example.sportsappteamlongfoot.model

import com.google.ai.client.generativeai.GenerativeModel
import com.google.firebase.vertexai.*
import kotlinx.coroutines.*

//Refer to the following instructions: https://medium.com/@bhoomigadhiya/integrating-googles-gemini-into-the-android-app-520508975c2e
class AIModel(){
val generativeModel = GenerativeModel(
    // For text-only input, use the gemini-pro model
    modelName = "gemini-1.5-flash",

    // Access your API key as a Build Configuration variable (see "Set up your API key" above)
    apiKey = "AIzaSyCZ39zy4Zv5-sZvtlqRJiQ8aIqr_u8sth4"
)

val prompt = "Write a story about a magic backpack."

//Uses coroutines
val response = runBlocking{generativeModel.generateContent(prompt)}



}