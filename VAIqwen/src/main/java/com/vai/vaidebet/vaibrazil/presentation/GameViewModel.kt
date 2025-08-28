package com.vai.vaidebet.vaibrazil.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vai.vaidebet.vaibrazil.data.LevelRepository
import com.vai.vaidebet.vaibrazil.domain.model.Block
import com.vai.vaidebet.vaibrazil.domain.model.GameLevel
import com.vai.vaidebet.vaibrazil.domain.usecase.GetLevelUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class GameViewModel(private val levelId: Int) : ViewModel() {
    private val repository = LevelRepository()
    private val getLevelUseCase = GetLevelUseCase(repository)
    
    private val _uiState = MutableStateFlow<GameUiState>(GameUiState.Loading)
    val uiState: StateFlow<GameUiState> = _uiState
    
    private var currentLevel: GameLevel? = null
    private var movesCount = 0

    init {
        loadLevel()
    }

    private fun loadLevel() {
        viewModelScope.launch {
            try {
                val level = getLevelUseCase(levelId)
                if (level != null) {
                    currentLevel = level
                    _uiState.value = GameUiState.Success(
                        level = level,
                        blocks = level.blocks.toMutableList(),
                        moves = movesCount
                    )
                } else {
                    _uiState.value = GameUiState.Error("Уровень не найден")
                }
            } catch (e: Exception) {
                _uiState.value = GameUiState.Error(e.message ?: "Неизвестная ошибка")
            }
        }
    }
    
    fun moveBlock(blockId: Int, newRow: Int, newCol: Int) {
        val currentState = _uiState.value
        if (currentState is GameUiState.Success) {
            val updatedBlocks = currentState.blocks.map { block ->
                if (block.id == blockId) {
                    block.copy(row = newRow, col = newCol)
                } else {
                    block
                }
            }.toMutableList()
            
            movesCount++
            
            // Проверяем, выиграл ли игрок
            val targetBlock = updatedBlocks.find { it.isTarget }!!
            val isWon = targetBlock.col + targetBlock.length == 6
            
            if (isWon) {
                _uiState.value = GameUiState.Won(
                    level = currentLevel!!,
                    moves = movesCount,
                    stars = calculateStars(movesCount, currentLevel!!.minMoves)
                )
            } else {
                _uiState.value = currentState.copy(
                    blocks = updatedBlocks,
                    moves = movesCount
                )
            }
        }
    }
    
    private fun calculateStars(moves: Int, minMoves: Int): Int {
        return when {
            moves <= minMoves -> 3
            moves <= minMoves * 1.5 -> 2
            else -> 1
        }
    }
}

sealed class GameUiState {
    object Loading : GameUiState()
    data class Success(
        val level: GameLevel,
        val blocks: MutableList<Block>,
        val moves: Int
    ) : GameUiState()
    data class Won(
        val level: GameLevel,
        val moves: Int,
        val stars: Int
    ) : GameUiState()
    data class Error(val message: String) : GameUiState()
}