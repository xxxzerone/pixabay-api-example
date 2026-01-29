package com.example.pixbayphoto.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ItemDto(
    val id: Long? = null,
    @SerialName("pageURL")
    val pageUrl: String? = null,
    val type: String? = null,
    val tags: String? = null,
    @SerialName("previewURL")
    val previewUrl: String? = null,
    val user: String? = null,
)

@Serializable
data class ItemResponse(
    val total: Int? = null,
    val totalHits: Int? = null,
    val hits: List<ItemDto>? = null
)