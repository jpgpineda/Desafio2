package com.example.desafio2.Entity

import kotlinx.serialization.Serializable

@Serializable
data class ApiErrorDetail(
    val status: String,
    val title: String,
    val detail: String
)
