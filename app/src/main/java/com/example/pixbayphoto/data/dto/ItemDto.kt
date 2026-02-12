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
    val previewWidth: Int? = null,
    val previewHeight: Int? = null,
    @SerialName("webFormatURL")
    val webFormatUrl: String? = null,
    val webFormatWidth: Int? = null,
    val webFormatHeight: Int? = null,
    @SerialName("largeImageURL")
    val largeImageUrl: String? = null,
    val imageWidth: Int? = null,
    val imageHeight: Int? = null,
    val imageSize: Int? = null,
    val views: Int? = null,
    val downloads: Int? = null,
    val collections: Int? = null,
    val likes: Int? = null,
    val comments: Int? = null,
    val userId: Long? = null,
    val user: String? = null,
    @SerialName("userImageURL")
    val userImageUrl: String? = null,
    val noAiTraining: Boolean? = null,
    val isAiGenerated: Boolean? = null,
    val isGRated: Boolean? = null,
    val isLowQuality: Boolean? = null,
    @SerialName("userURL")
    val userUrl: String? = null
)

@Serializable
data class ItemResponse(
    val total: Int? = null,
    val totalHits: Int? = null,
    val hits: List<ItemDto>? = null
)
