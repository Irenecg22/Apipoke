package com.example.apipoke.ui.screens


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.apipoke.R

@Composable
fun InicioScreen(
    onLoginClick: () -> Unit,
    onRegisterClick: () -> Unit
) {
    Surface(modifier = Modifier.fillMaxSize(), color = Color(0x3EF44336)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Mi app Pokémon", fontSize = 32.sp, color = Color(0xFFF44336))
            Spacer(modifier = Modifier.height(20.dp))
            Image(painter = painterResource(id = R.drawable.poke_ball),
                contentDescription = "Logo Pokémon", modifier = Modifier.size(150.dp))
            Spacer(modifier = Modifier.height(40.dp))
            Button(onClick = onLoginClick, colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF44336))) {
                Text("Iniciar Sesión", color = Color.White, fontSize = 18.sp)
            }
            Spacer(modifier = Modifier.height(15.dp))
            TextButton(onClick = onRegisterClick) {
                Text("¿No tienes cuenta? Regístrate aquí", color = Color(0xFFF44336))
            }
        }
    }
}
