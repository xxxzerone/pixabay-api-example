package com.example.pixbayphoto.presentation.screen.main

sealed interface MainEvent {
    data class ShowSnackbar(val message: String) : MainEvent
    data class NavigateToDetail(val id: Long) : MainEvent
}