package com.cricmost.cricmost

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.abs

@Composable
fun GameBoard(onMatch: () -> Unit) {
    val emojis = listOf("🏟️", "🧤", "🎯", "🔥", "👑")
    //val emojis = listOf("🏏", "🥊", "⛑️", "🏆", "⭐")

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

    Box(modifier = Modifier.Companion.border(2.dp, Color.Companion.White)) {
        Column {
            for (i in 0 until GRID_SIZE) {
                Row(modifier = Modifier.Companion.height(40.dp)) {
                    for (j in 0 until GRID_SIZE) {
                        val isSelected = selectedItem?.first == i && selectedItem?.second == j
                        Box(
                            contentAlignment = Alignment.Companion.Center,
                            modifier = Modifier.Companion
                                .size(40.dp)
                                .background(Color.Companion.Gray.copy(alpha = 0.3f))
                                .border(if (isSelected) 2.dp else 0.dp, Color.Companion.White)
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