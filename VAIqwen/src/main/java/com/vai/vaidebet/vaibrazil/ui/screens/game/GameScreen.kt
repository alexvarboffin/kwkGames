package com.vai.vaidebet.vaibrazil.ui.screens.game

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vai.vaidebet.vaibrazil.R
import com.vai.vaidebet.vaibrazil.domain.model.Block
import com.vai.vaidebet.vaibrazil.domain.model.BlockShape
import com.vai.vaidebet.vaibrazil.domain.model.GameLevel
import com.vai.vaidebet.vaibrazil.domain.model.Orientation
import com.vai.vaidebet.vaibrazil.presentation.screens.game.GameUiState
import com.vai.vaidebet.vaibrazil.presentation.screens.game.GameViewModel
import com.vai.vaidebet.vaibrazil.ui.theme.Yellow
import com.vai.vaidebet.vaibrazil.ui.theme.Black
import com.vai.vaidebet.vaibrazil.ui.theme.Green

@Composable
fun GameScreen(
    viewModel: GameViewModel,
    onBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when {
            uiState.isLoading -> {
                Text(
                    text = "Loading...",
                    color = Yellow,
                    fontSize = 24.sp,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
            uiState.error != null -> {
                Text(
                    text = "Error: ${uiState.error}",
                    color = Color.Red,
                    fontSize = 24.sp,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
            uiState.isWon -> {
                WinScreen(
                    moveCount = uiState.moveCount,
                    stars = uiState.stars,
                    onPlayAgain = { viewModel.resetLevel() },
                    onBack = onBack
                )
            }
            else -> {
                GameControls(
                    moveCount = uiState.moveCount,
                    onReset = { viewModel.resetLevel() },
                    onToggleGrid = { viewModel.toggleGrid() },
                    onBack = onBack
                )
                GameBoard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                        .padding(16.dp),
                    level = uiState.level!!,
                    showGrid = uiState.showGrid,
                    onBlockMove = { block, dragAmount -> viewModel.moveBlock(block, dragAmount) }
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
        Text(
            text = "Moves: $moveCount",
            fontSize = 20.sp,
            color = Yellow
        )
        Row {
            Button(
                onClick = onReset,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Yellow,
                    contentColor = Black
                )
            ) {
                Text(text = "Reset")
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = onToggleGrid,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Yellow,
                    contentColor = Black
                )
            ) {
                Text(text = "Grid")
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = onBack,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Yellow,
                    contentColor = Black
                )
            ) {
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
    Box(modifier = modifier) {
        // Football field background
        Image(
            painter = painterResource(id = R.drawable.football_field),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Canvas(modifier = Modifier.fillMaxSize()) {
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

private fun DrawScope.drawGrid(gridSize: Float) {
    for (i in 1 until 6) {
        drawLine(
            color = Color.White,
            start = Offset(x = i * gridSize, y = 0f),
            end = Offset(x = i * gridSize, y = size.height),
            strokeWidth = 2.dp.toPx()
        )
        drawLine(
            color = Color.White,
            start = Offset(x = 0f, y = i * gridSize),
            end = Offset(x = size.width, y = i * gridSize),
            strokeWidth = 2.dp.toPx()
        )
    }
}

private fun DrawScope.drawBlock(block: Block, gridSize: Float) {
    val topLeft = Offset(block.col * gridSize, block.row * gridSize)

    when (block.shape) {
        BlockShape.RECTANGLE -> {
            val size = when (block.orientation) {
                Orientation.HORIZONTAL -> Size(block.length * gridSize, gridSize)
                Orientation.VERTICAL -> Size(gridSize, block.length * gridSize)
                Orientation.SQUARE -> Size(block.length * gridSize, block.length * gridSize)
            }

            if (block.isTarget) {
                // Draw the target block with yellow color
                drawRect(
                    color = Yellow,
                    topLeft = topLeft,
                    size = size
                )

                // Draw ball indicator on target block
                drawCircle(
                    color = Color.Red,
                    center = Offset(
                        (block.col + block.length/2f) * gridSize,
                        (block.row + 0.5f) * gridSize
                    ),
                    radius = gridSize / 3
                )
            } else {
                // Draw regular blocks with dark gray color
                drawRect(
                    color = Color(0xFF2C2C2C), // Dark gray
                    topLeft = topLeft,
                    size = size
                )

                // Draw player indicator on block
                drawRect(
                    color = Color.White.copy(alpha = 0.3f),
                    topLeft = Offset(block.col * gridSize + 4.dp.toPx(), block.row * gridSize + 4.dp.toPx()),
                    size = when (block.orientation) {
                        Orientation.HORIZONTAL -> Size(block.length * gridSize - 8.dp.toPx(), gridSize - 8.dp.toPx())
                        Orientation.VERTICAL -> Size(gridSize - 8.dp.toPx(), block.length * gridSize - 8.dp.toPx())
                        Orientation.SQUARE -> Size(block.length * gridSize - 8.dp.toPx(), block.length * gridSize - 8.dp.toPx())
                    }
                )
            }
        }

        BlockShape.L_SHAPED -> {
            // Draw L-shaped block
            if (block.isTarget) {
                drawRect(
                    color = Yellow,
                    topLeft = Offset(block.col * gridSize, block.row * gridSize),
                    size = Size(gridSize, gridSize * 2)
                )
                drawRect(
                    color = Yellow,
                    topLeft = Offset(block.col * gridSize, block.row * gridSize),
                    size = Size(gridSize * 2, gridSize)
                )
            } else {
                drawRect(
                    color = Color(0xFF2C2C2C),
                    topLeft = Offset(block.col * gridSize, block.row * gridSize),
                    size = Size(gridSize, gridSize * 2)
                )
                drawRect(
                    color = Color(0xFF2C2C2C),
                    topLeft = Offset(block.col * gridSize, block.row * gridSize),
                    size = Size(gridSize * 2, gridSize)
                )

                // Player indicators
                drawRect(
                    color = Color.White.copy(alpha = 0.3f),
                    topLeft = Offset(block.col * gridSize + 4.dp.toPx(), block.row * gridSize + 4.dp.toPx()),
                    size = Size(gridSize - 8.dp.toPx(), gridSize * 2 - 8.dp.toPx())
                )
                drawRect(
                    color = Color.White.copy(alpha = 0.3f),
                    topLeft = Offset(block.col * gridSize + 4.dp.toPx(), block.row * gridSize + 4.dp.toPx()),
                    size = Size(gridSize * 2 - 8.dp.toPx(), gridSize - 8.dp.toPx())
                )
            }
        }

        BlockShape.T_SHAPED -> {
            // Draw T-shaped block
            if (block.isTarget) {
                drawRect(
                    color = Yellow,
                    topLeft = Offset((block.col + 0.5f) * gridSize, block.row * gridSize),
                    size = Size(gridSize, gridSize * 2)
                )
                drawRect(
                    color = Yellow,
                    topLeft = Offset(block.col * gridSize, block.row * gridSize),
                    size = Size(gridSize * 2, gridSize)
                )
            } else {
                drawRect(
                    color = Color(0xFF2C2C2C),
                    topLeft = Offset((block.col + 0.5f) * gridSize, block.row * gridSize),
                    size = Size(gridSize, gridSize * 2)
                )
                drawRect(
                    color = Color(0xFF2C2C2C),
                    topLeft = Offset(block.col * gridSize, block.row * gridSize),
                    size = Size(gridSize * 2, gridSize)
                )

                // Player indicators
                drawRect(
                    color = Color.White.copy(alpha = 0.3f),
                    topLeft = Offset((block.col + 0.5f) * gridSize + 4.dp.toPx(), block.row * gridSize + 4.dp.toPx()),
                    size = Size(gridSize - 8.dp.toPx(), gridSize * 2 - 8.dp.toPx())
                )
                drawRect(
                    color = Color.White.copy(alpha = 0.3f),
                    topLeft = Offset(block.col * gridSize + 4.dp.toPx(), block.row * gridSize + 4.dp.toPx()),
                    size = Size(gridSize * 2 - 8.dp.toPx(), gridSize - 8.dp.toPx())
                )
            }
        }

        BlockShape.Z_SHAPED -> {
            // Draw Z-shaped block
            if (block.isTarget) {
                drawRect(
                    color = Yellow,
                    topLeft = Offset(block.col * gridSize, block.row * gridSize),
                    size = Size(gridSize * 2, gridSize)
                )
                drawRect(
                    color = Yellow,
                    topLeft = Offset((block.col + 1) * gridSize, (block.row + 1) * gridSize),
                    size = Size(gridSize * 2, gridSize)
                )
            } else {
                drawRect(
                    color = Color(0xFF2C2C2C),
                    topLeft = Offset(block.col * gridSize, block.row * gridSize),
                    size = Size(gridSize * 2, gridSize)
                )
                drawRect(
                    color = Color(0xFF2C2C2C),
                    topLeft = Offset((block.col + 1) * gridSize, (block.row + 1) * gridSize),
                    size = Size(gridSize * 2, gridSize)
                )

                // Player indicators
                drawRect(
                    color = Color.White.copy(alpha = 0.3f),
                    topLeft = Offset(block.col * gridSize + 4.dp.toPx(), block.row * gridSize + 4.dp.toPx()),
                    size = Size(gridSize * 2 - 8.dp.toPx(), gridSize - 8.dp.toPx())
                )
                drawRect(
                    color = Color.White.copy(alpha = 0.3f),
                    topLeft = Offset((block.col + 1) * gridSize + 4.dp.toPx(), (block.row + 1) * gridSize + 4.dp.toPx()),
                    size = Size(gridSize * 2 - 8.dp.toPx(), gridSize - 8.dp.toPx())
                )
            }
        }
    }
}

@Composable
fun WinScreen(
    moveCount: Int,
    stars: Int,
    onPlayAgain: () -> Unit,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "You Won!",
            fontSize = 48.sp,
            color = Yellow
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row {
            for (i in 1..3) {
                Text(
                    text = if (i <= stars) "★" else "☆",
                    fontSize = 48.sp,
                    modifier = Modifier.padding(horizontal = 4.dp),
                    color = if (i <= stars) Yellow else Color.Gray
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Moves: $moveCount",
            fontSize = 24.sp,
            color = Yellow
        )
        Spacer(modifier = Modifier.height(32.dp))
        Row {
            Button(
                onClick = onPlayAgain,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Yellow,
                    contentColor = Black
                ),
                modifier = Modifier.padding(end = 8.dp)
            ) {
                Text(text = "Play Again")
            }
            Button(
                onClick = onBack,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Yellow,
                    contentColor = Black
                ),
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Text(text = "Back to Menu")
            }
        }
    }
}