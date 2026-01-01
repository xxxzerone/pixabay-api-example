package com.example.pixbayphoto.ui.screen.main

import com.example.pixbayphoto.domain.model.Pixabay

data class MainState(
    val pixabays: List<Pixabay> = emptyList(),
    val query: String = "",
    val isLoading: Boolean = false,
    val error: String? = null
)
