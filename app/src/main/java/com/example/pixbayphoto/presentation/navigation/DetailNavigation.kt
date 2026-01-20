package com.example.pixbayphoto.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.pixbayphoto.presentation.screen.detail.DetailRoot

fun NavGraphBuilder.detailScreen(onBack: () -> Unit) {
    composable<Route.Detail> {
        DetailRoot(
            onBack = onBack
        )
    }
}