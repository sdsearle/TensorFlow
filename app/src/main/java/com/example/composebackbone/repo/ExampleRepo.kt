package com.example.composebackbone.repo

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
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

    fun addClicked(){
        timeClicked.intValue += 1
    }

    suspend fun getPokemons(value: String) {
        pokemonList.value = api.pokemonColor(value)
    }

}