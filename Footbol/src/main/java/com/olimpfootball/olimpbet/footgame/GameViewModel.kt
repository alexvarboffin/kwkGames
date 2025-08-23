
package com.olimpfootball.olimpbet.footgame

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.random.Random

// Data class to hold the entire state of the game screen
data class GameState(
    val targets: List<Target> = emptyList(),
    val selectedTargetId: Int? = null,
    val betAmount: Double = 100.0,
    val multiplier: Double = 1.0,
    val balance: Double = 2000.0,
    val gameResult: GameResult? = null // null, WIN, or LOSS
)

// Represents a target in the goal
data class Target(val id: Int, val isGoalkeeper: Boolean = false)

enum class GameResult { WIN, LOSS }

class GameViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(GameState())
    val uiState: StateFlow<GameState> = _uiState.asStateFlow()

    init {
        resetGame()
    }

    fun resetGame() {
        viewModelScope.launch {
            val totalTargets = 12
            val goalkeeperPositions = List(2) { Random.nextInt(0, totalTargets) }.toSet()
            val newTargets = List(totalTargets) { Target(id = it, isGoalkeeper = goalkeeperPositions.contains(it)) }
            _uiState.update {
                it.copy(
                    targets = newTargets,
                    selectedTargetId = null,
                    gameResult = null,
                    multiplier = 1.0
                )
            }
        }
    }

    fun onTargetSelected(targetId: Int) {
        _uiState.update { it.copy(selectedTargetId = targetId) }
    }

    fun changeBetAmount(multiply: Boolean) {
        val currentBet = _uiState.value.betAmount
        val newBet = if (multiply) currentBet * 2 else currentBet / 2
        if (newBet > 0 && newBet <= _uiState.value.balance) {
            _uiState.update { it.copy(betAmount = newBet) }
        }
    }

    fun onBumpClicked() {
        val selectedId = _uiState.value.selectedTargetId ?: return
        val target = _uiState.value.targets.find { it.id == selectedId } ?: return

        if (target.isGoalkeeper) {
            // Loss
            _uiState.update {
                it.copy(
                    gameResult = GameResult.LOSS,
                    balance = it.balance - it.betAmount
                )
            }
        } else {
            // Win
            val winAmount = _uiState.value.betAmount * _uiState.value.multiplier
            _uiState.update {
                it.copy(
                    gameResult = GameResult.WIN,
                    balance = it.balance + winAmount
                )
            }
        }
        // Here you might want to show the result for a moment before resetting
        // For now, we can just reset directly or wait for user input.
    }
}
