package com.example.composebackbone.usecase.pokemon

import com.example.composebackbone.repo.ExampleRepo
import javax.inject.Inject

class GetPokemonListUseCase @Inject constructor(private val repo: ExampleRepo){
    fun invoke() = repo.pokemonList
}