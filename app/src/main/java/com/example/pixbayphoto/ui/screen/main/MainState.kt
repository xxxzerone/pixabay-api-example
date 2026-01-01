package com.example.pixbayphoto.ui.screen.main

import com.example.pixbayphoto.domain.Pixabay

data class MainState(
    val pixabays: List<Pixabay> = emptyList(),
    val query: String = "",
    val isLoading: Boolean = true,
    val error: String? = null
)
