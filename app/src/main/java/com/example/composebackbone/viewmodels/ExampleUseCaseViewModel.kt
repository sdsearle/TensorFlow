package com.example.tensorflow.viewmodels

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.tensorflow.usecase.add.AddTimesClickedUseCase
import com.example.tensorflow.usecase.pokemon.GetPokemonColorsUseCase
import com.example.tensorflow.usecase.pokemon.GetPokemonListUseCase
import com.example.tensorflow.usecase.add.GetTimeClickedUseCase
import com.example.tensorflow.usecase.pokemon.RequestPokemonUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by Spencer Searle, github: sdsearle on 9/16/2023.
 */


@HiltViewModel
class ExampleUseCaseViewModel @Inject constructor(
    getTimeClickedUseCase: GetTimeClickedUseCase,
    getPokemonColorsUseCase: GetPokemonColorsUseCase,
    getPokemonListUseCase: GetPokemonListUseCase,
    val addTimesClickedUseCase: AddTimesClickedUseCase,
    val requestPokemonUseCase: RequestPokemonUseCase
) : ViewModel() {

    val timesClicked = addTimesClickedUseCase.invoke()
    val pokemonList = getPokemonListUseCase.invoke()
    val colors = getPokemonColorsUseCase.invoke()


    fun clicked(navController: NavController) {
        navController.navigate("detail")
    }

    fun add() {
        addTimesClickedUseCase.invoke()
    }

    suspend fun getPokemon(value: String) {
        requestPokemonUseCase.invoke(value)
    }

}