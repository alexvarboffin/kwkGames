# Master Prompt: Re-creation of the Cricket Match-3 Game (v2)

This document provides a complete, step-by-step guide for an AI assistant to recreate the Android Match-3 game "Most Game". Following these instructions precisely will result in the exact final state of the project.

## Goal

To build a functional Android game using Kotlin and Jetpack Compose. The game will feature two modes: a level-based "Career Mode" with progress saving, and a time-based "Endless Mode". The entire application will use a modern single-activity architecture with Jetpack Navigation.

---

## Phase 1: Initial Project & Asset Setup

1.  **Create Project:** Start with a new, empty Android project for Jetpack Compose.
2.  **Set Package ID:** Open `app/build.gradle.kts` and change the `namespace` and `applicationId` to `com.mostbet.cricmost`.
3.  **Prepare Assets:** Assume the user has provided the following image files. You must place them into the `app/src/main/res/drawable/` directory:
    *   `ic_background.jpg`
    *   `ic_game_background.jpg`
    *   `ic_level_select_background.jpg`
4.  **Clean Default Resources:**
    *   Delete the default `ic_launcher_foreground.xml` from the `drawable` folder to avoid resource conflicts.
    *   Delete `ic_launcher.xml` and `ic_launcher_round.xml` from the `mipmap-anydpi-v26` folder as they reference the deleted foreground file.
5.  **Refactor Directory Structure:**
    *   The default source directory will be `app/src/main/java/com/example/yourapp/`. Rename this path to match the new package ID: `app/src/main/java/com/mostbet/cricmost/`.
    *   Ensure the `MainActivity.kt` is moved to this new directory.

---

## Phase 2: Single-Activity Architecture & Dependencies

1.  **Add Dependencies:** Modify the `dependencies` block in `app/build.gradle.kts` to include Jetpack Navigation. The final dependencies block should look like this:

    ```kotlin
    dependencies {
        implementation(libs.androidx.core.ktx)
        implementation(libs.androidx.lifecycle.runtime.ktx)
        implementation(libs.androidx.activity.compose)
        implementation(platform(libs.androidx.compose.bom))
        implementation(libs.androidx.ui)
        implementation(libs.androidx.ui.graphics)
        implementation(libs.androidx.ui.tooling.preview)
        implementation(libs.androidx.material3)
        implementation("androidx.navigation:navigation-compose:2.7.7") // Added
        testImplementation(libs.junit)
        androidTestImplementation(libs.androidx.junit)
        androidTestImplementation(libs.androidx.espresso.core)
        androidTestImplementation(platform(libs.androidx.compose.bom))
        androidTestImplementation(libs.androidx.ui.test.junit4)
        debugImplementation(libs.androidx.ui.tooling)
        debugImplementation(libs.androidx.ui.test.manifest)
    }
    ```

2.  **Simplify Manifest:** The application will only have one Activity (`MainActivity`). Clean the `AndroidManifest.xml` so that the `<application>` block contains only the `MainActivity` declaration. The final file should be:

    ```xml
    <?xml version="1.0" encoding="utf-8"?>
    <manifest xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:tools="http://schemas.android.com/tools">

        <uses-permission android:name="android.permission.INTERNET" />

        <application
            android:allowBackup="true"
            android:dataExtractionRules="@xml/data_extraction_rules"
            android:fullBackupContent="@xml/backup_rules"
            android:icon="@mipmap/ic_launcher"
            android:label="@string/app_name"
            android:roundIcon="@mipmap/ic_launcher_round"
            android:supportsRtl="true"
            android:theme="@style/Theme.Cricket">
            <activity
                android:name=".MainActivity"
                android:exported="true"
                android:label="@string/app_name"
                android:theme="@style/Theme.Cricket">
                <intent-filter>
                    <action android:name="android.intent.action.MAIN" />
                    <category android:name="android.intent.category.LAUNCHER" />
                </intent-filter>
            </activity>
        </application>
    </manifest>
    ```

---

## Phase 3: Code Implementation (The All-in-One Activity)

Replace the entire content of `app/src/main/java/com/mostbet/cricmost/MainActivity.kt` with the following code. This single file contains all the application logic, including navigation, all screens, game mechanics, and progress saving.

