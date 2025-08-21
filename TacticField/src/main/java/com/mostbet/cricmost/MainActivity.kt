package com.mostbet.cricmost

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mostbet.cricmost.ui.theme.PXBFootballTheme

const val PREFS_NAME_GAME = "PXBGamePrefs"
const val KEY_UNLOCKED_LEVEL = "unlockedLevel"
const val KEY_STARS = "stars"

sealed class Screen(val route: String) {
    object MainMenu : Screen("main_menu")
    object LevelSelect : Screen("level_select")
    object TacticField : Screen("tactic_field/{level}") {
        fun createRoute(level: Int) = "tactic_field/$level"
    }
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PXBFootballTheme {
                AppNavigation()
            }
        }
    }
}

fun openUrlInCustomTab(context: Context, url: String) {
    val builder = CustomTabsIntent.Builder()
    val customTabsIntent = builder.build()
    customTabsIntent.launchUrl(context, Uri.parse(url))
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val context = LocalContext.current

    NavHost(navController = navController, startDestination = Screen.MainMenu.route) {
        composable(Screen.MainMenu.route) {
            MainMenuScreen(navController = navController)
        }
        composable(Screen.LevelSelect.route) {
            LevelSelectScreen(onLevelClick = { level ->
                val intent = Intent(context, GameActivity::class.java).apply {
                    putExtra("level", level)
                }
                context.startActivity(intent)
            })
        }
        composable(Screen.TacticField.route) {
            val intent = Intent(context, GameActivity::class.java).apply {
                putExtra("level", 1) // Default level
            }
            context.startActivity(intent)
        }
    }
}

@Composable
fun MainMenuScreen(navController: NavController) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0288D1)) // Blue background
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "PXB Football",
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(64.dp))

        val buttonModifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)

        Button(
            onClick = { navController.navigate(Screen.TacticField.createRoute(1)) },
            modifier = buttonModifier,
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFA500)) // Orange
        ) {
            Text(text = "Go to Game", color = Color.Black)
        }

        Button(
            onClick = { navController.navigate(Screen.LevelSelect.route) },
            modifier = buttonModifier,
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFA500)) // Orange
        ) {
            Text(text = "Select Level", color = Color.Black)
        }

        Button(
            onClick = { openUrlInCustomTab(context, "https://www.google.com") },
            modifier = buttonModifier,
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFA500)) // Orange
        ) {
            Text(text = "Privacy Policy", color = Color.Black)
        }

        Button(
            onClick = { openUrlInCustomTab(context, "https://www.google.com") },
            modifier = buttonModifier,
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFA500)) // Orange
        ) {
            Text(text = "FAQ", color = Color.Black)
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
        Text(
            text = "Select Level",
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.align(Alignment.CenterHorizontally).padding(vertical = 32.dp)
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
                    Text(text = "🔒", fontSize = 32.sp)
                } else {
                    Text(
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
                Text(text = if (index < stars) "⭐" else "☆", fontSize = 24.sp)
            }
        }
    }
}