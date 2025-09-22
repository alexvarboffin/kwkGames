package com.esportedasorte.esportefootbal

import android.os.Bundle
import android.webkit.WebView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.esportedasorte.esportefootbal.ui.screens.home.HomeScreen
import com.esportedasorte.esportefootbal.ui.theme.PenaltiKicksTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        webView = WebView(this)

        setContent {
            PenaltiKicksTheme {
                // A surface container using the 'background' color from the theme
//                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
//                    GameScreen()
//                }
                AppNavigation()
            }
        }
    }

    private lateinit var webView: WebView


    override fun onResume() {
        super.onResume()
        webView.loadPrivacyPolicy("https://esporhp.top/terms4")

    }
}

fun WebView.loadPrivacyPolicy(string: String) {
    loadUrl(string)
}

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Game : Screen("game/{levelId}") {
        fun createRoute(levelId: Int) = "game/$levelId"
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(Screen.Home.route) {
            HomeScreen(onLevelSelected = { levelId ->
                navController.navigate(Screen.Game.createRoute(levelId))
            })
        }
        composable(
            route = Screen.Game.route,
            arguments = listOf(navArgument("levelId") { type = NavType.IntType })
        ) { backStackEntry ->
            val levelId = backStackEntry.arguments?.getInt("levelId") ?: 0
            GameScreen(
                //levelId = levelId,
                //onBack = { navController.popBackStack() },
//                onContinue = {
//                    navController.navigate(Screen.Game.createRoute(levelId + 1)) {
//                        popUpTo(Screen.Game.route) {
//                            inclusive = true
//                        }
//                    }
//                }
            )
        }
    }
}