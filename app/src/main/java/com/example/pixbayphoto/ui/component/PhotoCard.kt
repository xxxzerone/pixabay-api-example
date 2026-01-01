package com.example.pixbayphoto.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.pixbayphoto.domain.model.Pixabay
import com.example.pixbayphoto.ui.screen.main.MainAction

@Composable
fun PhotoCard(
    pixabay: Pixabay,
    modifier: Modifier = Modifier,
    onAction: (MainAction) -> Unit = {},
) {
    Card(
        onClick = { onAction(MainAction.OnPhotoClick(pixabay.id)) },
        modifier = modifier
            .size(160.dp),
        shape = RoundedCornerShape(10.dp)
    ) {
        Image(
            painter = rememberAsyncImagePainter(pixabay.previewURL),
            contentDescription = "Preview Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun PhotoCardPreview() {
    PhotoCard(
        pixabay = Pixabay(0, "", "", "")
    )
}