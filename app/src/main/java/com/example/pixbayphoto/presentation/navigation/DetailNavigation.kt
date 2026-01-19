package com.example.pixbayphoto.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.pixbayphoto.presentation.screen.detail.DetailRoot

fun NavGraphBuilder.detailScreen(onBack: () -> Unit) {
    composable<Route.Detail> { backStackEntry ->
        val detail: Route.Detail = backStackEntry.toRoute()
        DetailRoot(
            itemId = detail.itemId,
            onBack = onBack
        )
    }
}