package com.sv.speergithub.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.sv.speergithub.ui.screen.ConnectionsScreen
import com.sv.speergithub.ui.screen.SearchScreen
import com.sv.speergithub.ui.screen.UserProfileScreen
import com.sv.speergithub.ui.viewmodel.ConnectionsViewModel
import com.sv.speergithub.ui.viewmodel.UserViewModel

@Composable
fun AppNavGraph(navController: NavHostController) {
    val userViewModel: UserViewModel = hiltViewModel()
    val connectionsViewModel: ConnectionsViewModel = hiltViewModel()

    NavHost(navController, startDestination = "search", modifier = Modifier.padding(top = 32.dp)) {
        composable("search") {
            SearchScreen(navController, userViewModel)
        }

        composable(Screen.Profile.route) { backStackEntry ->
            val username = backStackEntry.arguments?.getString("username") ?: return@composable
            UserProfileScreen(viewModel = userViewModel, username = username, navController = navController)
        }

        composable(Screen.Connections.route) { backStackEntry ->
            val username = backStackEntry.arguments?.getString("username") ?: return@composable
            val type = backStackEntry.arguments?.getString("type") ?: "followers"
            ConnectionsScreen(navController, username, type, connectionsViewModel)
        }
    }
}
