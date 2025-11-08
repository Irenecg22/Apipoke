package com.example.apipoke.data.repository


import com.example.apipoke.data.models.Pokemon
import com.example.apipoke.data.network.RetrofitClient

class PokemonRepository {

    private val api = RetrofitClient.pokeAPI

    suspend fun getPokemonList(): ArrayList<Pokemon>? {
        val call = api.getPokemons().execute()
        val body = call.body()
        return if (call.isSuccessful) {
            body?.results
        } else {
            ArrayList()
        }
    }
}
