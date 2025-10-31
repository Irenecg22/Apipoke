package com.example.apipoke

import com.google.gson.annotations.SerializedName

data class PokemonResponse(
    @SerializedName("results") var results: ArrayList<Pokemon>
)
