package com.example.pixbayphoto.presentation.screen.detail

import com.example.pixbayphoto.domain.model.Item

data class DetailState(
    val item: Item = Item(0, "", "", "", "", ""),
    val isLoading: Boolean = false,
    val error: String? = null,
)