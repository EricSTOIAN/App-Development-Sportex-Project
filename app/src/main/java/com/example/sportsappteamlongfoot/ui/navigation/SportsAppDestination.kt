package com.example.sportsappteamlongfoot.ui.navigation
import com.example.sportsappteamlongfoot.ui.navigation.MainMenu

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.sportsappteamlongfoot.ui.MainScreen
import com.example.sportsappteamlongfoot.ui.screens.LoginScreen
import com.example.sportsappteamlongfoot.ui.screens.RegisterScreen
interface AppDestination {
    val route: String
    val screen: @Composable () -> Unit
}

object MainMenu : AppDestination {
    override val route = "main_menu"
    override val screen: @Composable () -> Unit = { MainScreen() }
}

object Register : AppDestination {
    override val route = "register"
    override val screen: @Composable () -> Unit = {
        RegisterScreen(
            onNavigateToLogin = { /* Navigate to Login */ }, modifier = Modifier,
            navController = TODO(),
            viewModel = TODO()
        )
    }
}

object Login : AppDestination {
    override val route = "login"
    override val screen: @Composable () -> Unit = {
        LoginScreen(onNavigateToRegister = { /* Navigate to Register */ },
            onNavigateToMenu = { /* Navigate to MainMenu */ },
            modifier = TODO(),
            viewModel = TODO()
        )
    }
}
val quizAppScreens = listOf(MainMenu)