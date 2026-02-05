package com.example.pixbayphoto.domain.usecase

import com.example.pixbayphoto.core.Resource
import com.example.pixbayphoto.core.asResource
import com.example.pixbayphoto.domain.model.Item
import com.example.pixbayphoto.domain.repository.ItemRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetItemsSortedByIdUseCase @Inject constructor(
    private val itemRepository: ItemRepository
) {
    operator fun invoke(query: String): Flow<Resource<List<Item>>> {
        return itemRepository.getItemsSortedById(query)
            .asResource()
    }
}