package com.example.sportsappteamlongfoot.model

import com.google.ai.client.generativeai.GenerativeModel
import com.google.firebase.vertexai.*
import kotlinx.coroutines.*

//Refer to the following instructions: https://medium.com/@bhoomigadhiya/integrating-googles-gemini-into-the-android-app-520508975c2e

val generativeModel = GenerativeModel(
    // For text-only input, use the gemini-pro model
    modelName = "gemini-1.5-flash",

    // Access your API key as a Build Configuration variable (see "Set up your API key" above)
    //(The BuildConfig below does not seem to have the apiKey property.
    // We will figure out if we should directly put the API key here or try our hands
    // again at accessing the API key from local.properties using BuildConfig.apiKey)
    apiKey = "BuildConfig.apiKey"
)

val prompt = "Write a story about a magic backpack."

//Uses coroutines
val response = runBlocking{generativeModel.generateContent(prompt)}