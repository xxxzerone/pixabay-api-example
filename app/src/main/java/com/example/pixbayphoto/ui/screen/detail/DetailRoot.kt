package com.example.pixbayphoto.ui.screen.detail

import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun DetailRoot(
    id: Int,
    viewModel: DetailViewModel = koinViewModel { parametersOf(id) },
) {
    val state = viewModel.state.collectAsStateWithLifecycle()

    DetailScreen(
        state = state.value
    )
}
