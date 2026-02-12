package com.example.pixbayphoto.presentation.screen.detail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun DetailRoot(
    onBack: () -> Unit = {},
    viewModel: DetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                DetailEvent.NavigateToBack -> onBack()
            }
        }
    }

    DetailScreen(
        uiState = uiState,
        onAction = viewModel::onAction
    )
}