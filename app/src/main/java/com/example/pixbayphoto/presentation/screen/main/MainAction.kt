package com.example.pixbayphoto.presentation.screen.main

sealed interface MainAction {
    data class OnItemClick(val id: Long) : MainAction
}