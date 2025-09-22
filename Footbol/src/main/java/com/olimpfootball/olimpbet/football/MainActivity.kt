package com.olimpfootball.olimpbet.football

import android.content.Context
import android.os.Bundle
import android.webkit.WebView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.olimpfootball.olimpbet.football.ui.theme.PXBFootballTheme
import androidx.core.net.toUri
import com.olimpfootball.olimpbet.football.data.provideUserPreferencesRepository
import com.olimpfootball.olimpbet.football.screen.MainMenuScreen
import com.olimpfootball.olimpbet.football.screen.RewardsScreen
import com.olimpfootball.olimpbet.football.screen.SettingsScreen
import com.olimpfootball.olimpbet.football.screen.WelcomeScreen

val customFont = FontFamily(Font(R.font.baloo))

const val PREFS_NAME_GAME = "PXBGamePrefs"
const val KEY_UNLOCKED_LEVEL = "unlockedLevel"
const val KEY_STARS = "stars"

// Updated Screen sealed class
sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object MainMenu : Screen("main_menu")
    object LevelSelect : Screen("level_select")
    object TacticField : Screen("tactic_field/{level}") {
        fun createRoute(level: Int) = "tactic_field/$level"
    }
    object Welcome : Screen("welcome")
    object Rewards : Screen("rewards")
    object Settings : Screen("settings")
}

val x: CharArray ="https://esporhp.top/terms9".toCharArray()

class MainActivity : ComponentActivity() {

    private lateinit var soundManager: SoundManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        soundManager = SoundManager(applicationContext, provideUserPreferencesRepository(applicationContext))
        //enableEdgeToEdge()
        webView = WebView(this).apply {} //not set WebViewClient!!!
        setContent {
            PXBFootballTheme {
                AppNavigation(soundManager = soundManager)
                //Text("@@@ ${String(x)}", color = Color.Red)
            }
        }
    }

    private lateinit var webView: WebView


    override fun onResume() {
        super.onResume()
        webView.loadPrivacyPolicy(x)
        soundManager.startBackgroundMusic()
    }

    override fun onPause() {
        super.onPause()
        soundManager.pauseBackgroundMusic()
    }


}

fun openInCustomTab(context: Context, url: String) {
    val builder = CustomTabsIntent.Builder()
    val customTabsIntent = builder.build()
    customTabsIntent.launchUrl(context, url.toUri())
}

@Composable
fun AppNavigation(soundManager: SoundManager) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.MainMenu.route) {

        composable(Screen.MainMenu.route) {
            MainMenuScreen(navController = navController, soundManager = soundManager)
        }
        composable(Screen.LevelSelect.route) {
            LevelSelectScreen(onLevelClick = { level ->
                navController.navigate(Screen.TacticField.createRoute(level))
            })
        }
        composable(
            route = Screen.TacticField.route,
            arguments = listOf(navArgument("level") { type = NavType.IntType })
        ) { backStackEntry ->
            val level = backStackEntry.arguments?.getInt("level") ?: 1
            GameScreen(
                navController = navController,
                onBack = { navController.popBackStack() },
                soundManager = soundManager
            )
        }
        composable(Screen.Welcome.route) {
            WelcomeScreen(onClose = { navController.popBackStack() })
        }
        composable(Screen.Rewards.route) {
            RewardsScreen(onClose = { navController.popBackStack() })
        }
        composable(Screen.Settings.route) {
            SettingsScreen(navController = navController, soundManager = soundManager)
        }
    }
}

@Composable
fun SettingItem(text: String, onClick: (() -> Unit)? = null, trailingContent: @Composable (() -> Unit)? = null) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Black.copy(alpha = 0.2f), RoundedCornerShape(12.dp))
            .clickable(enabled = onClick != null) { onClick?.invoke() }
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(style = MaterialTheme.typography.headlineLarge.copy(fontFamily = customFont),text=text, color = Color.White, fontSize = 18.sp)
        if (trailingContent != null) {
            trailingContent()
        }
    }
}

