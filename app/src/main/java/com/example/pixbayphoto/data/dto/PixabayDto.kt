package com.example.pixbayphoto.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class PixabayDto(
    val id: Int? = null,
    val user: String? = null,
    val tags: String? = null,
    val previewURL: String? = null
)

@Serializable
data class PixabayResponse(
    val total: Int? = null,
    val totalHits: Int? = null,
    val hits: List<PixabayDto>? = null
)
