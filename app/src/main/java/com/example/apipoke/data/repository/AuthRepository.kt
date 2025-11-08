package com.example.apipoke.data.repository


import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await

class AuthRepository {

    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    fun getCurrentUser(): FirebaseUser? {
        return firebaseAuth.currentUser
    }

    suspend fun register(email: String, password: String): Result<FirebaseUser> {
        val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
        if (result.user != null) {
            return Result.success(result.user!!)
        }
        return Result.failure(Exception("Error al registrar"))
    }

    suspend fun login(email: String, password: String): Result<FirebaseUser> {
        val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
        if (result.user != null) {
            return Result.success(result.user!!)
        }
        return Result.failure(Exception("Error al iniciar sesión"))
    }

    fun logOut() {
        firebaseAuth.signOut()
    }

    fun isUserLogIn(): Boolean {
        return firebaseAuth.currentUser != null
    }
}
