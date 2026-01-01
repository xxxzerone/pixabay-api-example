package com.example.pixbayphoto.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.example.pixbayphoto.ui.navigation.Route.*
import com.example.pixbayphoto.ui.screen.detail.DetailRoot
import com.example.pixbayphoto.ui.screen.main.MainRoot

@Composable
fun NavGraph(modifier: Modifier = Modifier) {
    val backStack = rememberNavBackStack(Main)

    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        modifier = modifier,
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator()
        ),
        entryProvider = entryProvider {
            entry<Main> {
                MainRoot(
                    onNavigateToDetail = { id ->
                        backStack.add(Detail(id))
                    }
                )
            }
            entry<Detail> { key ->
                DetailRoot(key.id)
            }
        }
    )
}