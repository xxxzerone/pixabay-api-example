package com.example.pixbayphoto.data.repository

import android.content.Context
import com.example.pixbayphoto.R
import com.example.pixbayphoto.core.NetworkError
import com.example.pixbayphoto.core.Result
import com.example.pixbayphoto.data.dto.PixabayResponse
import com.example.pixbayphoto.data.mapper.toModel
import com.example.pixbayphoto.domain.model.Pixabay
import com.example.pixbayphoto.domain.repository.PixabayRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.client.plugins.ResponseException
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.serialization.SerializationException

class PixabayRepositoryImpl(
    context: Context,
    private val httpClient: HttpClient
) : PixabayRepository {
    private val apiKey = context.getString(R.string.pixabay_key)

    override suspend fun loadPhoto(query: String): Result<List<Pixabay>, NetworkError> {
        return try {
            val response: PixabayResponse = httpClient.get {
                parameter("key", apiKey)
                parameter("q", query)
                parameter("image_type", "photo")
            }.body()

            val pixabays = response.hits?.map { it.toModel() } ?: emptyList()

            Result.Success(pixabays)
        } catch (_: UnresolvedAddressException) {
            // 인터넷 연결 끊김
            Result.Failure(NetworkError.NetworkUnavailable)
        } catch (_: HttpRequestTimeoutException) {
            // 타임 아웃
            Result.Failure(NetworkError.Timeout)
        } catch (e: ResponseException) {
            // 3xx, 4xx, 5xx 에러
            Result.Failure(NetworkError.HttpError(e.response.status.value))
        } catch (_: SerializationException) {
            Result.Failure(NetworkError.ParseError)
        } catch (e: Exception) {
            Result.Failure(NetworkError.Unknown(e.message ?: "알 수 없는 오류입니다."))
        }
    }
}
