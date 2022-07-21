package com.example.miquido.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Photo(
    val author: String,
    val download_url: String,
    val height: Int,
    val id: String,
    val url: String,
    val width: Int
)