package com.example.pixbayphoto.core

sealed class Result<out T, out E> {
    data class Success<out T>(val data: T): Result<T, Nothing>()
    data class Failure<out E>(val message: E): Result<Nothing, E>()
}
