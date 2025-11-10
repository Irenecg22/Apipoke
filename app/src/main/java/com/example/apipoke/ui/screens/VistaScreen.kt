package com.example.apipoke.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.apipoke.data.models.Pokemon
import com.example.apipoke.ui.viewmodels.VistaViewModel

@Composable
fun VistaScreen(
    vistaViewModel: VistaViewModel = viewModel()
) {
    val pokemons by vistaViewModel.pokemons.collectAsState()
    val isLoading by vistaViewModel.isLoading.collectAsState()

    Surface(modifier = Modifier.fillMaxSize(), color = Color(0xFFDFFFD6)) {
        Column(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Mis Pokémon:",
                style = MaterialTheme.typography.headlineMedium,
                color = Color(0xFF1B5E20)
            )
            Spacer(modifier = Modifier.height(20.dp))

            if (isLoading) {
                CircularProgressIndicator()
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f), // permite hacer scroll
                    horizontalAlignment = Alignment.CenterHorizontally,
                    contentPadding = PaddingValues(bottom = 50.dp)
                ) {
                    items(pokemons) { pokemon ->
                        PokemonFila(pokemon)
                    }
                }
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


