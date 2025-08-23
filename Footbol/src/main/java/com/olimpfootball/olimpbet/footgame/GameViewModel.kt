package com.olimpfootball.olimpbet.footgame

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

// Represents a single spot on the grid.
data class Target(val id: Int)

enum class GameResult { WIN, LOSS }

// Data class to hold the entire state of the game screen
data class GameState(
    val targets: List<Target> = List(18) { Target(id = it) }, // Static grid of 18 targets
    val betAmount: Double = 100.0,
    val multiplier: Double = 1.0,
    val balance: Double = 2000.0,
    val isSeriesMode: Boolean = false,

    // Results of the spin
    val gameResult: GameResult? = null,
    val ballPosition: Int? = null,
    val leftHandPosition: Int? = null,
    val rightHandPosition: Int? = null
)

class GameViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(GameState())
    val uiState: StateFlow<GameState> = _uiState.asStateFlow()

    // This function now simply resets the result state, preparing for the next spin.
    fun resetGame() {
        _uiState.update {
            it.copy(
                gameResult = null,
                ballPosition = null,
                leftHandPosition = null,
                rightHandPosition = null
            )
        }
    }

    fun changeBetAmount(multiply: Boolean) {
        val currentBet = _uiState.value.betAmount
        val newBet = if (multiply) currentBet * 2 else currentBet / 2
        if (newBet > 0 && newBet <= _uiState.value.balance) {
            _uiState.update { it.copy(betAmount = newBet) }
        }
    }

    fun toggleSeriesMode() {
        _uiState.update { it.copy(isSeriesMode = !it.isSeriesMode) }
    }

    // The core slot machine logic
    fun onBumpClicked() {
        // Don't run if a game is already finished and waiting for reset
        if (_uiState.value.gameResult != null) return

        viewModelScope.launch {
            val totalTargets = _uiState.value.targets.size
            val positions = (0 until totalTargets).shuffled().take(3)

            val ballPos = positions[0]
            val leftHandPos = positions[1]
            val rightHandPos = positions[2]

            val isLoss = (ballPos == leftHandPos || ballPos == rightHandPos)

            if (isLoss) {
                // Loss
                _uiState.update {
                    it.copy(
                        gameResult = GameResult.LOSS,
                        balance = it.balance - it.betAmount,
                        ballPosition = ballPos,
                        leftHandPosition = leftHandPos,
                        rightHandPosition = rightHandPos
                    )
                }
            } else {
                // Win
                val winAmount = _uiState.value.betAmount * _uiState.value.multiplier
                _uiState.update {
                    it.copy(
                        gameResult = GameResult.WIN,
                        balance = it.balance + winAmount,
                        ballPosition = ballPos,
                        leftHandPosition = leftHandPos,
                        rightHandPosition = rightHandPos
                    )
                }
            }
        }
    }
}