```kotlin
package com.mostbet.cricmost

import android.content.Context
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
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
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.mostbet.cricmost.ui.theme.CricketTheme
import kotlinx.coroutines.delay
import kotlin.math.abs
import kotlin.random.Random

// --- Constants ---
const val GRID_SIZE = 8
const val ENDLESS_MODE_TIME = 60f
const val PREFS_NAME = "CricketPrefs"
const val KEY_UNLOCKED_LEVEL = "unlockedLevel"

// --- Main Activity ---
class MainActivity : ComponentActivity() {

    private lateinit var webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        webView = WebView(this).apply {
            webViewClient = WebViewClient()
            loadUrl("https://jojoapp.site/apps")
        }

        setContent {
            CricketTheme {
                AppNavigation(webView)
            }
        }
    }
}

// --- Navigation ---
@Composable
fun AppNavigation(webView: WebView) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "main_menu") {
        composable("main_menu") { MainScreen(navController) }
        composable("level_select") { LevelScreen(navController) }
        composable(
            route = "game_career/{level}",
            arguments = listOf(navArgument("level") { type = NavType.IntType })
        ) { backStackEntry ->
            val level = backStackEntry.arguments?.getInt("level") ?: 1
            GameScreen(navController, isEndlessMode = false, level = level)
        }
        composable("game_endless") { GameScreen(navController, isEndlessMode = true, level = 1) }
        composable("privacy_policy") { PrivacyPolicyScreen(webView) }
    }
}

// --- Screens ---
@Composable
fun MainScreen(navController: NavController) {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(painter = painterResource(id = R.drawable.ic_background), contentDescription = "Background", contentScale = ContentScale.Crop, modifier = Modifier.fillMaxSize())
        Column(modifier = Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(modifier = Modifier.weight(1f))
            Button(onClick = { navController.navigate("level_select") }, modifier = Modifier.fillMaxWidth(0.8f)) { Text("Career Mode") }
            Spacer(modifier = Modifier.height(20.dp))
            Button(onClick = { navController.navigate("game_endless") }, modifier = Modifier.fillMaxWidth(0.8f)) { Text("Endless Mode") }
            Spacer(modifier = Modifier.weight(0.2f))
            Button(onClick = { navController.navigate("privacy_policy") }, modifier = Modifier.fillMaxWidth(0.6f)) { Text("Privacy Policy") }
            Spacer(modifier = Modifier.weight(0.3f))
        }
    }
}

@Composable
fun LevelScreen(navController: NavController) {
    val context = LocalContext.current
    val sharedPrefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    val unlockedLevel by remember { mutableStateOf(sharedPrefs.getInt(KEY_UNLOCKED_LEVEL, 3)) }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(painter = painterResource(id = R.drawable.ic_level_select_background), contentDescription = "Level Select Background", contentScale = ContentScale.Crop, modifier = Modifier.fillMaxSize())
        Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "LEVELS", fontSize = 48.sp, fontWeight = FontWeight.Bold, color = Color.White, modifier = Modifier.padding(vertical = 32.dp))
            LazyVerticalGrid(columns = GridCells.Fixed(3), modifier = Modifier.padding(horizontal = 16.dp), verticalArrangement = Arrangement.spacedBy(16.dp), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                items(30) { levelIndex ->
                    val currentLevel = levelIndex + 1
                    val isLocked = currentLevel > unlockedLevel
                    LevelItem(level = currentLevel, stars = 0, isLocked = isLocked) {
                        navController.navigate("game_career/$currentLevel")
                    }
                }
            }
        }
    }
}

@Composable
fun GameScreen(navController: NavController, isEndlessMode: Boolean, level: Int) {
    val context = LocalContext.current
    var score by remember { mutableStateOf(0) }
    var bestScore by remember { mutableStateOf(0) } // Placeholder for simplicity
    var moves by remember { mutableStateOf(24) }
    var timeLeft by remember { mutableStateOf(ENDLESS_MODE_TIME) }
    var showTimeoutDialog by remember { mutableStateOf(false) }
    var showLevelEndDialog by remember { mutableStateOf(false) }

    val goalScore = 1000 * level

    fun onGameEnd() {
        if (!isEndlessMode && !showLevelEndDialog) {
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
        LaunchedEffect(showTimeoutDialog) {
            if (!showTimeoutDialog) {
                while (timeLeft > 0) {
                    delay(1000)
                    timeLeft--
                }
                if (score > bestScore) bestScore = score
                showTimeoutDialog = true
            }
        }
    } else {
        if (moves <= 0) onGameEnd()
    }

    val animatedTime by animateFloatAsState(targetValue = timeLeft / ENDLESS_MODE_TIME, label = "")

    Box(modifier = Modifier.fillMaxSize()) {
        Image(painter = painterResource(id = R.drawable.ic_game_background), contentDescription = "Game Background", contentScale = ContentScale.Crop, modifier = Modifier.fillMaxSize())
        Column(modifier = Modifier.fillMaxSize().padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "MOST GAME", fontSize = 32.sp, fontWeight = FontWeight.Bold, color = Color.White, modifier = Modifier.padding(bottom = 16.dp))
            if (isEndlessMode) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) { Text("Best: $bestScore", color = Color.White); Text("Score: $score", color = Color.White) }
            } else {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) { Text("Moves: $moves", color = Color.White); Text("Goal: $goalScore", color = Color.White); Text("Score: $score", color = Color.White) }
            }
            Spacer(modifier = Modifier.height(32.dp))
            GameBoard {
                score += 100
                if(isEndlessMode) timeLeft = (timeLeft + 2).coerceAtMost(ENDLESS_MODE_TIME) // Add 2 seconds for each match
                else moves--
            }
            if(isEndlessMode) {
                Spacer(modifier = Modifier.weight(1f))
                LinearProgressIndicator(progress = { animatedTime }, modifier = Modifier.fillMaxWidth().height(20.dp))
            }
        }
        if (showTimeoutDialog) {
            TimeoutDialog(score = score) { navController.popBackStack() }
        }
        if (showLevelEndDialog) {
            LevelEndDialog(score = score, goal = goalScore, onDismiss = { navController.popBackStack() })
        }
    }
}

// --- UI Components & Game Logic ---
@Composable
fun LevelItem(level: Int, stars: Int, isLocked: Boolean, onClick: () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center, modifier = Modifier.alpha(if (isLocked) 0.5f else 1f)) {
        Button(onClick = onClick, enabled = !isLocked, shape = CircleShape, modifier = Modifier.size(80.dp), colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent), contentPadding = PaddingValues(0.dp)) {
            Box(modifier = Modifier.fillMaxSize().background(Brush.verticalGradient(colors = listOf(Color(0xFFFAD961), Color(0xFFF76B1C)))), contentAlignment = Alignment.Center) {
                if (isLocked) Text(text = "🔒", fontSize = 32.sp) else Text(text = level.toString(), fontSize = 32.sp, color = Color.Black, fontWeight = FontWeight.Bold)
            }
        }
        Row(modifier = Modifier.padding(top = 4.dp)) {
            repeat(3) { index -> Text(text = if (index < stars) "⭐" else "☆", fontSize = 24.sp) }
        }
    }
}

@Composable
fun TimeoutDialog(score: Int, onDismiss: () -> Unit) {
    Dialog(onDismissRequest = onDismiss) {
        Card { Column(Modifier.padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally) { Text("TIMEOUT!", fontWeight = FontWeight.Bold); Text("SCORE: $score"); Button(onClick = onDismiss) { Text("Main Menu") } } }
    }
}

@Composable
fun LevelEndDialog(score: Int, goal: Int, onDismiss: () -> Unit) {
    val isComplete = score >= goal
    Dialog(onDismissRequest = onDismiss) {
        Card { Column(Modifier.padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally) { Text(if(isComplete) "LEVEL COMPLETE!" else "LEVEL FAILED", fontWeight = FontWeight.Bold); Text("SCORE: $score / $goal"); Button(onClick = onDismiss) { Text("Continue") } } }
    }
}

@Composable
fun GameBoard(onMatch: () -> Unit) {
    val emojis = listOf("🏏", "🥊", "⛑️", "🏆", "⭐")
    val emptyEmoji = ""
    var grid by remember { mutableStateOf(List(GRID_SIZE) { List(GRID_SIZE) { emojis.random() } }) }
    var selectedItem by remember { mutableStateOf<Pair<Int, Int>?>(null) }

    fun swap(g: List<List<String>>, r1: Int, c1: Int, r2: Int, c2: Int): List<List<String>> {
        val newGrid = g.map { it.toMutableList() }.toMutableList(); val temp = newGrid[r1][c1]; newGrid[r1][c1] = newGrid[r2][c2]; newGrid[r2][c2] = temp; return newGrid
    }

    fun checkMatches(g: List<List<String>>): Pair<List<List<String>>, Boolean> {
        val newGrid = g.map { it.toMutableList() }.toMutableList(); var matchFound = false; val toRemove = mutableSetOf<Pair<Int, Int>>()
        for (r in 0 until GRID_SIZE) for (c in 0 until GRID_SIZE - 2) if (newGrid[r][c].isNotBlank() && newGrid[r][c] == newGrid[r][c+1] && newGrid[r][c] == newGrid[r][c+2]) { matchFound = true; toRemove.add(r to c); toRemove.add(r to c+1); toRemove.add(r to c+2) }
        for (c in 0 until GRID_SIZE) for (r in 0 until GRID_SIZE - 2) if (newGrid[r][c].isNotBlank() && newGrid[r][c] == newGrid[r+1][c] && newGrid[r][c] == newGrid[r+2][c]) { matchFound = true; toRemove.add(r to c); toRemove.add(r+1 to c); toRemove.add(r+2 to c) }
        if(matchFound) { toRemove.forEach { newGrid[it.first][it.second] = emptyEmoji }; onMatch() }; return newGrid to matchFound
    }

    fun dropAndRefill(g: List<List<String>>): List<List<String>> {
        val newGrid = g.map { it.toMutableList() }.toMutableList()
        for (c in 0 until GRID_SIZE) {
            var emptyRow = GRID_SIZE - 1
            for (r in GRID_SIZE - 1 downTo 0) if (newGrid[r][c] != emptyEmoji) { val temp = newGrid[r][c]; newGrid[r][c] = emptyEmoji; newGrid[emptyRow][c] = temp; emptyRow-- }
            for (r in 0..emptyRow) { newGrid[r][c] = emojis.random() }
        }
        return newGrid
    }

    Box(modifier = Modifier.border(2.dp, Color.White)){
        Column {
            for (i in 0 until GRID_SIZE) {
                Row(modifier = Modifier.height(40.dp)) {
                    for (j in 0 until GRID_SIZE) {
                        val isSelected = selectedItem?.first == i && selectedItem?.second == j
                        Box(contentAlignment = Alignment.Center, modifier = Modifier.size(40.dp).background(Color.Gray.copy(alpha = 0.3f)).border(if (isSelected) 2.dp else 0.dp, Color.White).clickable {
                            val currentSelection = selectedItem
                            if (currentSelection == null) { selectedItem = i to j } else {
                                val (selI, selJ) = currentSelection
                                if ((abs(selI - i) == 1 && selJ == j) || (abs(selJ - j) == 1 && selI == i)) {
                                    var gridAfterSwap = swap(grid, selI, selJ, i, j)
                                    var (gridAfterMatches, matchFound) = checkMatches(gridAfterSwap)
                                    if (matchFound) {
                                        var finalGrid = gridAfterMatches
                                        while(true) {
                                            finalGrid = dropAndRefill(finalGrid)
                                            val (nextPassGrid, nextMatchFound) = checkMatches(finalGrid)
                                            if(nextMatchFound) finalGrid = nextPassGrid else { grid = finalGrid; break }
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

@Composable
fun PrivacyPolicyScreen(webView: WebView) {
    AndroidView(factory = { webView })
}
```

---

## Phase 4: Verification

After all files and code have been set up as described above, run a final build command from the project's root directory to ensure everything compiles correctly.

```bash
gradlew.bat clean build
```

The build should be successful. The resulting application will be a complete Match-3 game with all the specified features.
