package com.example.composebackbone.api

import com.example.composebackbone.models.PokemonList
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.json.Json


/**
 * Created by Spencer Searle, github: sdsearle on 9/20/2023.
 */
class ApiExample {

    val client =  HttpClient()
    val urlExample = "https://pokeapi.co/api/v2/pokemon-color/"

    private val json = Json { ignoreUnknownKeys = true }

    suspend fun pokemonColor(color: String): PokemonList {
        val response = client.get(urlExample+color)
        return json.decodeFromString<PokemonList>(response.body())
    }
}