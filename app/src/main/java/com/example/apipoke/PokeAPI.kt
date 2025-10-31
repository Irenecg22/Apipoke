package com.example.apipoke

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface PokeAPI {
    @Headers("Accept: application/json")
    // Método para obtener todos los pokemon
    @GET("pokemon")
    fun getPokemons(
        @Query("limit") limit: Int = 20, // número de pokémon
        @Query("offset") offset: Int = 0
    ): Call<PokemonResponse>
    // Método para obtener una pokemon por su ID
    @GET("pokemon/{id}")
    fun getPokemon(@Path("id") id: Int): Call<Pokemon>
}