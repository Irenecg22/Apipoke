package com.example.apipoke

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.apipoke.ui.theme.ApiPokeTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class DatosUsuario : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ApiPokeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    PantallaDatosUsuario(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun PantallaDatosUsuario(modifier: Modifier = Modifier) {
    val auth = FirebaseAuth.getInstance()
    val db = FirebaseFirestore.getInstance()

    var nombre by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

    val user = auth.currentUser

    LaunchedEffect(Unit) {
        user?.let {
            db.collection("usuarios").document(it.uid).get()
                .addOnSuccessListener { document ->
                    nombre = document.getString("nombre") ?: ""
                    email = document.getString("email") ?: ""
                }
        }
    }

    Surface(color = Color(0xFFDFFFD6), modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .padding(30.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Datos del usuario", style = MaterialTheme.typography.headlineMedium, color = Color.Blue)
            Spacer(modifier = Modifier.height(20.dp))
            Text("Nombre: $nombre")
            Text("Email: $email")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PantallaDatosUsuarioPreview() {
    ApiPokeTheme {
        PantallaDatosUsuario()
    }
}
