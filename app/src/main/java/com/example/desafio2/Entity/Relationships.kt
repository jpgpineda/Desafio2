package com.example.desafio2.Entity

import kotlinx.serialization.Serializable

@Serializable
data class Relationships(
    val authors: Authors,
    val categories: Categories
): java.io.Serializable