package com.example.composebackbone.viewmodels

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.composebackbone.usecase.AddTimesClickedUseCase
import com.example.composebackbone.usecase.GetPokemonColorsUseCase
import com.example.composebackbone.usecase.GetPokemonListUseCase
import com.example.composebackbone.usecase.GetTimeClickedUseCase
import com.example.composebackbone.usecase.RequestPokemonUseCase
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