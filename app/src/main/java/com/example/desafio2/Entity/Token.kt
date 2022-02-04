package com.example.desafio2.Entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Token(
    @SerialName("plain-text-token")
    val token: String
)