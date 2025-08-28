package com.vai.vaidebet.vaibrazil.presentation

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect

import androidx.lifecycle.ViewModel
import com.vai.vaidebet.vaibrazil.data.LevelRepository
import com.vai.vaidebet.vaibrazil.model.Block
import com.vai.vaidebet.vaibrazil.model.GameBoard
import com.vai.vaidebet.vaibrazil.model.GameState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class GameViewModel(private val level: Int) : ViewModel() {

    private val levelRepository = LevelRepository()

    private val _gameState = MutableStateFlow(createInitialGameState())
    val gameState: StateFlow<GameState> = _gameState

    fun moveBlock(blockId: Int, dragAmount: Offset) {
        val currentState = _gameState.value
        if (currentState.isGameWon) return

        val blockToMove = currentState.board.blocks.first { it.id == blockId }

        val newX = if (blockToMove.width > blockToMove.height) blockToMove.x + (dragAmount.x / 50).toInt() else blockToMove.x
        val newY = if (blockToMove.height > blockToMove.width) blockToMove.y + (dragAmount.y / 50).toInt() else blockToMove.y

        if (isValidMove(blockToMove.copy(x = newX, y = newY), currentState.board)) {
            val newBlocks = currentState.board.blocks.map { block ->
                if (block.id == blockId) {
                    block.copy(x = newX, y = newY)
                } else {
                    block
                }
            }
            val newGameState = currentState.copy(board = currentState.board.copy(blocks = newBlocks), moves = currentState.moves + 1)
            _gameState.value = checkWinCondition(newGameState)
        }
    }

    fun resetGame() {
        _gameState.value = createInitialGameState()
    }

    fun toggleGrid() {
        _gameState.value = _gameState.value.copy(showGrid = !_gameState.value.showGrid)
    }

    private fun isValidMove(block: Block, board: GameBoard): Boolean {
        // Bounds check
        if (block.x < 0 || block.y < 0 ||
            block.x + block.width > board.width ||
            block.y + block.height > board.height
        ) {
            return false
        }

        // Collision check
        val newBlockRect = Rect(block.x.toFloat(), block.y.toFloat(), (block.x + block.width).toFloat(), (block.y + block.height).toFloat())
        board.blocks.forEach { otherBlock ->
            if (block.id != otherBlock.id) {
                val otherBlockRect = Rect(otherBlock.x.toFloat(), otherBlock.y.toFloat(), (otherBlock.x + otherBlock.width).toFloat(), (otherBlock.y + otherBlock.height).toFloat())
                if (newBlockRect.overlaps(otherBlockRect)) {
                    return false
                }
            }
        }

        return true
    }

    private fun checkWinCondition(gameState: GameState): GameState {
        val targetBlock = gameState.board.blocks.first { it.isTarget }
        if (targetBlock.x + targetBlock.width == gameState.board.exit.x && targetBlock.y == gameState.board.exit.y) {
            return gameState.copy(isGameWon = true)
        }
        return gameState
    }

    private fun createInitialGameState(): GameState {
        val board = levelRepository.getLevel(level)
        return GameState(board = board, moves = 0)
    }
}

