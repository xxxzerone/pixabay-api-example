package com.example.pixbayphoto.presentation.screen.main

sealed interface MainEvent {
    data class OnNavigateToDetail(val id: Long) : MainEvent
}