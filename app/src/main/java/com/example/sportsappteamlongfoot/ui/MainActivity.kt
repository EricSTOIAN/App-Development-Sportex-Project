package com.example.sportsappteamlongfoot.ui

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.sportsappteamlongfoot.ui.LoginScreen
import com.example.sportsappteamlongfoot.ui.RegisterScreen
import com.example.sportsappteamlongfoot.ui.navigation.MainMenu
import com.example.sportsappteamlongfoot.ui.theme.SportsAppTeamLongFootTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SportsAppTeamLongFootTheme {
                //val navController = rememberNavController()
                val context = LocalContext.current
                val viewModel = remember { MyViewModelSimpleSaved(context) }
                var isLoginScreen by remember { mutableStateOf(true) }
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    if (isLoginScreen) {
                        LoginScreen(
                            onNavigateToRegister = { isLoginScreen = false },
                            onNavigateToMenu = {
                                //change screen to main screen
                            },
                            modifier = Modifier.padding(innerPadding),
                            viewModel = viewModel
                        )
                    } else {
                        RegisterScreen(
                            onNavigateToLogin = { isLoginScreen = true },
                            modifier = Modifier.padding(innerPadding),
                            viewModel = viewModel
                        )
                    }
                }
            }
        }
    }
}
