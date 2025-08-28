package com.vai.vaidebet.vaibrazil.presentation.screens.game

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vai.vaidebet.vaibrazil.domain.model.Block
import com.vai.vaidebet.vaibrazil.domain.model.GameLevel
import com.vai.vaidebet.vaibrazil.domain.model.Orientation
import com.vai.vaidebet.vaibrazil.domain.usecase.GetLevelUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class GameViewModel(
    private val getLevelUseCase: GetLevelUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val levelId: Int = savedStateHandle.get<Int>("levelId")!!

    private val _uiState = MutableStateFlow<GameUiState>(GameUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private var initialLevel: GameLevel? = null
    private var moveCount = 0
    private var showGrid = true

    init {
        loadLevel()
    }

    fun loadLevel() {
        viewModelScope.launch {
            _uiState.value = GameUiState.Loading
            try {
                val level = getLevelUseCase(levelId)
                if (level != null) {
                    initialLevel = level
                    moveCount = 0
                    _uiState.value = GameUiState.Success(level, 0, showGrid)
                } else {
                    _uiState.value = GameUiState.Error("Level not found")
                }
            } catch (e: Exception) {
                _uiState.value = GameUiState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun moveBlock(block: Block, dragAmount: Float) {
        val currentState = _uiState.value
        if (currentState is GameUiState.Success) {
            val gridSize = 100 // Assuming a grid size for now
            val dragInGrid = (dragAmount / gridSize).toInt()

            if (dragInGrid == 0) return

            val newBlocks = currentState.level.blocks.toMutableList()
            val blockIndex = newBlocks.indexOf(block)
            if (blockIndex != -1) {
                val newBlock = if (block.orientation == Orientation.HORIZONTAL) {
                    block.copy(col = block.col + dragInGrid)
                } else {
                    block.copy(row = block.row + dragInGrid)
                }

                if (isValidMove(newBlock, newBlocks)) {
                    newBlocks[blockIndex] = newBlock
                    moveCount++
                    _uiState.value = currentState.copy(
                        level = currentState.level.copy(blocks = newBlocks),
                        moveCount = moveCount
                    )
                    checkWinCondition(newBlock, currentState.level.solutionMoves)
                }
            }
        }
    }

    private fun isValidMove(movedBlock: Block, blocks: List<Block>): Boolean {
        // Boundary checks
        if (movedBlock.orientation == Orientation.HORIZONTAL) {
            if (movedBlock.col < 0 || movedBlock.col + movedBlock.length > 6) return false
        } else {
            if (movedBlock.row < 0 || movedBlock.row + movedBlock.length > 6) return false
        }

        // Collision checks
        for (block in blocks) {
            if (block.id != movedBlock.id) {
                if (blocksOverlap(movedBlock, block)) {
                    return false
                }
            }
        }
        return true
    }

    private fun blocksOverlap(b1: Block, b2: Block): Boolean {
        val b1Cols = b1.col until (b1.col + if (b1.orientation == Orientation.HORIZONTAL || b1.orientation == Orientation.SQUARE) b1.length else 1)
        val b1Rows = b1.row until (b1.row + if (b1.orientation == Orientation.VERTICAL || b1.orientation == Orientation.SQUARE) b1.length else 1)
        val b2Cols = b2.col until (b2.col + if (b2.orientation == Orientation.HORIZONTAL || b2.orientation == Orientation.SQUARE) b2.length else 1)
        val b2Rows = b2.row until (b2.row + if (b2.orientation == Orientation.VERTICAL || b2.orientation == Orientation.SQUARE) b2.length else 1)

        return b1Cols.intersect(b2Cols).isNotEmpty() && b1Rows.intersect(b2Rows).isNotEmpty()
    }


    private fun checkWinCondition(movedBlock: Block, solutionMoves: Int) {
        if (movedBlock.isTarget && movedBlock.col + movedBlock.length == 6) {
            val stars = when {
                moveCount <= solutionMoves -> 3
                moveCount <= solutionMoves * 1.5 -> 2
                else -> 1
            }
            _uiState.value = GameUiState.Won(moveCount, stars)
        }
    }

    fun resetLevel() {
        initialLevel?.let {
            moveCount = 0
            _uiState.value = GameUiState.Success(it, 0, showGrid)
        }
    }

    fun toggleGrid() {
        showGrid = !showGrid
        val currentState = _uiState.value
        if (currentState is GameUiState.Success) {
            _uiState.value = currentState.copy(showGrid = showGrid)
        }
    }
}

sealed class GameUiState {
    object Loading : GameUiState()
    data class Success(val level: GameLevel, val moveCount: Int, val showGrid: Boolean) : GameUiState()
    data class Error(val message: String) : GameUiState()
    data class Won(val finalMoveCount: Int, val stars: Int) : GameUiState()
}
