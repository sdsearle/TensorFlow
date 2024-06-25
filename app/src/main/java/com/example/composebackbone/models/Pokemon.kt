package com.example.tensorflow.models

import kotlinx.serialization.Serializable

/**
 * Created by Spencer Searle, github: sdsearle on 9/21/2023.
 */

@Serializable
data class Pokemon(
    val name: String ="",
    val url: String = "")
