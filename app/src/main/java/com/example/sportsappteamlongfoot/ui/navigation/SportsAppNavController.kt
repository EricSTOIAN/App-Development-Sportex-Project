package com.example.sportsappteamlongfoot.ui.navigation

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.sportsappteamlongfoot.ui.screens.LoginScreen
import com.example.sportsappteamlongfoot.ui.screens.RegisterScreen
import com.example.sportsappteamlongfoot.ui.MainScreen
import com.example.sportsappteamlongfoot.ui.MyViewModelSimpleSaved
import com.example.sportsappteamlongfoot.ui.theme.SportsAppTeamLongFootTheme

// Define a CompositionLocal for accessing NavController anywhere in the composable hierarchy
val LocalNavController = compositionLocalOf<NavHostController> {
    error("No NavController found!")
}

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: MyViewModelSimpleSaved = viewModel() // Inject ViewModel here
) {
    SportsAppTeamLongFootTheme {
        NavHost(
            navController = navController,
            startDestination = Login.route,
            modifier = modifier
        ) {
            composable(route = MainMenu.route) {
                MainScreen() // Main screen for logged-in users
            }
            composable(route = Register.route) {
                RegisterScreen(
                    onNavigateToLogin = {
                        navController.navigate(Login.route) // Navigate to Login screen
                    },
                    navController = navController, // Pass NavController
                    viewModel = viewModel // Pass ViewModel
                )
            }
            composable(route = Login.route) {
                LoginScreen(
                    onNavigateToRegister = {
                        navController.navigate(Register.route) // Navigate to Register screen
                    },
                    onNavigateToMenu = {
                        navController.navigate(MainMenu.route) // Navigate to Main Menu
                    },
                    viewModel = viewModel // Pass ViewModel
                )
            }
        }
    }}