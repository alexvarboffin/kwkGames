package com.vai.vai.vaidebet.vaibrazil.ui

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.vai.vai.vaidebet.vaibrazil.R
import com.vai.vai.vaidebet.vaibrazil.model.GameState
import com.vai.vai.vaidebet.vaibrazil.presentation.GameViewModel


@Composable
fun GameBoard(gameState: GameState, gameViewModel: GameViewModel) {
    Box(modifier = Modifier.fillMaxSize()) {
        if (gameState.showGrid) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                for (i in 0..gameState.board.width) {
                    drawLine(
                        color = Color.White,
                        start = Offset(x = (i * 50).dp.toPx(), y = 0f),
                        end = Offset(x = (i * 50).dp.toPx(), y = (gameState.board.height * 50).dp.toPx())
                    )
                }
                for (i in 0..gameState.board.height) {
                    drawLine(
                        color = Color.White,
                        start = Offset(x = 0f, y = (i * 50).dp.toPx()),
                        end = Offset(x = (gameState.board.width * 50).dp.toPx(), y = (i * 50).dp.toPx())
                    )
                }
            }
        }
        gameState.board.blocks.forEach { block ->
            val drawableId = if (block.isTarget) R.drawable.ball else R.drawable.player
            val offsetX by animateDpAsState(targetValue = (block.x * 50).dp, label = "")
            val offsetY by animateDpAsState(targetValue = (block.y * 50).dp, label = "")
            Image(
                painter = painterResource(id = drawableId),
                contentDescription = "Block",
                modifier = Modifier
                    .offset(offsetX, offsetY)
                    .size((block.width * 50).dp, (block.height * 50).dp)
                    .pointerInput(block.id) {
                        detectDragGestures {
                            change, dragAmount ->
                            change.consume()
                            gameViewModel.moveBlock(block.id, dragAmount)
                        }
                    }
            )
        }
    }
}
