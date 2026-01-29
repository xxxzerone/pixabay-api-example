package com.example.pixbayphoto.data.datasource

import com.example.pixbayphoto.data.dto.ItemResponse
import com.example.pixbayphoto.domain.common.Response
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.isSuccess
import io.ktor.util.toMap

class DefaultDataSource(
    private val httpClient: HttpClient,
    private val apiKey: String,
) : DataSource {
    private val baseUrl = "https://pixabay.com/api/"

    override suspend fun fetchQueryItems(query: String): Response<ItemResponse> {
        return try {
            val httpResponse = httpClient.get(baseUrl) {
                parameter("key", apiKey)
                parameter("q", query)
                parameter("image_type", "photo")
            }

            Response(
                headers = httpResponse.headers.toMap(),
                statusCode = httpResponse.status.value,
                body = if (httpResponse.status.isSuccess()) httpResponse.body() else null
            )
        } catch (e: Exception) {
            Response(headers = emptyMap(), statusCode = -1, body = null)
        }
    }

    override suspend fun fetchItemById(id: Long): Response<ItemResponse> {
        return try {
            val httpResponse = httpClient.get(baseUrl) {
                parameter("key", apiKey)
                parameter("id", id)
                parameter("image_type", "photo")
            }

            Response(
                headers = httpResponse.headers.toMap(),
                statusCode = httpResponse.status.value,
                body = if (httpResponse.status.isSuccess()) httpResponse.body() else null
            )
        } catch (e: Exception) {
            Response(headers = emptyMap(), statusCode = -1, body = null)
        }
    }
}