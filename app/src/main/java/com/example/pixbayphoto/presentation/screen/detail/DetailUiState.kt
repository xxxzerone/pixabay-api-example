package com.example.pixbayphoto.presentation.screen.detail

import com.example.pixbayphoto.domain.model.Item

data class DetailUiState(
    val item: Item? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
