package com.example.pixbayphoto.data.repository

import com.example.pixbayphoto.core.isSuccess
import com.example.pixbayphoto.data.datasource.DataSource
import com.example.pixbayphoto.data.mapper.ItemMapper
import com.example.pixbayphoto.domain.model.Item
import com.example.pixbayphoto.domain.repository.ItemRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ItemRepositoryImpl @Inject constructor(
    private val dataSource: DataSource,
    private val itemMapper: ItemMapper
) : ItemRepository {
    override fun getItemsSortedById(query: String): Flow<List<Item>> = flow {
        val response = dataSource.fetchQueryItems(query)
        if (response.isSuccess()) {
            val items = response.body?.let { itemMapper.toDomainList(it) }
                ?.sortedByDescending { it.id }
                ?: emptyList()
            emit(items)
        } else {
            throw RuntimeException("Failed to fetch items (status: ${response.statusCode})")
        }
    }

    override fun getItemById(id: Long): Flow<Item?> = flow {
        val response = dataSource.fetchItemById(id)
        if (response.isSuccess()) {
            emit(response.body?.hits?.firstOrNull()?.let { itemMapper.toDomain(it) })
        } else {
            throw RuntimeException("Failed to fetch item (status: ${response.statusCode})")
        }
    }
}