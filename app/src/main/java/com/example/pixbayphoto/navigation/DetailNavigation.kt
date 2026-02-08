package com.example.pixbayphoto.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.pixbayphoto.presentation.screen.detail.DetailRoot

fun NavGraphBuilder.detailRoot(onBack: () -> Unit) {
    composable<Route.Detail> {
        DetailRoot(onBack = onBack)
    }
}