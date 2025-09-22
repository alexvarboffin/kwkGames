package com.vai.vai.vaidebet.vaibrazil.ui.screens.game

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack

import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.vai.vai.vaidebet.vaibrazil.presentation.screens.game.GameUiState
import com.vai.vai.vaidebet.vaibrazil.presentation.screens.game.GameViewModel
import org.koin.androidx.compose.koinViewModel
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import com.vai.vai.vaidebet.vaibrazil.R
import com.walhalla.sdk.domain.model.Block
import com.walhalla.sdk.domain.model.GameLevel
import com.walhalla.sdk.domain.model.Orientation
import nl.dionsegijn.konfetti.compose.KonfettiView
import nl.dionsegijn.konfetti.core.Party
import nl.dionsegijn.konfetti.core.Position
import nl.dionsegijn.konfetti.core.emitter.Emitter
import org.koin.core.parameter.parametersOf
import java.util.concurrent.TimeUnit

@Composable
fun GameScreen(
    levelId: Int,
    onBack: () -> Unit,
    onContinue: () -> Unit, // Added onContinue
    viewModel: GameViewModel = koinViewModel(parameters = { parametersOf(levelId) })
) {
    val uiState by viewModel.uiState.collectAsState()
    val blockPixelOffsets by viewModel.blockPixelOffsets.collectAsState()

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
                    levelId = state.level.id,
                    totalLevels = state.totalLevels,
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
                    blockPixelOffsets = blockPixelOffsets,
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
                    timeTaken = state.timeTaken,
                    onPlayAgain = { viewModel.loadLevel() },
                    onContinue = onContinue // Pass onContinue
                )
            }
        }
    }
}

@Composable
fun GameControls(
    levelId: Int,
    totalLevels: Int,
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
        Column {
            Text(text = "Level: $levelId / $totalLevels", fontSize = 20.sp)
            Text(text = "Moves: $moveCount", fontSize = 20.sp)
        }
        Row {
            IconButton(onClick = onReset) {
                Icon(Icons.Default.Refresh, contentDescription = "Reset")
            }
            Spacer(modifier = Modifier.width(8.dp))
            IconButton(onClick = onToggleGrid) {
                Icon(Icons.Default.Menu, contentDescription = "Toggle Grid")
            }
            Spacer(modifier = Modifier.width(8.dp))
            IconButton(onClick = onBack) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
            }
        }
    }
}

@Composable
fun GameBoard(
    modifier: Modifier = Modifier,
    level: GameLevel,
    showGrid: Boolean,
    blockPixelOffsets: Map<Int, Pair<Float, Float>>,
    onBlockDrag: (Block, Float, Float, Float) -> Unit,
    onBlockDragEnd: (Block) -> Unit
) {
    var selectedBlock by remember { mutableStateOf<Block?>(null) }

    BoxWithConstraints(modifier = modifier.border(4.dp, Color.Black)) {
        val gridSize = maxWidth / level.gridWidth
        val gridSizePx = with(LocalDensity.current) { gridSize.toPx() }


        Canvas(modifier = Modifier.fillMaxSize()) {
            drawFootballField()
            if (showGrid) {
                drawGrid(gridSizePx, level.gridWidth, level.gridHeight)
            }
            drawGoal(gridSizePx, level.exitX, level.exitY)
        }

        // Draw blocks as separate Box composables
        for (block in level.blocks) {
            val currentOffset = blockPixelOffsets[block.id] ?: Pair(0f, 0f)
            Box(
                modifier = Modifier
                    .offset {
                        IntOffset(
                            x = (block.col * gridSizePx + currentOffset.first).toInt(),
                            y = (block.row * gridSizePx + currentOffset.second).toInt()
                        )
                    }
                    .width(gridSize * block.widthInGrid)
                    .height(gridSize * block.heightInGrid)
                    .background(if (block.isTarget) Color.Transparent else Color.Gray)
                    .border(2.dp, Color.Black)
                    .pointerInput(block) {
                        detectDragGestures(
                            onDragStart = {
                                selectedBlock = block
                            },
                            onDrag = { change, drag ->
                                selectedBlock?.let { b ->
                                    onBlockDrag(b, drag.x, drag.y, gridSizePx)
                                }
                            },
                            onDragEnd = {
                                selectedBlock?.let { b ->
                                    onBlockDragEnd(b)
                                }
                                selectedBlock = null
                            }
                        )
                    }
            ) {
                val drawableId = if (block.isTarget) R.drawable.ball else R.drawable.player
                Image(
                    painter = painterResource(id = drawableId),
                    contentDescription = "Ball",
                    modifier = Modifier.fillMaxSize()
                )
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
    for (i in 1 until gridWidth) {
        drawLine(
            color = Color.Black,
            start = Offset(x = i * gridSize, y = 0f),
            end = Offset(x = i * gridSize, y = size.height),
            strokeWidth = 1.dp.toPx()
        )
    }
    for (i in 1 until gridHeight) {
        drawLine(
            color = Color.Black,
            start = Offset(x = 0f, y = i * gridSize),
            end = Offset(x = size.width, y = i * gridSize),
            strokeWidth = 1.dp.toPx()
        )
    }
}

@Composable
fun WinScreen(moveCount: Int, stars: Int, timeTaken: Long, onPlayAgain: () -> Unit, onContinue: () -> Unit) {
    val party = Party(
        speed = 0f,
        maxSpeed = 30f,
        damping = 0.9f,
        spread = 360,
        colors = listOf(0xfce18a, 0xff726d, 0xf4306d, 0xb48def),
        emitter = Emitter(duration = 100, TimeUnit.MILLISECONDS).max(100),
        position = Position.Relative(0.5, 0.3)
    )

    Box(modifier = Modifier.fillMaxSize()) {
        KonfettiView(
            modifier = Modifier.fillMaxSize(),
            parties = listOf(party)
        )
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
            Text(text = "Time: ${timeTaken / 1000}s", fontSize = 24.sp)
            Spacer(modifier = Modifier.height(32.dp))
            Row {
                Button(onClick = onPlayAgain) {
                    Text(text = "Play Again")
                }
                Spacer(modifier = Modifier.width(16.dp))
                Button(onClick = onContinue) {
                    Text(text = "Continue")
                }
            }
        }
    }
}


private fun DrawScope.drawGoal(gridSize: Float, exitX: Int, exitY: Int) {
    val goalWidth = gridSize * 0.8f
    val goalHeight = gridSize
    val goalX = exitX * gridSize
    val goalY = exitY * gridSize

    drawRect(
        color = Color.Red,
        topLeft = Offset(goalX, goalY),
        size = Size(goalWidth, goalHeight),
        style = Stroke(width = 4.dp.toPx())
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

val Block.widthInGrid: Int
    get() = if (orientation == Orientation.HORIZONTAL) length else 1

val Block.heightInGrid: Int
    get() = if (orientation == Orientation.VERTICAL) length else 1
