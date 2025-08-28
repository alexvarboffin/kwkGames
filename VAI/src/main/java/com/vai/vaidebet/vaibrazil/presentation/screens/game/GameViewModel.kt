package com.vai.vaidebet.vaibrazil.presentation.screens.game

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vai.vaidebet.vaibrazil.domain.model.Block
import com.vai.vaidebet.vaibrazil.domain.model.GameLevel
import com.vai.vaidebet.vaibrazil.domain.model.Orientation

import com.vai.vaidebet.vaibrazil.domain.usecase.GetLevelUseCase
import com.vai.vaidebet.vaibrazil.domain.usecase.UpdateHighestUnlockedLevelUseCase
import com.vai.vaidebet.vaibrazil.domain.usecase.UpdateStarsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

class GameViewModel(
    private val getLevelUseCase: GetLevelUseCase,
    private val updateHighestUnlockedLevelUseCase: UpdateHighestUnlockedLevelUseCase,
    private val updateStarsUseCase: UpdateStarsUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val levelId: Int = savedStateHandle.get<Int>("levelId")!!

    private val _uiState = MutableStateFlow<GameUiState>(GameUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private var initialLevel: GameLevel? = null
    private var moveCount = 0
    private var showGrid = true

    // Store current pixel offsets for smooth dragging
    private val _blockPixelOffsets = MutableStateFlow<Map<Int, Pair<Float, Float>>>(emptyMap())
    val blockPixelOffsets = _blockPixelOffsets.asStateFlow()

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
                    // Initialize pixel offsets for each block
                    val initialOffsets = mutableMapOf<Int, Pair<Float, Float>>()
                    level.blocks.forEach { block ->
                        initialOffsets[block.id] = Pair(0f, 0f)
                    }
                    _blockPixelOffsets.value = initialOffsets
                    _uiState.value = GameUiState.Success(level, 0, showGrid)
                } else {
                    _uiState.value = GameUiState.Error("Level not found")
                }
            } catch (e: Exception) {
                _uiState.value = GameUiState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun onBlockDrag(block: Block, dragX: Float, dragY: Float, gridSize: Float) {
        val currentState = _uiState.value
        if (currentState is GameUiState.Success) {
            val currentOffset = _blockPixelOffsets.value[block.id] ?: Pair(0f, 0f)
            var newOffsetX = currentOffset.first + dragX
            var newOffsetY = currentOffset.second + dragY

            val newBlocks = currentState.level.blocks.toMutableList()
            val blockIndex = newBlocks.indexOf(block)
            if (blockIndex != -1) {
                val currentBlock = newBlocks[blockIndex]
                var newBlock: Block = currentBlock

                if (currentBlock.orientation == Orientation.HORIZONTAL) {
                    val newColFloat = currentBlock.col + (newOffsetX / gridSize)
                    val newCol = newColFloat.roundToInt().coerceIn(0, currentState.level.gridWidth - currentBlock.length)
                    newBlock = currentBlock.copy(col = newCol)
                } else if (currentBlock.orientation == Orientation.VERTICAL) {
                    val newRowFloat = currentBlock.row + (newOffsetY / gridSize)
                    val newRow = newRowFloat.roundToInt().coerceIn(0, currentState.level.gridHeight - currentBlock.length)
                    newBlock = currentBlock.copy(row = newRow)
                }

                if (isValidMove(newBlock, newBlocks, currentState.level.gridWidth, currentState.level.gridHeight)) {
                    newBlocks[blockIndex] = newBlock
                    // Only increment move count if the block actually moved to a new grid cell
                    if (newBlock.col != currentBlock.col || newBlock.row != currentBlock.row) {
                        moveCount++
                        // Reset pixel offset after a successful grid move
                        _blockPixelOffsets.value = _blockPixelOffsets.value.toMutableMap().apply { this[block.id] = Pair(0f, 0f) }
                    } else {
                        // Update pixel offset for smooth dragging if not snapped to a new grid cell
                        _blockPixelOffsets.value = _blockPixelOffsets.value.toMutableMap().apply { this[block.id] = Pair(newOffsetX, newOffsetY) }
                    }
                    _uiState.value = currentState.copy(
                        level = currentState.level.copy(blocks = newBlocks),
                        moveCount = moveCount
                    )
                    checkWinCondition(newBlock, currentState.level.solutionMoves, currentState.level.exitX, currentState.level.exitY)
                } else {
                    // If move is invalid, revert pixel offset
                    _blockPixelOffsets.value = _blockPixelOffsets.value.toMutableMap().apply { this[block.id] = Pair(0f, 0f) }
                }
            }
        }
    }

    fun onBlockDragEnd(block: Block) {
        // Reset pixel offset after drag ends
        _blockPixelOffsets.value = _blockPixelOffsets.value.toMutableMap().apply { this[block.id] = Pair(0f, 0f) }
    }


    private fun isValidMove(movedBlock: Block, blocks: List<Block>, gridWidth: Int, gridHeight: Int): Boolean {
        // Boundary checks
        if (movedBlock.orientation == Orientation.HORIZONTAL) {
            if (movedBlock.col < 0 || movedBlock.col + movedBlock.length > gridWidth) return false
        } else if (movedBlock.orientation == Orientation.VERTICAL) { // Only vertical blocks move vertically
            if (movedBlock.row < 0 || movedBlock.row + movedBlock.length > gridHeight) return false
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
        val b1Cols = b1.col until (b1.col + if (b1.orientation == Orientation.HORIZONTAL) b1.length else 1)
        val b1Rows = b1.row until (b1.row + if (b1.orientation == Orientation.VERTICAL) b1.length else 1)
        val b2Cols = b2.col until (b2.col + if (b2.orientation == Orientation.HORIZONTAL) b2.length else 1)
        val b2Rows = b2.row until (b2.row + if (b2.orientation == Orientation.VERTICAL) b2.length else 1)

        return b1Cols.intersect(b2Cols).isNotEmpty() && b1Rows.intersect(b2Rows).isNotEmpty()
    }


    private fun checkWinCondition(movedBlock: Block, solutionMoves: Int, exitX: Int, exitY: Int) {
        if (movedBlock.isTarget && movedBlock.col + movedBlock.length == exitX && movedBlock.row == exitY) {
            val stars = when {
                moveCount <= solutionMoves -> 3
                moveCount <= solutionMoves * 1.5 -> 2
                else -> 1
            }
            viewModelScope.launch {
                updateStarsUseCase(levelId, stars)
                updateHighestUnlockedLevelUseCase(levelId + 1)
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
