package com.example.apipoke.ui.viewmodels


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apipoke.data.models.Pokemon
import com.example.apipoke.data.repository.PokemonRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class VistaViewModel : ViewModel() {
    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()
    private val pokemonRepo = PokemonRepository()

    private val _nombre = MutableStateFlow("")
    val nombre: StateFlow<String> = _nombre

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email

    private val _pokemons = MutableStateFlow<List<Pokemon>>(emptyList())
    val pokemons: StateFlow<List<Pokemon>> = _pokemons

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    init { loadUserData(); loadPokemons() }

    private fun loadUserData() {
        val user = auth.currentUser
        user?.let {
            db.collection("usuarios").document(it.uid).get()
                .addOnSuccessListener { doc ->
                    _nombre.value = doc.getString("nombre") ?: ""
                    _email.value = doc.getString("email") ?: ""
                }
        }
    }

    private fun loadPokemons() {
        viewModelScope.launch {
            _isLoading.value = true
            val list = pokemonRepo.getPokemonList() ?: emptyList()
            _pokemons.value = list
            _isLoading.value = false
        }
    }
}
