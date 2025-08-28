package com.vai.vaidebet.vaibrazil.presentation.screens.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vai.vaidebet.vaibrazil.di.AppModule
import com.vai.vaidebet.vaibrazil.domain.model.Block
import com.vai.vaidebet.vaibrazil.domain.model.GameLevel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class GameUiState(
    val isLoading: Boolean = false,
    val level: GameLevel? = null,
    val moveCount: Int = 0,
    val showGrid: Boolean = true,
    val error: String? = null,
    val isWon: Boolean = false,
    val stars: Int = 0
)

class GameViewModel(private val levelId: Int) : ViewModel() {
    private val _uiState = MutableStateFlow<GameUiState>(GameUiState())
    val uiState: StateFlow<GameUiState> = _uiState

    init {
        loadLevel()
    }

    private fun loadLevel() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                val level = AppModule.getLevelUseCase.invoke(levelId)
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    level = level,
                    moveCount = 0,
                    error = null,
                    isWon = false
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    level = null,
                    error = e.message
                )
            }
        }
    }

    fun moveBlock(block: Block, dragAmount: Float) {
        val currentState = _uiState.value
        val level = currentState.level ?: return
        
        // Calculate new position based on drag amount
        val newPosition = calculateNewPosition(block, dragAmount)
        
        // Validate move
        if (isValidMove(block, newPosition, level.blocks)) {
            // Update block position
            val updatedBlocks = level.blocks.map { 
                if (it.id == block.id) newPosition else it 
            }
            
            // Update move count
            val newMoveCount = currentState.moveCount + 1
            
            // Check win condition
            val isWon = checkWinCondition(newPosition, level.solutionMoves)
            val stars = if (isWon) calculateStars(newMoveCount, level.solutionMoves) else 0
            
            _uiState.value = currentState.copy(
                level = level.copy(blocks = updatedBlocks),
                moveCount = newMoveCount,
                isWon = isWon,
                stars = stars
            )
        }
    }

    private fun calculateNewPosition(block: Block, dragAmount: Float): Block {
        // Simplified calculation - in a real app, this would be more complex
        return if (block.orientation == com.vai.vaidebet.vaibrazil.domain.model.Orientation.HORIZONTAL) {
            block.copy(col = (block.col + (dragAmount / 100)).toInt().coerceIn(0, 6 - block.length))
        } else {
            block.copy(row = (block.row + (dragAmount / 100)).toInt().coerceIn(0, 6 - block.length))
        }
    }

    private fun isValidMove(block: Block, newPosition: Block, allBlocks: List<Block>): Boolean {
        // Check boundaries
        if (newPosition.col < 0 || newPosition.row < 0) return false
        if (newPosition.col + (if (newPosition.orientation == com.vai.vaidebet.vaibrazil.domain.model.Orientation.HORIZONTAL) newPosition.length else 1) > 6) return false
        if (newPosition.row + (if (newPosition.orientation == com.vai.vaidebet.vaibrazil.domain.model.Orientation.VERTICAL) newPosition.length else 1) > 6) return false
        
        // Check collisions with other blocks
        val otherBlocks = allBlocks.filter { it.id != block.id }
        for (otherBlock in otherBlocks) {
            if (blocksOverlap(newPosition, otherBlock)) {
                return false
            }
        }
        
        return true
    }

    private fun blocksOverlap(block1: Block, block2: Block): Boolean {
        val block1Cols = block1.col until (block1.col + if (block1.orientation == com.vai.vaidebet.vaibrazil.domain.model.Orientation.HORIZONTAL || block1.orientation == com.vai.vaidebet.vaibrazil.domain.model.Orientation.SQUARE) block1.length else 1)
        val block1Rows = block1.row until (block1.row + if (block1.orientation == com.vai.vaidebet.vaibrazil.domain.model.Orientation.VERTICAL || block1.orientation == com.vai.vaidebet.vaibrazil.domain.model.Orientation.SQUARE) block1.length else 1)
        val block2Cols = block2.col until (block2.col + if (block2.orientation == com.vai.vaidebet.vaibrazil.domain.model.Orientation.HORIZONTAL || block2.orientation == com.vai.vaidebet.vaibrazil.domain.model.Orientation.SQUARE) block2.length else 1)
        val block2Rows = block2.row until (block2.row + if (block2.orientation == com.vai.vaidebet.vaibrazil.domain.model.Orientation.VERTICAL || block2.orientation == com.vai.vaidebet.vaibrazil.domain.model.Orientation.SQUARE) block2.length else 1)
        
        return block1Cols.intersect(block2Cols).isNotEmpty() && block1Rows.intersect(block2Rows).isNotEmpty()
    }

    private fun checkWinCondition(block: Block, solutionMoves: Int): Boolean {
        // Win condition: target block reaches the right edge
        return block.isTarget && block.col + block.length == 6
    }

    private fun calculateStars(moves: Int, solutionMoves: Int): Int {
        return when {
            moves <= solutionMoves -> 3
            moves <= solutionMoves * 1.5 -> 2
            else -> 1
        }
    }

    fun resetLevel() {
        loadLevel()
    }

    fun toggleGrid() {
        _uiState.value = _uiState.value.copy(showGrid = !_uiState.value.showGrid)
    }
}