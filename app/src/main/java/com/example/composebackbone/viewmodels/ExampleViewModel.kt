package com.example.composebackbone.viewmodels

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.composebackbone.repo.ExampleRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by Spencer Searle, github: sdsearle on 9/16/2023.
 */


@HiltViewModel
class ExampleViewModel @Inject constructor(val repo: ExampleRepo): ViewModel() {

    val timesClicked = repo.timeClicked
    val apiString = repo.pokemonList
    val colors = repo.colors


    fun clicked(navController: NavController) {
        navController.navigate("detail")
    }

    fun add() {
        repo.addClicked()
    }

    suspend fun getPokemon(value: String) {
        repo.getPokemons(value)
    }

}