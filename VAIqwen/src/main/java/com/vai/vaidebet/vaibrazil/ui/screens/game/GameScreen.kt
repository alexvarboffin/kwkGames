package com.vai.vaidebet.vaibrazil.ui.screens.game

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vai.vaidebet.vaibrazil.domain.model.Block
import com.vai.vaidebet.vaibrazil.domain.model.GameLevel
import com.vai.vaidebet.vaibrazil.domain.model.Orientation
import com.vai.vaidebet.vaibrazil.presentation.screens.game.GameUiState
import com.vai.vaidebet.vaibrazil.ui.theme.*

@Composable
fun GameScreen(
    uiState: GameUiState,
    onMoveBlock: (blockId: Int, newRow: Int, newCol: Int) -> Unit,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        when (uiState) {
            is GameUiState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            is GameUiState.Success -> {
                GameContent(
                    level = uiState.level,
                    blocks = uiState.blocks,
                    moves = uiState.moves,
                    onMoveBlock = onMoveBlock,
                    onBack = onBack
                )
            }
            is GameUiState.Won -> {
                // Отображаем экран победы
                WinScreen(
                    level = uiState.level,
                    moves = uiState.moves,
                    stars = uiState.stars,
                    onBack = onBack
                )
            }
            is GameUiState.Error -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = uiState.message,
                        color = MaterialTheme.colors.error
                    )
                }
            }
        }
    }
}

@Composable
fun GameContent(
    level: GameLevel,
    blocks: List<Block>,
    moves: Int,
    onMoveBlock: (blockId: Int, newRow: Int, newCol: Int) -> Unit,
    onBack: () -> Unit
) {
    // Заголовок
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(onClick = onBack) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Назад"
            )
        }
        
        Text(
            text = level.name,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        
        Spacer(modifier = Modifier.width(48.dp))
    }
    
    // Счетчик ходов
    Text(
        text = "Ходы: $moves",
        fontSize = 16.sp,
        modifier = Modifier
            .align(Alignment.End)
            .padding(top = 8.dp, bottom = 8.dp)
    )
    
    // Игровое поле
    GameBoard(
        blocks = blocks,
        onBlockMoved = onMoveBlock,
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .background(GreenField)
    )
}

@Composable
fun WinScreen(
    level: GameLevel,
    moves: Int,
    stars: Int,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Победа!",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        
        Text(
            text = "Уровень ${level.name} пройден",
            fontSize = 20.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        
        Text(
            text = "Ходы: $moves",
            fontSize = 18.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        
        // Отображаем звезды
        Row(
            modifier = Modifier.padding(bottom = 32.dp)
        ) {
            repeat(3) { index ->
                Icon(
                    imageVector = androidx.compose.material.icons.filled.Star,
                    contentDescription = null,
                    tint = if (index < stars) Color.Yellow else Color.Gray,
                    modifier = Modifier
                        .size(48.dp)
                        .padding(4.dp)
                )
            }
        }
        
        Button(
            onClick = onBack,
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Вернуться к уровням")
        }
    }
}

@Composable
fun GameBoard(
    blocks: List<Block>,
    onBlockMoved: (blockId: Int, newRow: Int, newCol: Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val boardSize = 6
    val cellSize = 40.dp
    
    Box(
        modifier = modifier
    ) {
        // Рисуем сетку поля
        Canvas(
            modifier = Modifier.fillMaxSize()
        ) {
            val width = size.width
            val height = size.height
            val cellWidth = width / boardSize
            val cellHeight = height / boardSize
            
            // Горизонтальные линии
            for (i in 0..boardSize) {
                drawLine(
                    color = Color.White,
                    start = Offset(0f, i * cellHeight),
                    end = Offset(width, i * cellHeight),
                    strokeWidth = 2f
                )
            }
            
            // Вертикальные линии
            for (i in 0..boardSize) {
                drawLine(
                    color = Color.White,
                    start = Offset(i * cellWidth, 0f),
                    end = Offset(i * cellWidth, height),
                    strokeWidth = 2f
                )
            }
        }
        
        // Рисуем блоки
        blocks.forEach { block ->
            DraggableBlock(
                block = block,
                cellSize = cellSize,
                onMoved = { newRow, newCol ->
                    onBlockMoved(block.id, newRow, newCol)
                }
            )
        }
    }
}

@Composable
fun DraggableBlock(
    block: Block,
    cellSize: dp,
    onMoved: (newRow: Int, newCol: Int) -> Unit
) {
    var offsetX by remember { mutableStateOf(0f) }
    var offsetY by remember { mutableStateOf(0f) }
    
    val blockColor = when (block.id % 7) {
        1 -> RedBlock
        2 -> BlueBlock
        3 -> PurpleBlock
        4 -> OrangeBlock
        5 -> BrownBlock
        6 -> GrayBlock
        else -> YellowBlock
    }
    
    Box(
        modifier = Modifier
            .offset(offsetX.dp, offsetY.dp)
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = { },
                    onDragEnd = {
                        // Вычисляем новую позицию блока
                        val newRow = block.row + (offsetY / cellSize.value).toInt()
                        val newCol = block.col + (offsetX / cellSize.value).toInt()
                        
                        // Проверяем границы поля
                        val clampedRow = newRow.coerceIn(0, 5)
                        val clampedCol = newCol.coerceIn(0, 5)
                        
                        onMoved(clampedRow, clampedCol)
                        
                        // Сбрасываем смещение
                        offsetX = 0f
                        offsetY = 0f
                    }
                ) { change, dragAmount ->
                    change.consume()
                    offsetX += dragAmount.x
                    offsetY += dragAmount.y
                }
            }
    ) {
        Canvas(
            modifier = Modifier
                .size(
                    width = if (block.orientation == Orientation.HORIZONTAL) cellSize * block.length else cellSize,
                    height = if (block.orientation == Orientation.VERTICAL) cellSize * block.length else cellSize
                )
        ) {
            drawRect(
                color = blockColor,
                size = Size(size.width, size.height)
            )
        }
    }
}