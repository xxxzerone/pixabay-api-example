package com.example.pixbayphoto.presentation.screen.main

sealed interface MainAction {
    data class OnValueChange(val query: String) : MainAction

    data class OnItemClick(val id: Long) : MainAction
    data class OnSearchAction(val query: String) : MainAction
}