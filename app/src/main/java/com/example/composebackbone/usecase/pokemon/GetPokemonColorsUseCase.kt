package com.example.composebackbone.usecase.pokemon

import com.example.composebackbone.repo.ExampleRepo
import javax.inject.Inject

class GetPokemonColorsUseCase @Inject constructor(private val repo: ExampleRepo){
    fun invoke() = repo.POKE_COLORS
}