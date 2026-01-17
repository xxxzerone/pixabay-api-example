package com.example.pixbayphoto.presentation.screen.main

sealed interface MainAction {
    data class OnImageClick(val id: Long) : MainAction
    data class OnSearchAction(val query: String) : MainAction
    data class OnValueChange(val query: String) : MainAction
    data class OnRefresh(val query: String) : MainAction
    data class OnRetry(val query: String) : MainAction
}