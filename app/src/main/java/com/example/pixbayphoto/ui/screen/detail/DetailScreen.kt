package com.example.pixbayphoto.ui.screen.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter

@Composable
fun DetailScreen(
    state: DetailState,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        state.pixabay?.let {
            Image(
                painter = rememberAsyncImagePainter(state.pixabay.previewURL),
                contentDescription = "Photo",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(350.dp)
            )

            Spacer(Modifier.height(20.dp))

            Column(
                modifier = Modifier
                    .padding(horizontal = 30.dp)
            ) {
                Text(
                    text = "user: ${state.pixabay.user}"
                )
                Spacer(Modifier.height(10.dp))
                Text(
                    text = "tags: ${state.pixabay.tags}"
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun DetailScreenPreview() {
    DetailScreen(
        state = DetailState()
    )
}