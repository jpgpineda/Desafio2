package com.example.desafio2.Entity

import kotlinx.serialization.Serializable

@Serializable
data class Data(
    val type: String,
    val id: String
): java.io.Serializable