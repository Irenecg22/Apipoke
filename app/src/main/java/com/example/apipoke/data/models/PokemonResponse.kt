package com.example.apipoke.data.models

import com.google.gson.annotations.SerializedName

class PokemonResponse(
    @SerializedName("results")
    var results: ArrayList<Pokemon>
)
