package com.example.sportsappteamlongfoot.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.sportsappteamlongfoot.ui.LoginScreen
import com.example.sportsappteamlongfoot.ui.RegisterScreen
import com.example.sportsappteamlongfoot.ui.theme.SportsAppTeamLongFootTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SportsAppTeamLongFootTheme {
                var isLoginScreen by remember { mutableStateOf(true) }
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    if (isLoginScreen) {
                        LoginScreen(
                            onNavigateToRegister = { isLoginScreen = false },
                            modifier = Modifier.padding(innerPadding)
                        )
                    } else {
                        RegisterScreen(
                            onNavigateToLogin = { isLoginScreen = true },
                            modifier = Modifier.padding(innerPadding)
                        )
                    }
                }
            }
        }
    }
}
