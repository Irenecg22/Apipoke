package com.example.apipoke.core.navigation


import kotlinx.serialization.Serializable

// Pantallas sin parámetros
@Serializable
object Inicio
@Serializable
object Login
@Serializable
object Registro

// Pantalla con parámetros (si quieres pasar email y pass, aunque en MVVM no hace falta)
@Serializable
data class Vista(val email: String = "", val pass: String = "")



