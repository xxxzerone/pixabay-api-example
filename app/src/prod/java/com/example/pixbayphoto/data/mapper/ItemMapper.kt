package com.example.pixbayphoto.data.mapper

import com.example.pixbayphoto.data.dto.ItemDto
import com.example.pixbayphoto.domain.model.Item

fun ItemDto.toModel(): Item {
    return Item(
        id = id ?: 0,
        pageUrl = pageUrl ?: "",
        type = type ?: "",
        tags = tags ?: "",
        previewUrl = previewUrl ?: "",
        user = user ?: "",
    )
}