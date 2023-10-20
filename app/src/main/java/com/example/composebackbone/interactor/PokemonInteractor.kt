package com.example.composebackbone.interactor

import com.example.composebackbone.repo.ExampleRepo
import javax.inject.Inject

/**
 * Created by Spencer Searle, github: sdsearle on 9/16/2023.
 */
class PokemonInteractor @Inject constructor(val repo: ExampleRepo){

    val pokemonList = repo.pokemonList
    val POKE_COLORS = repo.POKE_COLORS
    suspend fun getPokemons(value: String) {
        repo.getPokemons(value)
    }

}

