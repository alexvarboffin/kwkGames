package com.vai.vaidebet.vaibrazil.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.vai.vaidebet.vaibrazil.presentation.screens.home.HomeViewModel
import com.vai.vaidebet.vaibrazil.presentation.screens.splash.SplashViewModel
import com.vai.vaidebet.vaibrazil.ui.screens.game.GameScreen
import com.vai.vaidebet.vaibrazil.ui.screens.home.HomeScreen
import com.vai.vaidebet.vaibrazil.ui.screens.splash.SplashScreen
import com.vai.vaidebet.vaibrazil.presentation.screens.game.GameViewModel

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Home : Screen("home")
    object Game : Screen("game/{levelId}") {
        fun createRoute(levelId: Int) = "game/$levelId"
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        composable(Screen.Splash.route) {
            val viewModel = SplashViewModel()
            SplashScreen(
                viewModel = viewModel,
                onNavigateToHome = { navController.navigate(Screen.Home.route) }
            )
        }
        
        composable(Screen.Home.route) {
            val viewModel = HomeViewModel()
            HomeScreen(
                viewModel = viewModel,
                onLevelSelected = { levelId ->
                    navController.navigate(Screen.Game.createRoute(levelId))
                }
            )
        }
        
        composable(Screen.Game.route) { backStackEntry ->
            val levelId = backStackEntry.arguments?.getString("levelId")?.toIntOrNull() ?: 1
            val viewModel = GameViewModel(levelId)
            GameScreen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() }
            )
        }
    }
}