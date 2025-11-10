package com.example.apipoke.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apipoke.data.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private val authRepository = AuthRepository()

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _isLoginOk = MutableStateFlow(false)
    val isLoginOk: StateFlow<Boolean> = _isLoginOk

    fun onEmailChange(newEmail: String) { _email.value = newEmail }
    fun onPasswordChange(newPassword: String) { _password.value = newPassword }

    fun login() {
        val emailValue = _email.value.trim()
        val passwordValue = _password.value.trim()

        if (emailValue.isBlank() || passwordValue.isBlank()) {
            _errorMessage.value = "Completa todos los campos"
            return
        }

        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            _isLoginOk.value = false

            try {
                val result = authRepository.login(emailValue, passwordValue)
                result.onSuccess {
                    _isLoginOk.value = true
                }.onFailure {
                    _errorMessage.value = it.message ?: "Error al iniciar sesión"
                }
            } catch (e: Exception) {
                _errorMessage.value = e.message ?: "Error inesperado al iniciar sesión"
            } finally {
                _isLoading.value = false
            }
        }
    }
}

