package com.example.pixbayphoto.domain.model

data class Item(
    val id: Long,
    val pageUrl: String,
    val type: String,
    val tags: String,
    val previewUrl: String,
    val user: String
)