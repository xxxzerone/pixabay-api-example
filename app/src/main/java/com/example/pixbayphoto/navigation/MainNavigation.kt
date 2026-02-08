package com.example.pixbayphoto.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.pixbayphoto.presentation.screen.main.MainRoot

fun NavGraphBuilder.mainRoot(navigateToDetail: (Long) -> Unit) {
    composable<Route.Main> {
        MainRoot(onNavigateToDetail = navigateToDetail)
    }
}