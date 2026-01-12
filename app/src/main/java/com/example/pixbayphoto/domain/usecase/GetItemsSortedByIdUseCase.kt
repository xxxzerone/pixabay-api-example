package com.example.pixbayphoto.domain.usecase

import com.example.pixbayphoto.domain.model.Item
import com.example.pixbayphoto.domain.repository.ItemRepository
import kotlinx.coroutines.flow.Flow

class GetItemsSortedByIdUseCase(private val repository: ItemRepository) {
    operator fun invoke(): Flow<List<Item>> {
        return repository.getItemsSortedById()
    }
}