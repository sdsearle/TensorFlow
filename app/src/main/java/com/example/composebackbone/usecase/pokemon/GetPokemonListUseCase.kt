package com.example.tensorflow.usecase.pokemon

import com.example.tensorflow.repo.ExampleRepo
import javax.inject.Inject

class GetPokemonListUseCase @Inject constructor(private val repo: ExampleRepo){
    fun invoke() = repo.pokemonList
}