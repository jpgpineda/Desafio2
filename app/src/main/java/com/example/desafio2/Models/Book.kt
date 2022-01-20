package com.example.desafio2.Models

import java.io.Serializable

data class Book(
    val title: String,
    val author: Author,
    val category: String,
    val description: String,
    val details: String,
    val image: String
)