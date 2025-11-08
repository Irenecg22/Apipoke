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
        val emailValue = _email.value
        val passwordValue = _password.value

        if (emailValue.isBlank() || passwordValue.isBlank()) {
            _errorMessage.value = "Completa todos los campos"
            return
        }

        viewModelScope.launch {
            _isLoading.value = true
            val result = authRepository.login(emailValue, passwordValue)
            result.onSuccess { _isLoginOk.value = true }
                .onFailure { _errorMessage.value = it.message }
            _isLoading.value = false
        }
    }
}
