package com.example.pixbayphoto.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.pixbayphoto.presentation.screen.detail.DetailRoot
import com.example.pixbayphoto.presentation.screen.main.MainRoot

@Composable
fun NavGraph(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Route.Main,
        modifier = modifier
    ) {
        composable<Route.Main> {
            MainRoot(
                navigateToDetail = { id ->
                    navController.navigate(Route.Detail(id))
                }
            )
        }

        composable<Route.Detail> { backStackEntry ->
            val detailRoute: Route.Detail = backStackEntry.toRoute()
            DetailRoot(
                itemId = detailRoute.itemId,
                onBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}
