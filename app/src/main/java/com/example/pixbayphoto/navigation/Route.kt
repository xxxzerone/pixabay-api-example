package com.example.pixbayphoto.navigation

import kotlinx.serialization.Serializable

sealed interface Route {
    @Serializable
    data object Main : Route
    @Serializable
    data class Detail(val itemId: Long) : Route
}