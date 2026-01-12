package com.example.pixbayphoto.domain.repository

import com.example.pixbayphoto.domain.model.Item
import kotlinx.coroutines.flow.Flow

interface ItemRepository {
    fun getItemsSortedById(): Flow<List<Item>>
    fun getItemById(id: Long): Flow<Item>
}