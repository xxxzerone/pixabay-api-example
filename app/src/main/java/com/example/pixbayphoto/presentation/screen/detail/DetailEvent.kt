package com.example.pixbayphoto.presentation.screen.detail

sealed interface DetailEvent {
    data object NavigateToBack : DetailEvent
}