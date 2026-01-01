package com.example.pixbayphoto.data.mapper

import com.example.pixbayphoto.data.dto.PixabayDto
import com.example.pixbayphoto.domain.model.Pixabay

fun PixabayDto.toModel(): Pixabay {
    return Pixabay(
        id = id ?: 0,
        user = user ?: "",
        tags = tags ?: "",
        previewURL = previewURL ?: "",
    )
}
