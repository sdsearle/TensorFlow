package com.example.tensorflow.usecase.pokemon

import com.example.tensorflow.repo.ExampleRepo
import javax.inject.Inject

class RequestPokemonUseCase @Inject constructor(private val repo: ExampleRepo) {
    suspend fun invoke(value: String){
        repo.getPokemons(value)
    }
}