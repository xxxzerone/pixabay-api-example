package com.example.pixbayphoto.ui.screen.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun MainRoot(
    onNavigateToDetail: (Int) -> Unit = {},
    viewModel: MainViewModel = koinViewModel()
) {
    val state = viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(viewModel.event) {
        viewModel.event.collect { event ->
            when (event) {
                is MainEvent.NavigateToDetail -> onNavigateToDetail(event.id)
            }
        }
    }

    MainScreen(
        state = state.value,
        onAction = viewModel::handleActon
    )
}