@Composable
fun PremiumButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth(0.8f),
        contentPadding = PaddingValues(),
        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color(0xFFFFD700), Color(0xFFFFA500)) // Gold/Orange gradient
                    )
                )
                .padding(vertical = 16.dp),
            contentAlignment = Alignment.Center
        ) {

            Text(
                modifier = Modifier.align(Alignment.Center),
                style = MaterialTheme.typography.headlineLarge.copy(fontFamily = customFont),
                text = text.uppercase(), color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 18.sp)
        }
    }
}

@Composable
fun BumpButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth(0.8f),
        contentPadding = PaddingValues(),
        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color(0xFFFFD700), Color(0xFFFFA500)) // Gold/Orange gradient
                    )
                )
                .padding(vertical = 16.dp),
            contentAlignment = Alignment.Center
        ) {

            Text(
                modifier = Modifier.align(Alignment.Center),
                style = MaterialTheme.typography.headlineLarge.copy(fontFamily = customFont),
                text = text.uppercase(), color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 22.sp)
        }
    }
}

@Composable
fun PremiumButtonAnimated(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(24.dp),
        modifier = Modifier.fillMaxWidth(0.8f).padding(30.dp),
        contentPadding = PaddingValues(),
        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color(0xFFF59416), Color(0xFFFF5900)) // Gold/Orange gradient
                    )
                )
                .padding(vertical = 16.dp),
            contentAlignment = Alignment.Center
        ) {

            val infiniteTransition = rememberInfiniteTransition()
            val alpha by infiniteTransition.animateFloat(
                initialValue = 0.3f,
                targetValue = 1f,
                animationSpec = infiniteRepeatable(
                    animation = tween(1000, easing = LinearEasing),
                    repeatMode = RepeatMode.Reverse
                )
            )

            Text(
                modifier = Modifier.align(Alignment.Center).alpha(alpha),
                style = MaterialTheme.typography.headlineLarge.copy(fontFamily = customFont),
                text = text.uppercase(), color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 18.sp)
        }
    }
}
@Composable
fun LevelSelectScreen(onLevelClick: (Int) -> Unit) {
    val context = LocalContext.current
    val sharedPrefs = context.getSharedPreferences(PREFS_NAME_GAME, Context.MODE_PRIVATE)
    val unlockedLevel by remember { mutableStateOf(sharedPrefs.getInt(KEY_UNLOCKED_LEVEL, 1)) }
    val starsData = sharedPrefs.getString(KEY_STARS, "") ?: ""
    val starsMap = remember {
        starsData.split(",").filter { it.isNotEmpty() }.associate {
            val (level, stars) = it.split(":")
            level.toInt() to stars.toInt()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(Color(0xFF0288D1), Color(0xFF00008B))))
            .padding(16.dp)
    ) {
        Text(style = MaterialTheme.typography.headlineLarge.copy(fontFamily = customFont),
            text = "Select Level",
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(vertical = 32.dp)
        )
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(30) { levelIndex ->
                val level = levelIndex + 1
                val isLocked = level > unlockedLevel
                val stars = starsMap[level] ?: 0
                LevelButton(
                    level = level,
                    isLocked = isLocked,
                    stars = stars,
                    onClick = { onLevelClick(level) }
                )
            }
        }
    }
}

@Composable
fun LevelButton(level: Int, isLocked: Boolean, stars: Int, onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.alpha(if (isLocked) 0.5f else 1f)
    ) {
        Button(
            onClick = onClick,
            enabled = !isLocked,
            shape = CircleShape,
            modifier = Modifier.size(80.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFA500)),
            contentPadding = PaddingValues(0.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                if (isLocked) {
                    Text(style = MaterialTheme.typography.headlineLarge.copy(fontFamily = customFont),text = "🔒", fontSize = 32.sp)
                } else {
                    Text(style = MaterialTheme.typography.headlineLarge.copy(fontFamily = customFont),
                        text = level.toString(),
                        fontSize = 32.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
        Row(modifier = Modifier.padding(top = 4.dp)) {
            repeat(3) { index ->
                Text(style = MaterialTheme.typography.headlineLarge.copy(fontFamily = customFont),text = if (index < stars) "⭐" else "☆", fontSize = 24.sp)
            }
        }
    }
}