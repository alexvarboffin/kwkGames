package com.vai.vaidebet.vaibrazil.ui.screens.game

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.vai.vaidebet.vaibrazil.domain.model.Block
import com.vai.vaidebet.vaibrazil.domain.model.GameLevel
import kotlin.math.abs

@Composable
fun DraggableGameBoard(
    modifier: Modifier = Modifier,
    level: GameLevel,
    showGrid: Boolean,
    onBlockMove: (Block, Float) -> Unit,
    content: @Composable (List<Block>) -> Unit
) {
    var selectedBlock by remember { mutableStateOf<Block?>(null) }
    var dragAmount by remember { mutableStateOf(0f) }

    Box(modifier = modifier) {
        content(level.blocks)
    }
    .pointerInput(level.blocks) {
        detectDragGestures(
            onDragStart = { offset ->
                val gridSize = size.width / 6
                val col = (offset.x / gridSize).toInt()
                val row = (offset.y / gridSize).toInt()
                selectedBlock = level.blocks.find { block ->
                    isPositionInBlock(block, col, row)
                }
            },
            onDrag = { change, drag ->
                selectedBlock?.let { block ->
                    dragAmount += if (block.orientation == com.vai.vaidebet.vaibrazil.domain.model.Orientation.HORIZONTAL) drag.x else drag.y
                    // Threshold to trigger a move
                    if (abs(dragAmount) > 50.dp.toPx()) {
                        onBlockMove(block, dragAmount)
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
}

private fun isPositionInBlock(block: Block, col: Int, row: Int): Boolean {
    return when (block.shape) {
        com.vai.vaidebet.vaibrazil.domain.model.BlockShape.RECTANGLE -> {
            val cols = block.col until (block.col + if (block.orientation == com.vai.vaidebet.vaibrazil.domain.model.Orientation.HORIZONTAL || block.orientation == com.vai.vaidebet.vaibrazil.domain.model.Orientation.SQUARE) block.length else 1)
            val rows = block.row until (block.row + if (block.orientation == com.vai.vaidebet.vaibrazil.domain.model.Orientation.VERTICAL || block.orientation == com.vai.vaidebet.vaibrazil.domain.model.Orientation.SQUARE) block.length else 1)
            col in cols && row in rows
        }
        com.vai.vaidebet.vaibrazil.domain.model.BlockShape.L_SHAPED -> {
            // L-shaped block: 2x2 with one corner missing
            (col == block.col && row == block.row) ||
            (col == block.col && row == block.row + 1) ||
            (col == block.col + 1 && row == block.row + 1)
        }
        com.vai.vaidebet.vaibrazil.domain.model.BlockShape.T_SHAPED -> {
            // T-shaped block: 3 cells in a T shape
            (col == block.col && row == block.row) ||
            (col == block.col + 1 && row == block.row) ||
            (col == block.col + 1 && row == block.row + 1)
        }
        com.vai.vaidebet.vaibrazil.domain.model.BlockShape.Z_SHAPED -> {
            // Z-shaped block: 3 cells in a Z shape
            (col == block.col && row == block.row) ||
            (col == block.col + 1 && row == block.row) ||
            (col == block.col + 1 && row == block.row + 1)
        }
    }
}