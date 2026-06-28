package com.example.beautifulstrugglefoundation.ui.spark

import kotlinx.serialization.Serializable

@Serializable
data class DailySpark(
    val id: Int? = null,
    val author_name: String,
    val content: String,
    val created_at: String? = null
)
