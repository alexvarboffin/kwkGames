package com.mostbet.cricmost

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mostbet.cricmost.ui.theme.CricketTheme
import kotlin.random.Random
import kotlin.math.abs

const val GRID_SIZE = 8

class GameActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CricketTheme {
                GameScreen()
            }
        }
    }
}

@Composable
fun GameScreen() {
    var score by remember { mutableStateOf(0) }
    var moves by remember { mutableStateOf(24) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF004d00))
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

@Composable
fun GameBoard(onMatch: () -> Unit) {
    val emojis = listOf("🏏", "🥊", "⛑️", "🏆", "⭐")
    val emptyEmoji = ""
    var grid by remember { mutableStateOf(List(GRID_SIZE) { List(GRID_SIZE) { emojis.random() } }) }
    var selectedItem by remember { mutableStateOf<Pair<Int, Int>?>(null) }

    fun swap(r1: Int, c1: Int, r2: Int, c2: Int) {
        val newGrid = grid.map { it.toMutableList() }.toMutableList()
        val temp = newGrid[r1][c1]
        newGrid[r1][c1] = newGrid[r2][c2]
        newGrid[r2][c2] = temp
        grid = newGrid
    }

    fun checkMatches(): Boolean {
        val newGrid = grid.map { it.toMutableList() }.toMutableList()
        var matchFound = false

        // Check horizontal matches
        for (r in 0 until GRID_SIZE) {
            for (c in 0 until GRID_SIZE - 2) {
                if (newGrid[r][c].isNotBlank() && newGrid[r][c] == newGrid[r][c+1] && newGrid[r][c] == newGrid[r][c+2]) {
                    matchFound = true
                    newGrid[r][c] = emptyEmoji
                    newGrid[r][c+1] = emptyEmoji
                    newGrid[r][c+2] = emptyEmoji
                }
            }
        }

        // Check vertical matches
        for (c in 0 until GRID_SIZE) {
            for (r in 0 until GRID_SIZE - 2) {
                if (newGrid[r][c].isNotBlank() && newGrid[r][c] == newGrid[r+1][c] && newGrid[r][c] == newGrid[r+2][c]) {
                    matchFound = true
                    newGrid[r][c] = emptyEmoji
                    newGrid[r+1][c] = emptyEmoji
                    newGrid[r+2][c] = emptyEmoji
                }
            }
        }

        if(matchFound) {
            grid = newGrid
            onMatch()
        }
        return matchFound
    }

    fun dropAndRefill() {
        val newGrid = grid.map { it.toMutableList() }.toMutableList()
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
        grid = newGrid
    }

    fun checkMatchesAndRefill() {
        if (checkMatches()) {
            dropAndRefill()
            checkMatchesAndRefill()
        }
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
                                            swap(selI, selJ, i, j)
                                            checkMatchesAndRefill()
                                        }
                                        selectedItem = null
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

@Preview(showBackground = true)
@Composable
fun GameScreenPreview() {
    CricketTheme {
        GameScreen()
    }
}