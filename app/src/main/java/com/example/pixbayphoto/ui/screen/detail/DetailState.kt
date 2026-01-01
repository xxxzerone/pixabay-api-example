package com.example.pixbayphoto.ui.screen.detail

import com.example.pixbayphoto.domain.model.Pixabay

data class DetailState(
    val pixabay: Pixabay? = Pixabay(id = 0, user = "", tags = "", previewURL = ""),
    val isLoading: Boolean = false,
    val error: String? = null
)
