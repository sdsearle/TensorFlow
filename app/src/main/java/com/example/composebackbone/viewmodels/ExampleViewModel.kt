package com.example.tensorflow.viewmodels

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.tensorflow.interactor.AddInteractor
import com.example.tensorflow.interactor.PokemonInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by Spencer Searle, github: sdsearle on 9/16/2023.
 */


@HiltViewModel
class ExampleViewModel @Inject constructor(
    val pokemonInteractor: PokemonInteractor,
    val addInteractor: AddInteractor
) : ViewModel() {

    val timesClicked = addInteractor.timeClicked
    val pokemonList = pokemonInteractor.pokemonList
    val colors = pokemonInteractor.POKE_COLORS


    fun clicked(navController: NavController) {
        navController.navigate("detail")
    }

    fun add() {
        addInteractor.addClicked()
    }

    suspend fun getPokemon(value: String) {
        pokemonInteractor.getPokemons(value)
    }

}