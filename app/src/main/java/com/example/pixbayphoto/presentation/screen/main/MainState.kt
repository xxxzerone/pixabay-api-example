package com.example.pixbayphoto.presentation.screen.main

import com.example.pixbayphoto.domain.model.Item

data class MainState(
    val items: List<Item> = emptyList(),
    val query: String = "",
    val isLoading: Boolean = false,
    val error: String? = null
)