package com.example.apipoke

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.concurrent.thread

class Api : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PantallaApi()
        }
    }
}

@Composable
fun PantallaApi() {
    var pokemons by remember { mutableStateOf<List<Pokemon>>(emptyList()) }
    var cargando by remember { mutableStateOf(true) }


    if (cargando) {
        thread {
            try {
                val retrofit = Retrofit.Builder()
                    .baseUrl("https://pokeapi.co/api/v2/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

                val servicio = retrofit.create(PokeAPI::class.java)
                val respuesta: Response<PokemonResponse> =
                    servicio.getPokemons().execute()

                if (respuesta.isSuccessful) {
                    pokemons = respuesta.body()?.results ?: emptyList()
                }
            } catch (e: Exception) {
                Log.e("API", "Error: ${e.message}")
            } finally {
                cargando = false
            }
        }
    }

    if (cargando) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(pokemons) { pokemon ->
                PokemonFila(pokemon)
            }
        }
    }
}

@Composable
fun PokemonFila(pokemon: Pokemon) {
    val imageUrl = getPokemonImageUrl(pokemon.getUrl())

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = rememberAsyncImagePainter(imageUrl),
            contentDescription = pokemon.getNombre(),
            modifier = Modifier.size(100.dp),
            contentScale = ContentScale.Fit
        )
        Text(text = pokemon.getNombre().replaceFirstChar { it.uppercase() })
        Spacer(modifier = Modifier.height(10.dp))
    }
}

fun getPokemonImageUrl(url: String): String {
    val id = url.trimEnd('/').split("/").last()
    return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$id.png"
}


