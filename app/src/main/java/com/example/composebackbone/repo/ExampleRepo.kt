package com.example.composebackbone.repo

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import com.example.composebackbone.api.ApiExample
import com.example.composebackbone.models.PokemonList
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Spencer Searle, github: sdsearle on 9/19/2023.
 */
@Singleton
class ExampleRepo @Inject constructor() {
    val api = ApiExample()
    val timeClicked = mutableIntStateOf(0)
    val pokemonList: MutableState<PokemonList?> = mutableStateOf(null)

    val POKE_COLORS: Map<String, Color> =
        mapOf(
            "black" to Color.Black,
            "white" to Color.White,
            "blue" to Color.Blue,
            "red" to Color.Red,
            "green" to Color.Green,
            "yellow" to Color.Yellow,
            "purple" to Color(75,0,130),
            "brown" to Color(139,69,19),
            "gray" to Color.Gray,
            "pink" to Color(255,105,180),
        )

    fun addClicked(){
        timeClicked.intValue += 1
    }

    suspend fun getPokemons(value: String) {
        pokemonList.value = api.pokemonColor(value)
    }

}