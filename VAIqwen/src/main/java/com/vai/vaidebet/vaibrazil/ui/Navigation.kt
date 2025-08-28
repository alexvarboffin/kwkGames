package com.vai.vaidebet.vaibrazil.ui

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.vai.vaidebet.vaibrazil.presentation.screens.game.GameViewModel
import com.vai.vaidebet.vaibrazil.presentation.screens.home.HomeViewModel
import com.vai.vaidebet.vaibrazil.ui.screens.game.GameScreen
import com.vai.vaidebet.vaibrazil.ui.screens.home.HomeScreen
import com.vai.vaidebet.vaibrazil.ui.screens.splash.SplashScreen
import org.koin.androidx.compose.getViewModel

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    
    NavHost(
        navController = navController,
        startDestination = "splash"
    ) {
        composable("splash") {
            SplashScreen(navController)
        }
        
        composable("home") {
            val viewModel: HomeViewModel = getViewModel()
            val uiState = viewModel.uiState.collectAsState()
            
            HomeScreen(
                uiState = uiState.value,
                navController = navController
            )
        }
        
        composable("game/{levelId}") { backStackEntry ->
            val levelId = backStackEntry.arguments?.getString("levelId")?.toIntOrNull()
            if (levelId != null) {
                val viewModel: GameViewModel = getViewModel(parameters = { parametersOf(levelId) })
                val uiState = viewModel.uiState.collectAsState()
                
                GameScreen(
                    uiState = uiState.value,
                    onMoveBlock = { blockId, newRow, newCol ->
                        viewModel.moveBlock(blockId, newRow, newCol)
                    },
                    onBack = { navController.popBackStack() }
                )
            }
        }
    }
}