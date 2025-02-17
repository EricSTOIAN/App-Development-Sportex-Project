package com.example.sportsappteamlongfoot.ui.screens
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.sportsappteamlongfoot.ui.MyViewModelSimpleSaved

@Composable
fun LoginScreen(
    onNavigateToRegister: () -> Unit,
    onNavigateToMenu: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MyViewModelSimpleSaved // Inject ViewModel here
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
    ) {
        Text(
            text = "Login",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Username", color = Color(0xFF1A1E25)) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,

        )

        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password" , color = Color(0xFF1A1E25)) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { var canLogin = viewModel.checkLogin(username, password)
                            if(canLogin){
                                onNavigateToMenu() //screen change to the main menu screen
                            }}) {
            Text(text = "Login")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = onNavigateToRegister) {
            Text(text = "Don't have an account? Register")
        }
    }
}
