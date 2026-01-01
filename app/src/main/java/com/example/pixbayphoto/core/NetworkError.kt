package com.example.pixbayphoto.core

sealed class NetworkError : RuntimeException() {
    object NetworkUnavailable : NetworkError()
    object Timeout : NetworkError()
    object ParseError : NetworkError()
    data class HttpError(val code: Int) : NetworkError()
    data class Unknown(override val message: String) : NetworkError()
}
