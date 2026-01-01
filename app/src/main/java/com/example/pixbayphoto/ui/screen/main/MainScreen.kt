package com.example.pixbayphoto.ui.screen.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pixbayphoto.ui.component.PhotoCard
import com.example.pixbayphoto.ui.component.SearchInputField

@Composable
fun MainScreen(
    state: MainState,
    modifier: Modifier = Modifier,
    onAction: (MainAction) -> Unit = {},
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        SearchInputField(
            text = state.query,
            placeholder = "Search",
            onValueChange = { onAction(MainAction.OnValueChange(it)) },
            onSearchAction = { onAction(MainAction.OnSearchAction(it)) }
        )

        Spacer(Modifier.height(20.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            items(state.pixabays) { pixabay ->
                PhotoCard(
                    pixabay = pixabay,
                    onAction = onAction
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun MainScreenPreview() {
    MainScreen(
        state = MainState()
    )
}