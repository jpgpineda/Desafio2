package com.example.desafio2.Entity

import kotlinx.serialization.Serializable

@Serializable
data class BookData(
    val data: MutableList<Book>
): java.io.Serializable