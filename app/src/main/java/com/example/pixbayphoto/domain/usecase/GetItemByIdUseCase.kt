package com.example.pixbayphoto.domain.usecase

import com.example.pixbayphoto.core.Resource
import com.example.pixbayphoto.core.asResource
import com.example.pixbayphoto.domain.model.Item
import com.example.pixbayphoto.domain.repository.ItemRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetItemByIdUseCase @Inject constructor(
    private val itemRepository: ItemRepository
) {
    operator fun invoke(id: Long): Flow<Resource<Item?>> {
        return itemRepository.getItemById(id)
            .asResource()
    }
}