package com.example.pixbayphoto.presentation.screen.detail

sealed interface DetailAction {
    data object OnRetry : DetailAction
}