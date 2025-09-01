package com.mostbet.cricmost

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.webkit.WebView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.mostbet.cricmost.ui.theme.CricketTheme
import kotlinx.coroutines.delay
import kotlin.math.abs
import androidx.core.net.toUri

const val GRID_SIZE = 8
const val ENDLESS_MODE_TIME = 60f
const val PREFS_NAME = "CricketPrefs"
const val KEY_UNLOCKED_LEVEL = "unlockedLevel"

sealed class Screen(val route: String) {
    object MainMenu : Screen("main_menu")
    object LevelSelect : Screen("level_select")
    object GameCareer : Screen("game_career/{level}") {
        fun createRoute(level: Int) = "game_career/$level"
    }
    object GameEndless : Screen("game_endless")
}

class MainActivity : ComponentActivity() {

    private lateinit var webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        webView = WebView(this)

        setContent {
            CricketTheme {
                AppNavigation()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        webView.loadPrivacyPolicy("https://jojoapp.site/apps")
    }
}

fun openUrlInCustomTab(context: Context, url: String) {
    val builder = CustomTabsIntent.Builder()
    val customTabsIntent = builder.build()
    customTabsIntent.launchUrl(context, url.toUri())
}


@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.MainMenu.route) {
        composable(Screen.MainMenu.route) { 
            val context = LocalContext.current
            MainScreen(
                onCareerModeClick = { navController.navigate(Screen.LevelSelect.route) },
                onEndlessModeClick = { navController.navigate(Screen.GameEndless.route) },
                onPrivacyClick = { openUrlInCustomTab(context, "https://jojoapp.site/Privacy") },
                onFaqClick = { openUrlInCustomTab(context, "https://jojoapp.site/FAQ") }
            )
        }
        composable(Screen.LevelSelect.route) { 
            LevelScreen(
                onLevelClick = { level -> navController.navigate(Screen.GameCareer.createRoute(level)) }
            )
        }
        composable(
            route = Screen.GameCareer.route,
            arguments = listOf(navArgument("level") { type = NavType.IntType })
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

@Composable
fun MainScreen(
    onCareerModeClick: () -> Unit,
    onEndlessModeClick: () -> Unit,
    onPrivacyClick: () -> Unit,
    onFaqClick: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.ic_background),
            contentDescription = "Background",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(150.dp))
            BeautifulButton(
                onClick = onCareerModeClick,
                text = "Career Mode",
                modifier = Modifier.fillMaxWidth(0.8f)
            )
            Spacer(modifier = Modifier.height(20.dp))
            BeautifulButton(
                onClick = onEndlessModeClick,
                text = "Endless Mode",
                modifier = Modifier.fillMaxWidth(0.8f)
            )
            Spacer(modifier = Modifier.height(40.dp))
            Row(
                modifier = Modifier.fillMaxWidth(0.8f),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                BeautifulButton(
                    onClick = onPrivacyClick,
                    text = "Privacy",
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(16.dp))
                BeautifulButton(
                    onClick = onFaqClick,
                    text = "FAQ",
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
fun BeautifulButton(onClick: () -> Unit, text: String, modifier: Modifier = Modifier) {
    Button(
        onClick = onClick,
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            contentColor = Color.White
        ),
        contentPadding = PaddingValues()
    ) {
        Box(
            modifier = Modifier
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFFFAD961),
                            Color(0xFFF76B1C)
                        )
                    )
                )
                .border(BorderStroke(2.dp, Color.White))
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(text = text, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun LevelScreen(onLevelClick: (Int) -> Unit) {
    val context = LocalContext.current
    val sharedPrefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    val unlockedLevel by remember { mutableStateOf(sharedPrefs.getInt(KEY_UNLOCKED_LEVEL, 3)) }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.ic_level_select_background),
            contentDescription = "Level Select Background",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Column {
            Box(modifier = Modifier.height(30.dp))
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "",
                    fontSize = 48.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.padding(vertical = 32.dp)
                )
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    modifier = Modifier.padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(30) { levelIndex ->
                        val currentLevel = levelIndex + 1
                        val isLocked = currentLevel > unlockedLevel
                        LevelItem(level = currentLevel, stars = 0, isLocked = isLocked) {
                            onLevelClick(currentLevel)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun LevelItem(level: Int, stars: Int, isLocked: Boolean, onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.alpha(if (isLocked) 0.5f else 1f)
    ) {
        Button(
            onClick = onClick,
            enabled = !isLocked,
            shape = CircleShape,
            modifier = Modifier.size(80.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
            contentPadding = PaddingValues(0.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color(0xFFFAD961),
                                Color(0xFFF76B1C)
                            )
                        )
                    ), contentAlignment = Alignment.Center
            ) {
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
            repeat(3) { index -> Text(text = if (index < stars) "⭐" else "☆", fontSize = 24.sp) }
        }
    }
}

@Composable
fun GameScreen(isEndlessMode: Boolean, level: Int, onBack: () -> Unit) {
    val context = LocalContext.current
    var score by remember { mutableStateOf(0) }
    var bestScore by remember { mutableStateOf(0) } // Placeholder
    var moves by remember { mutableStateOf(24) }
    var timeLeft by remember { mutableStateOf(ENDLESS_MODE_TIME) }
    var showTimeoutDialog by remember { mutableStateOf(false) }
    var showLevelEndDialog by remember { mutableStateOf(false) }

    val goalScore = 1000 * level // Example goal

    fun onGameEnd() {
        if (!isEndlessMode) {
            showLevelEndDialog = true
            if (score >= goalScore) {
                val sharedPrefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                val unlockedLevel = sharedPrefs.getInt(KEY_UNLOCKED_LEVEL, 3)
                if (level == unlockedLevel) {
                    sharedPrefs.edit().putInt(KEY_UNLOCKED_LEVEL, unlockedLevel + 1).apply()
                }
            }
        }
    }

    if (isEndlessMode) {
        LaunchedEffect(Unit) {
            while (timeLeft > 0) {
                delay(1000)
                timeLeft--
            }
            if (score > bestScore) bestScore = score
            showTimeoutDialog = true
        }
    } else {
        if (moves <= 0) onGameEnd()
    }

    val animatedTime by animateFloatAsState(targetValue = timeLeft / ENDLESS_MODE_TIME, label = "")

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.ic_game_background),
            contentDescription = "Game Background",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "MOST GAME",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            if (isEndlessMode) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Text("Best: $bestScore", color = Color.White); Text(
                    "Score: $score",
                    color = Color.White
                )
                }
            } else {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Text("Moves: $moves", color = Color.White); Text(
                    "Goal: $goalScore",
                    color = Color.White
                ); Text("Score: $score", color = Color.White)
                }
            }
            Spacer(modifier = Modifier.height(32.dp))
            GameBoard {
                score += 100
                if (isEndlessMode) timeLeft = (timeLeft + 1).coerceAtMost(ENDLESS_MODE_TIME)
                else moves--
            }
            if (isEndlessMode) {
                Spacer(modifier = Modifier.weight(1f))
                LinearProgressIndicator(
                    progress = { animatedTime },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(20.dp)
                )
            }
        }
        if (showTimeoutDialog) {
            TimeoutDialog(score = score) { onBack() }
        }
        if (showLevelEndDialog) {
            LevelEndDialog(
                score = score,
                goal = goalScore,
                onDismiss = { onBack() })
        }
    }
}

