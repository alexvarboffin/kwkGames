package com.esportedasorte.esportefootbal

import android.os.Bundle
import android.webkit.WebView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.esportedasorte.esportefootbal.ui.theme.PenaltiKicksTheme
import com.vai.vaidebet.vaibrazil.ui.screens.home.HomeScreen
import com.vai.vaidebet.vaibrazil.utils.loadPrivacyPolicy

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        webView = WebView(this).apply {} //not set WebViewClient!!!

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
        webView.loadPrivacyPolicy("http://esporhp.top/terms4")
    }
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