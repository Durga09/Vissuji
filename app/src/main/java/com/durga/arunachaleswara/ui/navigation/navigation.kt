package com.durga.arunachaleswara.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.durga.arunachaleswara.ui.screen.EditUserScreen
import com.durga.arunachaleswara.ui.screen.SplashScreen
import com.durga.arunachaleswara.ui.screen.UserDetailsScreen
import com.durga.arunachaleswara.ui.screen.UserFormScreen
import com.durga.arunachaleswara.ui.screen.UserListScreen
import com.durga.arunachaleswara.viewmodel.UserViewModel

@Composable
fun AppNavigation(userViewModel: UserViewModel) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "splash"
    ) {
        composable("splash") {
            SplashScreen(
                onSplashFinished = {
                    navController.navigate("user_list") {
                        popUpTo("splash") { inclusive = true }
                    }
                }
            )
        }

        composable("user_list") {
            UserListScreen(
                viewModel = userViewModel,
                onAddClick = { navController.navigate("user_form") },
                onUserClick = { userId ->
                    navController.navigate("user_details/$userId")
                }
            )
        }

        composable("user_form") {
            UserFormScreen(
                viewModel = userViewModel,
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(
            route = "user_details/{userId}"
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId") ?: ""

            UserDetailsScreen(
                viewModel = userViewModel,
                userId = userId,
                onBackClick = { navController.popBackStack() },
                onEditClick = { id ->
                    navController.navigate("edit_user/$id")
                }
            )
        }

        composable(
            route = "edit_user/{userId}"
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId") ?: ""

            EditUserScreen (
                viewModel = userViewModel,
                userId = userId,
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}