@Composable
fun TimeoutDialog(score: Int, onDismiss: () -> Unit) {
    Dialog(onDismissRequest = onDismiss) {
        Card {
            Column(
                Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "TIMEOUT!",
                    fontWeight = FontWeight.Bold
                ); Text("SCORE: $score"); Button(onClick = onDismiss) { Text("Main Menu") }
            }
        }
    }
}

@Composable
fun LevelEndDialog(score: Int, goal: Int, onDismiss: () -> Unit) {
    val isComplete = score >= goal
    Dialog(onDismissRequest = onDismiss) {
        Card {
            Column(
                Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    if (isComplete) "LEVEL COMPLETE!" else "LEVEL FAILED",
                    fontWeight = FontWeight.Bold
                ); Text("SCORE: $score / $goal"); Button(onClick = onDismiss) { Text("Continue") }
            }
        }
    }
}

@Composable
fun GameBoard(onMatch: () -> Unit) {
    val emojis = listOf("🏏", "🥊", "⛑️", "🏆", "⭐")
    val emptyEmoji = ""
    var grid by remember { mutableStateOf(List(GRID_SIZE) { List(GRID_SIZE) { emojis.random() } }) }
    var selectedItem by remember { mutableStateOf<Pair<Int, Int>?>(null) }

    fun swap(g: List<List<String>>, r1: Int, c1: Int, r2: Int, c2: Int): List<List<String>> {
        val newGrid = g.map { it.toMutableList() }.toMutableList();
        val temp = newGrid[r1][c1]; newGrid[r1][c1] = newGrid[r2][c2]; newGrid[r2][c2] =
            temp; return newGrid
    }

    fun checkMatches(g: List<List<String>>): Pair<List<List<String>>, Boolean> {
        val newGrid = g.map { it.toMutableList() }.toMutableList();
        var matchFound = false;
        val toRemove = mutableSetOf<Pair<Int, Int>>()
        for (r in 0 until GRID_SIZE) for (c in 0 until GRID_SIZE - 2) if (newGrid[r][c].isNotBlank() && newGrid[r][c] == newGrid[r][c + 1] && newGrid[r][c] == newGrid[r][c + 2]) {
            matchFound =
                true; toRemove.add(r to c); toRemove.add(r to c + 1); toRemove.add(r to c + 2)
        }
        for (c in 0 until GRID_SIZE) for (r in 0 until GRID_SIZE - 2) if (newGrid[r][c].isNotBlank() && newGrid[r][c] == newGrid[r + 1][c] && newGrid[r][c] == newGrid[r + 2][c]) {
            matchFound =
                true; toRemove.add(r to c); toRemove.add(r + 1 to c); toRemove.add(r + 2 to c)
        }
        if (matchFound) {
            toRemove.forEach { newGrid[it.first][it.second] = emptyEmoji }; onMatch()
        }; return newGrid to matchFound
    }

    fun dropAndRefill(g: List<List<String>>): List<List<String>> {
        val newGrid = g.map { it.toMutableList() }.toMutableList()
        for (c in 0 until GRID_SIZE) {
            var emptyRow = GRID_SIZE - 1
            for (r in GRID_SIZE - 1 downTo 0) if (newGrid[r][c] != emptyEmoji) {
                val temp = newGrid[r][c]; newGrid[r][c] = emptyEmoji; newGrid[emptyRow][c] =
                    temp; emptyRow--
            }
            for (r in 0..emptyRow) {
                newGrid[r][c] = emojis.random()
            }
        }
        return newGrid
    }

    Box(modifier = Modifier.border(2.dp, Color.White)) {
        Column {
            for (i in 0 until GRID_SIZE) {
                Row(modifier = Modifier.height(40.dp)) {
                    for (j in 0 until GRID_SIZE) {
                        val isSelected = selectedItem?.first == i && selectedItem?.second == j
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .size(40.dp)
                                .background(Color.Gray.copy(alpha = 0.3f))
                                .border(if (isSelected) 2.dp else 0.dp, Color.White)
                                .clickable {
                                    val currentSelection = selectedItem
                                    if (currentSelection == null) {
                                        selectedItem = i to j
                                    } else {
                                        val (selI, selJ) = currentSelection
                                        if ((abs(selI - i) == 1 && selJ == j) || (abs(selJ - j) == 1 && selI == i)) {
                                            var gridAfterSwap = swap(grid, selI, selJ, i, j)
                                            var (gridAfterMatches, matchFound) = checkMatches(
                                                gridAfterSwap
                                            )
                                            if (matchFound) {
                                                var finalGrid = gridAfterMatches
                                                while (true) {
                                                    finalGrid = dropAndRefill(finalGrid)
                                                    val (nextPassGrid, nextMatchFound) = checkMatches(
                                                        finalGrid
                                                    )
                                                    if (nextMatchFound) finalGrid =
                                                        nextPassGrid else {
                                                        grid = finalGrid; break
                                                    }
                                                }
                                            }
                                        }
                                        selectedItem = null
                                    }
                                }) { Text(text = grid[i][j], fontSize = 24.sp) }
                    }
                }
            }
        }
    }
}