package com.example.pixbayphoto.ui.screen.main

sealed interface MainAction {
    data class OnPhotoClick(val id: Int) : MainAction
    data class OnValueChange(val query: String) : MainAction
    data class OnSearchAction(val query: String) : MainAction
}
