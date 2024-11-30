package com.example.sportsappteamlongfoot.ui.navigation

import androidx.compose.runtime.Composable
import com.example.sportsappteamlongfoot.ui.MainScreen

interface QuizDestination {
    val route: String
    val screen: @Composable () -> Unit
}

/**
 * Quiz app navigation destinations
 */
object MainMenu : QuizDestination {
    override val route = "main_menu"
    override val screen: @Composable () -> Unit = { MainScreen() }
}

val quizAppScreens = listOf(MainMenu)