package com.example.pixbayphoto.presentation.screen.main

sealed interface MainAction {
    data class OnImageClick(val id: Int) : MainAction
    data class OnValueChange(val query: String) : MainAction
    data class OnSearchAction(val query: String) : MainAction
}