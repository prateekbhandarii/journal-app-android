package com.pb.audia.app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.pb.audia.memo.presentation.list_screen.MemoListScreenRoot
import com.pb.audia.memo.presentation.utils.toCreateMemoRoute

@Composable
fun NavigationRoot(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = NavigationRoute.Memos
    ) {
        composable<NavigationRoute.Memos> {
            MemoListScreenRoot(
                onNavigateToCreateMemo = { details ->
                    navController.navigate(details.toCreateMemoRoute())
                }
            )
        }
        composable<NavigationRoute.CreateMemo> {

        }
    }
}