package most.vai.casi.reta

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.walhalla.sdk.utils.openInCustomTab

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.MainMenu.route) {
        composable(Screen.MainMenu.route) {
            val context = LocalContext.current
            MainScreen(
                onCareerModeClick = { navController.navigate(Screen.LevelSelect.route) },
                onEndlessModeClick = { navController.navigate(Screen.GameEndless.route) },
//                onPrivacyClick = { openUrlInCustomTab(context, "https://jojoapp.site/Privacy") },
//                onFaqClick = { openUrlInCustomTab(context, "https://jojoapp.site/FAQ") }

                onPrivacyClick = { openInCustomTab(context, Cfg.privacy) },
                onFaqClick = { openInCustomTab(context, Cfg.faq) }


            )
        }
        composable(Screen.LevelSelect.route) {
            LevelScreen(
                onLevelClick = { level -> navController.navigate(Screen.GameCareer.createRoute(level)) }
            )
        }
        composable(
            route = Screen.GameCareer.route,
            arguments = listOf(navArgument("level") { type = NavType.Companion.IntType })
        ) { backStackEntry ->
            val level = backStackEntry.arguments?.getInt("level") ?: 1
            GameScreen(
                isEndlessMode = false,
                level = level,
                onBack = { navController.popBackStack() }
            )
        }
        composable(Screen.GameEndless.route) {
            GameScreen(
                isEndlessMode = true,
                level = 1,
                onBack = { navController.popBackStack() }
            )
        }
    }
}