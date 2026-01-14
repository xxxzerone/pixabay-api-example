package com.example.pixbayphoto.domain.usecase

import com.example.pixbayphoto.domain.common.Resource
import com.example.pixbayphoto.domain.model.Item
import com.example.pixbayphoto.domain.repository.ItemRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetItemsSortedByIdUseCase @Inject constructor(
    private val repository: ItemRepository,
) {

    operator fun invoke(): Flow<Resource<List<Item>>> {
        return repository.getItemsSortedById()
    }
}