package com.example.pixbayphoto.presentation.screen.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pixbayphoto.presentation.component.ErrorMessage
import com.example.pixbayphoto.presentation.component.ItemCard
import com.example.pixbayphoto.presentation.component.SearchInput

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    state: MainState,
    modifier: Modifier = Modifier,
) {
    PullToRefreshBox(
        isRefreshing = state.isLoading,
        onRefresh = {},
        modifier = modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(20.dp)
        ) {
            SearchInput(value = state.query, label = "Search", onValueChange = {})

            Spacer(Modifier.height(20.dp))

            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                when {
                    // 로딩 상태 (데이터가 없을 때만 보여주거나, 전체 화면 덮기)
                    state.isLoading && state.items.isEmpty() -> {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    }

                    // 에러 상태
                    state.error != null -> {
                        ErrorMessage(
                            message = state.error,
                            onRetry = { /* 다시 시도 로직 */ },
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }

                    // 빈 데이터 상태
                    !state.isLoading && state.items.isEmpty() -> {
                        Text(
                            text = "검색 결과가 없습니다.",
                            modifier = Modifier.align(Alignment.Center),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }

                    // 성공 상태
                    else -> {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            modifier = Modifier.fillMaxSize(),
                            horizontalArrangement = Arrangement.spacedBy(15.dp),
                            verticalArrangement = Arrangement.spacedBy(15.dp),
                            contentPadding = PaddingValues(bottom = 20.dp)
                        ) {
                            items(state.items, key = { it.id }) { item ->
                                ItemCard(item = item)
                            }
                        }
                    }
                }

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