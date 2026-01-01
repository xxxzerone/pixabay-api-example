package com.example.pixbayphoto.ui.screen.main

sealed interface MainEvent {
    data class NavigateToDetail(val id: Int) : MainEvent
}
