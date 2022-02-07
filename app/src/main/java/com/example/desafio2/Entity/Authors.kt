package com.example.desafio2.Entity

import kotlinx.serialization.Serializable

@Serializable
data class Authors(
    val links: Links
): java.io.Serializable