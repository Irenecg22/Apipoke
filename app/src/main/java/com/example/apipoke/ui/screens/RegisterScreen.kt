package com.example.apipoke.ui.screens


import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.apipoke.ui.viewmodels.RegisterViewModel

@Composable
fun RegisterScreen(
    onRegisterSuccess: () -> Unit,
    registerViewModel: RegisterViewModel = viewModel()
) {
    val email by registerViewModel.email.collectAsState()
    val password by registerViewModel.password.collectAsState()
    val nombre by registerViewModel.nombre.collectAsState()
    val isLoading by registerViewModel.isLoading.collectAsState()
    val errorMessage by registerViewModel.errorMessage.collectAsState()
    val isRegisterOk by registerViewModel.isRegisterOk.collectAsState()

    LaunchedEffect(isRegisterOk) {
        if (isRegisterOk != null) onRegisterSuccess()
    }

    Surface(modifier = Modifier.fillMaxSize(), color = Color(0xFFE8F5E9)) {
        Column(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Registro Pokémon", fontSize = 30.sp, color = Color(0xFFF44336))
            Spacer(modifier = Modifier.height(30.dp))
            OutlinedTextField(
                value = nombre,
                onValueChange = { registerViewModel.onNombreChange(it) },
                label = { Text("Usuario") },
                modifier = Modifier.fillMaxWidth(0.9f)
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = email,
                onValueChange = { registerViewModel.onEmailChange(it) },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth(0.9f)
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = password,
                onValueChange = { registerViewModel.onPasswordChange(it) },
                label = { Text("Contraseña") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(0.9f)
            )
            Spacer(modifier = Modifier.height(24.dp))
            if (isLoading) {
                CircularProgressIndicator()
            } else {
                Button(
                    onClick = { registerViewModel.register() },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF44336)),
                    modifier = Modifier.fillMaxWidth(0.7f)
                ) {
                    Text("Registrar", color = Color.White)
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            errorMessage?.let { Text(it, color = Color.Red) }
        }
    }
}
