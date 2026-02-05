package com.example.pixbayphoto.core

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

sealed class Resource<out T> {
    data class Success<out T>(val data: T) : Resource<T>()
    data class Failure(
        val message: String? = null,
        val throwable: Throwable? = null
    ) : Resource<Nothing>()
    data object Loading : Resource<Nothing>()
}

inline fun <T, R> Resource<T>.fold(
    onSuccess: (T) -> R,
    onFailure: (String?, Throwable?) -> R,
    onLoading: () -> R
): R {
    return when (this) {
        is Resource.Success<T> -> onSuccess(data)
        is Resource.Failure -> onFailure(message, throwable)
        Resource.Loading -> onLoading()
    }
}

fun <T> Flow<T>.asResource(): Flow<Resource<T>> {
    return this
        .map<T, Resource<T>> { data ->
            Resource.Success(data)
        }
        .onStart { emit(Resource.Loading) }
        .catch { e ->
            emit(Resource.Failure(e.message ?: "Unknown Error", e))
        }
}