package com.example.pixbayphoto.data.datasource

import com.example.pixbayphoto.data.dto.ItemResponse
import com.example.pixbayphoto.domain.common.Response

interface DataSource {
    suspend fun fetchItems(query: String): Response<ItemResponse>
}