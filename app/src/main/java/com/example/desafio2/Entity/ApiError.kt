package com.example.desafio2.Entity

import kotlinx.serialization.Serializable

@Serializable
data class ApiError(
    val errors: ArrayList<ApiErrorDetail>
)
