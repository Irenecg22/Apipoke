package com.example.apipoke


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.apipoke.core.navigation.NavigationWrapper
import com.example.apipoke.ui.theme.ApiPokeTheme
import com.google.firebase.FirebaseApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        FirebaseApp.initializeApp(this)

        setContent {
            ApiPokeTheme {
                NavigationWrapper()
            }
        }
    }
}