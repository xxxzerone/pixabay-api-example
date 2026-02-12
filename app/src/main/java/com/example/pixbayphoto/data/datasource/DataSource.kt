package com.example.pixbayphoto.data.datasource

import com.example.pixbayphoto.core.Response
import com.example.pixbayphoto.data.dto.ItemResponse

interface DataSource {
    suspend fun fetchQueryItems(query: String): Response<ItemResponse>
    suspend fun fetchItemById(id: Long): Response<ItemResponse>
}