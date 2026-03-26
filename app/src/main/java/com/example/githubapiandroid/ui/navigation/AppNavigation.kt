package com.example.githubapiandroid.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.feature.pulls.iu.compose.PullsScreen
import com.example.feature.pulls.iu.viewmodel.PullsViewModel
import com.example.feature.repos.ui.compose.ReposScreen
import com.example.feature.repos.ui.viewmodel.ReposViewModel

@Composable
fun AppNavigation(navController: NavHostController = rememberNavController()) {

    NavHost(navController = navController, startDestination = "repos") {
        composable("repos") {
            val viewModel = hiltViewModel<ReposViewModel>()
            ReposScreen(viewModel = viewModel, navController)
        }
        composable("pulls/{owner}/{repo}") { backStackEntry ->
            val viewModel = hiltViewModel<PullsViewModel>()
            PullsScreen(
                navController = navController,
                viewModel = viewModel,
                owner = backStackEntry.arguments?.getString("owner") ?: "",
                repo = backStackEntry.arguments?.getString("repo") ?: ""
            )
        }
    }
}