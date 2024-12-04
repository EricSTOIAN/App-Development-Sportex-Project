package com.example.sportsappteamlongfoot.ui

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sportsappteamlongfoot.model.AIModel
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
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SportsAppTeamLongFootTheme {


                AIChatBox(Modifier.fillMaxSize())
                //val navController = rememberNavController()
//                val context = LocalContext.current
//                val viewModel = remember { MyViewModelSimpleSaved(context) }
//                var isLoginScreen by remember { mutableStateOf(true) }
//                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                    if (isLoginScreen) {
//                        LoginScreen(
//                            onNavigateToRegister = { isLoginScreen = false },
//                            onNavigateToMenu = {
//                                //change screen to main screen
//                            },
//                            modifier = Modifier.padding(innerPadding),
//                            viewModel = viewModel
//                        )
//                    } else {
//                        RegisterScreen(
//                            onNavigateToLogin = { isLoginScreen = true },
//                            modifier = Modifier.padding(innerPadding),
//                            viewModel = viewModel
//                        )
//                    }
//                }
            }
        }
    }
}
