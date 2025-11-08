package com.example.apipoke.core.navigation


import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.apipoke.ui.screens.InicioScreen
import com.example.apipoke.ui.screens.LoginScreen
import com.example.apipoke.ui.screens.RegisterScreen
import com.example.apipoke.ui.screens.VistaScreen

@Composable
fun NavigationWrapper() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "inicio") {

        // Inicio
        composable("inicio") {
            InicioScreen(
                onLoginClick = { navController.navigate("login") },
                onRegisterClick = { navController.navigate("registro") }
            )
        }

        // Login
        composable("login") {
            LoginScreen(
                onLoginSuccess = { navController.navigate("vista") {
                    popUpTo("inicio") { inclusive = true } // Limpiar stack
                } }
            )
        }

        // Registro
        composable("registro") {
            RegisterScreen(
                onRegisterSuccess = { navController.navigate("login") {
                    popUpTo("inicio") { inclusive = false }
                } }
            )
        }

        // VistaScreen (usuario + Pokémon)
        composable("vista") {
            VistaScreen()
        }
    }
}
