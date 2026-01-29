package com.example.pixbayphoto.domain.common

/**
 * T: 데이터의 타입
 * E: 에러 발생 시 전달할 커스텀 에러 타입 (기본은 Throwable)
 */
sealed class Resource<out T> {

    data object Loading : Resource<Nothing>()
    data class Success<out T>(val data: T) : Resource<T>()
    data class Error(
        val message: String? = null,
        val throwable: Throwable? = null,
    ) : Resource<Nothing>()
}

inline fun <T> Resource<T>.onSuccess(action: (T) -> Unit): Resource<T> {
    if (this is Resource.Success) action(data)
    return this
}

inline fun <T> Resource<T>.onError(action: (String?, Throwable?) -> Unit): Resource<T> {
    if (this is Resource.Error) action(message, throwable)
    return this
}

inline fun <T, R> Resource<T>.fold(
    onSuccess: (T) -> R,
    onError: (String?, Throwable?) -> R,
    onLoading: () -> R
): R {
    return when (this) {
        is Resource.Success<T> -> onSuccess(data)
        is Resource.Error -> onError(message, throwable)
        Resource.Loading -> onLoading()
    }
}