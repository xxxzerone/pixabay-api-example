package com.example.pixbayphoto.data.repository

import android.content.Context
import com.example.pixbayphoto.R
import com.example.pixbayphoto.core.NetworkError
import com.example.pixbayphoto.core.Result
import com.example.pixbayphoto.data.api.PixabayApi
import com.example.pixbayphoto.data.mapper.toModel
import com.example.pixbayphoto.domain.model.Pixabay
import com.example.pixbayphoto.domain.repository.PixabayRepository
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.serialization.SerializationException
import okio.IOException

class PixabayRepositoryImpl(
    context: Context,
    private val pixabayApi: PixabayApi,
) : PixabayRepository {
    private val apiKey = context.getString(R.string.pixabay_key)

    override suspend fun loadPhoto(query: String): Result<List<Pixabay>, NetworkError> {
        return try {
            val response = pixabayApi.loadPixabayData(key = apiKey, q = query, type = "photo")
            val pixabays = response.hits?.map {
                it.toModel()
            } ?: emptyList()

            Result.Success(pixabays)
        } catch (_: IOException) {
            Result.Failure(NetworkError.NetworkUnavailable)
        } catch (_: TimeoutCancellationException) {
            Result.Failure(NetworkError.Timeout)
        } catch (_: SerializationException) {
            Result.Failure(NetworkError.ParseError)
        } catch (e: retrofit2.HttpException) {
            Result.Failure(NetworkError.HttpError(e.code()))
        } catch (e: Exception) {
            Result.Failure(NetworkError.Unknown(e.message ?: "알 수 없는 오류입니다."))
        }
    }
}
