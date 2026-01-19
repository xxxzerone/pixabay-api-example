package com.example.pixbayphoto.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.pixbayphoto.presentation.screen.main.MainRoot

fun NavGraphBuilder.mainScreen(navigateToDetail: (Long) -> Unit) {
    composable<Route.Main> {
        MainRoot(navigateToDetail = navigateToDetail)
    }
}