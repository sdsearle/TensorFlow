package com.example.composebackbone.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

/**
 * Created by Spencer Searle, github: sdsearle on 9/21/2023.
 */

@Serializable
data class PokemonList(
    @SerialName("pokemon_species")
    val pokemons: List<Pokemon> = listOf()
)
