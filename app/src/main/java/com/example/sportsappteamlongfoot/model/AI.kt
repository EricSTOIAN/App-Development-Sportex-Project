package com.example.sportsappteamlongfoot.model

import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.GenerateContentResponse
import com.google.firebase.vertexai.*
import kotlinx.coroutines.*

//Refer to the following instructions: https://medium.com/@bhoomigadhiya/integrating-googles-gemini-into-the-android-app-520508975c2e
class AIModel(){


    //Using Gemini
    val generativeModel = GenerativeModel(
    // For text-only input, use the gemini-pro model
    modelName = "gemini-1.5-flash",

    // Access your API key as a Build Configuration variable (see "Set up your API key" above)
    //WARNING: DO NOT USE THIS API KEY FOR ANYTHING ELSE.
    apiKey = "AIzaSyCZ39zy4Zv5-sZvtlqRJiQ8aIqr_u8sth4"
)

private val prompt = "Write a story about a magic backpack."

//Uses coroutines
private val response = runBlocking{generativeModel.generateContent(prompt)}

    suspend fun GenerateAIResponse(prompt: String): GenerateContentResponse {
        return generativeModel.generateContent(prompt)
    }
}