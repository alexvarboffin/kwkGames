package com.vai.vaidebet.vaibrazil.ui.screens.game

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.graphics.drawscope.Stroke
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
                    onBlockDrag = { block, dragX, dragY, gridSize -> viewModel.onBlockDrag(block, dragX, dragY, gridSize) },
                    onBlockDragEnd = { block -> viewModel.onBlockDragEnd(block) }
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
    onBlockDrag: (Block, Float, Float, Float) -> Unit,
    onBlockDragEnd: (Block) -> Unit
) {
    var selectedBlock by remember { mutableStateOf<Block?>(null) }

    Box(modifier = modifier.border(4.dp, Color.Black)) { // Added Modifier.border here
        Canvas(modifier = Modifier
            .fillMaxSize()
            .pointerInput(level.blocks) { // Use level.blocks as key to restart pointerInput when blocks change
                val gridSize: Float = size.width / level.gridWidth.toFloat() // Use level.gridWidth
                detectDragGestures(
                    onDragStart = { offset ->
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
                        selectedBlock?.let { block ->
                            onBlockDrag(block, drag.x, drag.y, gridSize)
                        }
                    },
                    onDragEnd = {
                        selectedBlock?.let { block ->
                            onBlockDragEnd(block)
                        }
                        selectedBlock = null
                    }
                )
            }
        ) {
            drawFootballField()
            val gridSize = size.width / level.gridWidth.toFloat() // Use level.gridWidth
            if (showGrid) {
                drawGrid(gridSize, level.gridWidth, level.gridHeight) // Pass grid dimensions
            }
            for (block in level.blocks) {
                drawBlock(block, gridSize)
            }
            drawGoal(gridSize, level.exitX, level.exitY)
        }
    }
}

private fun DrawScope.drawFootballField() {
    drawRect(color = Color(0xFF006400)) // Dark Green
    val strokeWidth = 2.dp.toPx()
    // Center circle
    drawCircle(
        color = Color.White,
        radius = size.width / 6f,
        style = Stroke(width = strokeWidth)
    )
    // Center line
    drawLine(
        color = Color.White,
        start = Offset(x = size.width / 2f, y = 0f),
        end = Offset(x = size.width / 2f, y = size.height),
        strokeWidth = strokeWidth
    )
}

private fun DrawScope.drawGrid(gridSize: Float, gridWidth: Int, gridHeight: Int) {
    for (i in 1 until gridWidth) { // Use gridWidth
        drawLine(
            color = Color.Black,
            start = Offset(x = i * gridSize, y = 0f),
            end = Offset(x = i * gridSize, y = size.height),
            strokeWidth = 1.dp.toPx()
        )
    }
    for (i in 1 until gridHeight) { // Use gridHeight
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
    val borderSize = 4.dp.toPx() // Thickness of the border
    val inset = borderSize / 2f // Inset the colored block by half the border thickness

    val topLeftOuter = Offset(block.col * gridSize, block.row * gridSize)
    val sizeOuter = when (block.orientation) {
        Orientation.HORIZONTAL -> Size(block.length * gridSize, gridSize)
        Orientation.VERTICAL -> Size(gridSize, block.length * gridSize)
    }

    // Draw the outer black rectangle (the border)
    drawRect(color = Color.Black, topLeft = topLeftOuter, size = sizeOuter)

    // Calculate inner dimensions for the colored block
    val topLeftInner = Offset(topLeftOuter.x + inset, topLeftOuter.y + inset)
    val sizeInner = Size(sizeOuter.width - borderSize, sizeOuter.height - borderSize)

    // Draw the inner colored rectangle
    val color = if (block.isTarget) Color.Red else Color.Gray // Use distinct colors for clarity
    drawRect(color = color, topLeft = topLeftInner, size = sizeInner)
}

private fun DrawScope.drawGoal(gridSize: Float, exitX: Int, exitY: Int) {
    val goalWidth = gridSize * 0.8f
    val goalHeight = gridSize * 2f
    val goalX = exitX * gridSize
    val goalY = exitY * gridSize

    drawRect(
        color = Color.White,
        topLeft = Offset(goalX, goalY),
        size = Size(goalWidth, goalHeight),
        style = Stroke(width = 2.dp.toPx())
    )
    // Draw net lines
    for (i in 0..4) {
        drawLine(
            color = Color.White,
            start = Offset(goalX + i * (goalWidth / 4f), goalY),
            end = Offset(goalX + i * (goalWidth / 4f), goalY + goalHeight),
            strokeWidth = 1.dp.toPx()
        )
    }
    for (i in 0..8) {
        drawLine(
            color = Color.White,
            start = Offset(goalX, goalY + i * (goalHeight / 8f)),
            end = Offset(goalX + goalWidth, goalY + i * (goalHeight / 8f)),
            strokeWidth = 1.dp.toPx()
        )
    }
}