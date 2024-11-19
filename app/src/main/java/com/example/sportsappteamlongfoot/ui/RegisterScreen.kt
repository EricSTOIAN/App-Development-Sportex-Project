package com.example.sportsappteamlongfoot.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun RegisterScreen(onNavigateToLogin: () -> Unit, modifier: Modifier = Modifier) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
    ) {
        Text(
            text = "Register",
            style = androidx.compose.material3.MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.height(16.dp))

        BasicTextField(
            value = username,
            onValueChange = { username = it },
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            singleLine = true,
            decorationBox = { innerTextField ->
                if (username.isEmpty()) Text(text = "Username", color = MaterialTheme.colorScheme.onSurface)
                innerTextField()
            }
        )
        Spacer(modifier = Modifier.height(8.dp))
        BasicTextField(
            value = password,
            onValueChange = { password = it },
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            decorationBox = { innerTextField ->
                if (password.isEmpty()) Text(text = "Password", color = MaterialTheme.colorScheme.onSurface)
                innerTextField()
            }
        )
        Spacer(modifier = Modifier.height(8.dp))
        BasicTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            decorationBox = { innerTextField ->
                if (confirmPassword.isEmpty()) Text(text = "Confirm Password", color = MaterialTheme.colorScheme.onSurface)
                innerTextField()
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { /* registration logic */ }) {
            Text(text = "Register")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = onNavigateToLogin) {
            Text(text = "Already have an account? Login")
        }
    }
}
