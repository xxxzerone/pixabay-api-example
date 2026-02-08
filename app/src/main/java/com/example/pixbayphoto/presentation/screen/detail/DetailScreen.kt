package com.example.pixbayphoto.presentation.screen.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun DetailScreen(
    uiState: DetailUiState,
    onAction: (DetailAction) -> Unit,
    modifier: Modifier = Modifier
) {
    Text("Detail Screen")
}

@Preview(showBackground = true)
@Composable
private fun DetailScreenPreview() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        DetailScreen(
            uiState = DetailUiState(),
            onAction = {},
        )
    }
}