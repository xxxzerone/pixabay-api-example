package com.example.pixbayphoto.presentation.screen.main

import com.example.pixbayphoto.domain.model.Item

data class MainUiState(
    val items: List<Item> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val query: String = ""
)
