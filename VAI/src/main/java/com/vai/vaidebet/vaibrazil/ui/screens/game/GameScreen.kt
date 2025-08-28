package com.vai.vaidebet.vaibrazil.ui.screens.game

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vai.vaidebet.vaibrazil.domain.model.Block
import com.vai.vaidebet.vaibrazil.domain.model.GameLevel
import com.vai.vaidebet.vaibrazil.domain.model.Orientation
import com.vai.vaidebet.vaibrazil.presentation.screens.game.GameUiState
import com.vai.vaidebet.vaibrazil.presentation.screens.game.GameViewModel
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun GameScreen(
    levelId: Int,
    onBack: () -> Unit,
    viewModel: GameViewModel = koinViewModel(parameters = { parametersOf(levelId) })
) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (val state = uiState) {
            is GameUiState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            }
            is GameUiState.Success -> {
                GameControls(
                    moveCount = state.moveCount,
                    onReset = { viewModel.resetLevel() },
                    onToggleGrid = { viewModel.toggleGrid() },
                    onBack = onBack
                )
                GameBoard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                        .padding(16.dp),
                    level = state.level,
                    showGrid = state.showGrid,
                    onBlockMove = { block, dragAmount -> viewModel.moveBlock(block, dragAmount) }
                )
            }
            is GameUiState.Error -> {
                Text(
                    text = state.message,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
            is GameUiState.Won -> {
                WinScreen(
                    moveCount = state.finalMoveCount,
                    stars = state.stars,
                    onPlayAgain = { viewModel.loadLevel() }
                )
            }
        }
    }
}

@Composable
fun GameControls(
    moveCount: Int,
    onReset: () -> Unit,
    onToggleGrid: () -> Unit,
    onBack: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "Moves: $moveCount", fontSize = 20.sp)
        Row {
            Button(onClick = onReset) {
                Text(text = "Reset")
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = onToggleGrid) {
                Text(text = "Grid")
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = onBack) {
                Text(text = "Back")
            }
        }
    }
}

@Composable
fun GameBoard(
    modifier: Modifier = Modifier,
    level: GameLevel,
    showGrid: Boolean,
    onBlockMove: (Block, Float) -> Unit
) {
    var selectedBlock by remember { mutableStateOf<Block?>(null) }
    var dragAmount by remember { mutableStateOf(0f) }

    Box(modifier = modifier) {
        Canvas(modifier = Modifier
            .fillMaxSize()
            .pointerInput(level.blocks) {
                detectDragGestures(
                    onDragStart = { offset ->
                        val gridSize = size.width / 6
                        val col = (offset.x / gridSize).toInt()
                        val row = (offset.y / gridSize).toInt()
                        selectedBlock = level.blocks.find { block ->
                            if (block.orientation == Orientation.HORIZONTAL) {
                                row == block.row && col >= block.col && col < block.col + block.length
                            } else {
                                col == block.col && row >= block.row && row < block.row + block.length
                            }
                        }
                    },
                    onDrag = { change, drag ->
                        dragAmount += if (selectedBlock?.orientation == Orientation.HORIZONTAL) drag.x else drag.y
                        if (kotlin.math.abs(dragAmount) > 50) { // Threshold to trigger a move
                            selectedBlock?.let {
                                onBlockMove(it, dragAmount)
                                dragAmount = 0f
                            }
                        }
                    },
                    onDragEnd = {
                        selectedBlock = null
                        dragAmount = 0f
                    }
                )
            }
        ) {
            drawFootballField()
            val gridSize = size.width / 6
            if (showGrid) {
                drawGrid(gridSize)
            }
            for (block in level.blocks) {
                drawBlock(block, gridSize)
            }
        }
    }
}

private fun DrawScope.drawFootballField() {
    drawRect(color = Color(0xFF006400)) // Dark Green
    val strokeWidth = 2.dp.toPx()
    // Center circle
    drawCircle(
        color = Color.White,
        radius = size.width / 6,
        style = androidx.compose.ui.graphics.drawscope.Stroke(width = strokeWidth)
    )
    // Center line
    drawLine(
        color = Color.White,
        start = Offset(x = size.width / 2, y = 0f),
        end = Offset(x = size.width / 2, y = size.height),
        strokeWidth = strokeWidth
    )
}

private fun DrawScope.drawGrid(gridSize: Float) {
    for (i in 1 until 6) {
        drawLine(
            color = Color.Black,
            start = Offset(x = i * gridSize, y = 0f),
            end = Offset(x = i * gridSize, y = size.height),
            strokeWidth = 1.dp.toPx()
        )
        drawLine(
            color = Color.Black,
            start = Offset(x = 0f, y = i * gridSize),
            end = Offset(x = size.width, y = i * gridSize),
            strokeWidth = 1.dp.toPx()
        )
    }
}

@Composable
fun WinScreen(moveCount: Int, stars: Int, onPlayAgain: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "You Won!", fontSize = 48.sp)
        Spacer(modifier = Modifier.height(16.dp))
        Row {
            for (i in 1..3) {
                Text(
                    text = if (i <= stars) "⭐" else "☆",
                    fontSize = 48.sp,
                    modifier = Modifier.padding(horizontal = 4.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Moves: $moveCount", fontSize = 24.sp)
        Spacer(modifier = Modifier.height(32.dp))
        Button(onClick = onPlayAgain) {
            Text(text = "Play Again")
        }
    }
}


private fun DrawScope.drawBlock(block: Block, gridSize: Float) {
    val color = if (block.isTarget) {
        Color.Yellow
    } else {
        Color.Black.copy(alpha = 0.8f)
    }
    val topLeft = Offset(block.col * gridSize, block.row * gridSize)
    val size = when (block.orientation) {
        Orientation.HORIZONTAL -> Size(block.length * gridSize, gridSize)
        Orientation.VERTICAL -> Size(gridSize, block.length * gridSize)
        Orientation.SQUARE -> Size(block.length * gridSize, block.length * gridSize)
    }
    drawRect(color = color, topLeft = topLeft, size = size)
}