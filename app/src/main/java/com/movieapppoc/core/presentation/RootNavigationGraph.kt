package com.movieapppoc.core.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.movieapppoc.movielist.util.Screen

@Composable
fun RootNavigationGraph(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.rout
    ) {

        composable(
            Screen.Account.rout,
            deepLinks = Screen.Account.deepLink
        ) {
            AccountScreen()
        }

        composable(Screen.Home.rout) {
            HomeScreen(onProfileIconClick = {
                navController.navigate(Screen.Account.rout)
            }, onMovieItemClick = {
                navController.navigate(Screen.Details.rout + "/${it}")
            })
        }
        composable(
            Screen.Details.routeWithArgs,
            arguments = Screen.Details.argument,
            deepLinks = Screen.Details.deepLink
        ) { backStackEntry ->
            val movieId =
                backStackEntry.arguments?.getString(Screen.Details.movieIdArg)
        }
    }
}