package com.example.apipoke.data.repository

import com.example.apipoke.data.models.Pokemon
import com.example.apipoke.data.network.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PokemonRepository {
    private val api = RetrofitClient.pokeAPI

    suspend fun getPokemonList(): ArrayList<Pokemon>? {
        return withContext(Dispatchers.IO) {
            try {
                val response = api.getPokemons().execute()
                if (response.isSuccessful) {
                    response.body()?.results ?: arrayListOf()
                } else {
                    arrayListOf()
                }
            } catch (e: Exception) {
                e.printStackTrace()
                arrayListOf()
            }
        }
    }
}

