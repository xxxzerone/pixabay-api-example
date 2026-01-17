package com.example.pixbayphoto.domain.repository

import com.example.pixbayphoto.domain.common.Resource
import com.example.pixbayphoto.domain.model.Item
import kotlinx.coroutines.flow.Flow

interface ItemRepository {
    fun getItemsSortedById(query: String): Flow<Resource<List<Item>>>
    fun getItemById(id: Long): Flow<Resource<Item>>
}