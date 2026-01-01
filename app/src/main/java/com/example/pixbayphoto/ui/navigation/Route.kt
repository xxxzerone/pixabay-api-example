package com.example.pixbayphoto.ui.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

interface Route : NavKey {
    @Serializable
    data object Main : Route

    @Serializable
    data class Detail(val id: Int) : Route
}
