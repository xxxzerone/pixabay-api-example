package com.example.pixbayphoto.data.mapper

import com.example.pixbayphoto.data.dto.ItemDto
import com.example.pixbayphoto.data.dto.ItemResponse
import com.example.pixbayphoto.domain.model.Item
import javax.inject.Inject

class ItemMapper @Inject constructor() {

    fun toDomainList(response: ItemResponse): List<Item> {
        return response.hits?.map { dto ->
            toDomain(dto)
        } ?: emptyList()
    }

    fun toDomain(dto: ItemDto): Item {
        return Item(
            id = dto.id ?: 0,
            pageUrl = dto.pageUrl ?: "",
            type = dto.type ?: "",
            tags = dto.tags ?: "",
            previewUrl = dto.previewUrl ?: "",
            user = dto.user ?: ""
        )
    }
}