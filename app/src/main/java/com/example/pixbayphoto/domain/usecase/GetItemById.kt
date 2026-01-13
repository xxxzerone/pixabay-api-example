package com.example.pixbayphoto.domain.usecase

import com.example.pixbayphoto.domain.model.Item
import com.example.pixbayphoto.domain.repository.ItemRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetItemById @Inject constructor(
    private val repository: ItemRepository
) {

    operator fun invoke(id: Long): Flow<Item?> {
        return repository.getItemById(id)
    }
}