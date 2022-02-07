package com.example.desafio2.Entity

import kotlinx.serialization.Serializable

@Serializable
data class Links(
    val self: String,
    val related: String = ""
): java.io.Serializable