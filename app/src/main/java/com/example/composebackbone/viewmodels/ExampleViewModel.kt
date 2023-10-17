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
    val colors: Map<String,Color> =
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