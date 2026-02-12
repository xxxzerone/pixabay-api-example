package com.example.pixbayphoto.data.datasource

import com.example.pixbayphoto.core.Response
import com.example.pixbayphoto.data.dto.ItemResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.parameter
import io.ktor.http.isSuccess
import io.ktor.util.toMap
import jakarta.inject.Inject

class RemoteDataSource @Inject constructor(
    private val apiKey: String,
    private val httpClient: HttpClient
) : DataSource {
    private val baseUrl = "https://pixabay.com/api/"

    override suspend fun fetchQueryItems(query: String): Response<ItemResponse> {
        return request {
            parameter("q", query)
        }
    }

    override suspend fun fetchItemById(id: Long): Response<ItemResponse> {
        return request {
            parameter("id", id)
        }
    }

    private suspend fun request(block: HttpRequestBuilder.() -> Unit): Response<ItemResponse> {
        val httpResponse = httpClient.get(baseUrl) {
            parameter("key", apiKey)
            parameter("image_type", "photo")
            block()
        }

        return Response(
            headers = httpResponse.headers.toMap(),
            statusCode = httpResponse.status.value,
            body = if (httpResponse.status.isSuccess()) httpResponse.body() else null
        )
    }
}