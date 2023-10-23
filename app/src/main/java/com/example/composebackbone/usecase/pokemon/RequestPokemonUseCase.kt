package com.example.composebackbone.usecase.pokemon

import com.example.composebackbone.repo.ExampleRepo
import javax.inject.Inject

class RequestPokemonUseCase @Inject constructor(private val repo: ExampleRepo) {
    suspend fun invoke(value: String){
        repo.getPokemons(value)
    }
}