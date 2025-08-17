package com.mostbet.cricmost

import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mostbet.cricmost.ui.theme.CricketTheme
import kotlin.math.abs
import kotlin.random.Random

const val GRID_SIZE = 8

class MainActivity : ComponentActivity() {

    private lateinit var webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Pre-load WebView
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

@Composable
fun AppNavigation(webView: WebView) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "main_menu") {
        composable("main_menu") { MainScreen(navController) }
        composable("level_select") { LevelScreen(navController) }
        composable("game") { GameScreen(navController) }
        composable("privacy_policy") { PrivacyPolicyScreen(webView) }
    }
}

@Composable
fun MainScreen(navController: NavController) {
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
            Button(onClick = { navController.navigate("level_select") },
                  modifier = Modifier.fillMaxWidth(0.8f)) {
                Text("Career Mode")
            }
            Spacer(modifier = Modifier.height(20.dp))
            Button(onClick = { /* TODO */ },
                  modifier = Modifier.fillMaxWidth(0.8f)) {
                Text("Endless Mode")
            }
            Spacer(modifier = Modifier.height(40.dp))
            Button(onClick = { navController.navigate("privacy_policy") },
                  modifier = Modifier.fillMaxWidth(0.6f)) {
                Text("Privacy Policy")
            }
        }
    }
}



@Composable
fun LevelScreen(navController: NavController) {
    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_level_select_background),
            contentDescription = "Level Select Background",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "LEVELS",
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
                items(30) { level ->
                    val stars = Random.nextInt(0, 4) // Random stars for demo
                    LevelItem(level = level + 1, stars = stars) {
                        navController.navigate("game")
                    }
                }
            }
        }
    }
}

@Composable
fun LevelItem(level: Int, stars: Int, onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = onClick,
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
                            colors = listOf(Color(0xFFFAD961), Color(0xFFF76B1C))
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = level.toString(),
                    fontSize = 32.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
            }
        }
        Row(modifier = Modifier.padding(top = 4.dp)) {
            repeat(3) { index ->
                val starEmoji = if (index < stars) "⭐" else "☆"
                Text(text = starEmoji, fontSize = 24.sp)
            }
        }
    }
}

@Composable
fun GameScreen(navController: NavController) {
    var score by remember { mutableStateOf(0) }
    var moves by remember { mutableStateOf(24) }

    Box(modifier = Modifier.fillMaxSize()) { // Wrap in a Box
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
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Moves: $moves", fontSize = 24.sp, color = Color.White, fontWeight = FontWeight.Bold)
                Text(text = "Score: $score", fontSize = 24.sp, color = Color.White, fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.height(32.dp))
            GameBoard {
                score += 100
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

    // This is now a pure function
    fun swap(g: List<List<String>>, r1: Int, c1: Int, r2: Int, c2: Int): List<List<String>> {
        val newGrid = g.map { it.toMutableList() }.toMutableList()
        val temp = newGrid[r1][c1]
        newGrid[r1][c1] = newGrid[r2][c2]
        newGrid[r2][c2] = temp
        return newGrid
    }

    // This is now a pure function
    fun checkMatches(g: List<List<String>>): Pair<List<List<String>>, Boolean> {
        val newGrid = g.map { it.toMutableList() }.toMutableList()
        var matchFound = false

        val rowsToRemove = mutableSetOf<Pair<Int, Int>>()
        // Check horizontal matches
        for (r in 0 until GRID_SIZE) {
            for (c in 0 until GRID_SIZE - 2) {
                if (newGrid[r][c].isNotBlank() && newGrid[r][c] == newGrid[r][c+1] && newGrid[r][c] == newGrid[r][c+2]) {
                    matchFound = true
                    rowsToRemove.add(r to c); rowsToRemove.add(r to c+1); rowsToRemove.add(r to c+2)
                }
            }
        }

        // Check vertical matches
        for (c in 0 until GRID_SIZE) {
            for (r in 0 until GRID_SIZE - 2) {
                if (newGrid[r][c].isNotBlank() && newGrid[r][c] == newGrid[r+1][c] && newGrid[r][c] == newGrid[r+2][c]) {
                    matchFound = true
                    rowsToRemove.add(r to c); rowsToRemove.add(r+1 to c); rowsToRemove.add(r+2 to c)
                }
            }
        }

        if(matchFound) {
            rowsToRemove.forEach { newGrid[it.first][it.second] = emptyEmoji }
            onMatch()
        }
        return newGrid to matchFound
    }

    // This is now a pure function
    fun dropAndRefill(g: List<List<String>>): List<List<String>> {
        val newGrid = g.map { it.toMutableList() }.toMutableList()
        for (c in 0 until GRID_SIZE) {
            var emptyRow = GRID_SIZE - 1
            for (r in GRID_SIZE - 1 downTo 0) {
                if (newGrid[r][c] != emptyEmoji) {
                    val temp = newGrid[r][c]
                    newGrid[r][c] = emptyEmoji
                    newGrid[emptyRow][c] = temp
                    emptyRow--
                }
            }
            for (r in 0..emptyRow) {
                newGrid[r][c] = emojis.random()
            }
        }
        return newGrid
    }

    Box(modifier = Modifier.border(2.dp, Color.White)){
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
                                            // Perform swap and check for matches in a structured way
                                            var gridAfterSwap = swap(grid, selI, selJ, i, j)
                                            var (gridAfterMatches, matchFound) = checkMatches(gridAfterSwap)

                                            if (matchFound) {
                                                var finalGrid = gridAfterMatches
                                                while(true) {
                                                    finalGrid = dropAndRefill(finalGrid)
                                                    val (nextPassGrid, nextMatchFound) = checkMatches(finalGrid)
                                                    if(nextMatchFound) {
                                                        finalGrid = nextPassGrid
                                                    } else {
                                                        grid = finalGrid
                                                        break
                                                    }
                                                }
                                            } else {
                                                // No match, so don't update the grid (or swap back)
                                                // For now, we just don't apply the swap
                                            }
                                        }
                                        selectedItem = null // Deselect item
                                    }
                                }
                        ) {
                            Text(text = grid[i][j], fontSize = 24.sp)
                        }
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
