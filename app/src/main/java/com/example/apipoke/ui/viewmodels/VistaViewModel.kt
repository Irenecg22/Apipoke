package com.example.apipoke.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apipoke.data.models.Pokemon
import com.example.apipoke.data.repository.PokemonRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class VistaViewModel : ViewModel() {
    private val pokemonRepository = PokemonRepository()
    private val auth = FirebaseAuth.getInstance()

    private val _nombre = MutableStateFlow(auth.currentUser?.displayName ?: "Usuario")
    val nombre: StateFlow<String> = _nombre

    private val _email = MutableStateFlow(auth.currentUser?.email ?: "Sin email")
    val email: StateFlow<String> = _email

    private val _pokemons = MutableStateFlow<List<Pokemon>>(emptyList())
    val pokemons: StateFlow<List<Pokemon>> = _pokemons

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    init {
        loadPokemons()
    }

    private fun loadPokemons() {
        viewModelScope.launch {
            _isLoading.value = true
            val result = pokemonRepository.getPokemonList()
            _pokemons.value = result ?: emptyList()
            _isLoading.value = false
        }
    }
}

