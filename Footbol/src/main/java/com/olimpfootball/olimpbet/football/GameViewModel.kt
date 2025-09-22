package com.olimpfootball.olimpbet.footgame

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

// Represents a single spot on the grid.
data class Target(val id: Int)

enum class GameResult { WIN, LOSS }

sealed class SoundEvent {
    object Kick : SoundEvent()
    object GameOver : SoundEvent()
    object ButtonClick : SoundEvent()
}

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
    val handPosition: Int? = null, // Final hand position
    val currentHandPosition: Int? = 8, // Animated hand position
    val isAnimating: Boolean = false,
    val animatedBallPosition: Int? = null, // Animated ball position
    val showSettingsDialog: Boolean = false
)

class GameViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(GameState())
    val uiState: StateFlow<GameState> = _uiState.asStateFlow()

    private val _soundEvent = MutableSharedFlow<SoundEvent>()
    val soundEvent: SharedFlow<SoundEvent> = _soundEvent.asSharedFlow()

    fun resetGame() {
        _uiState.update {
            it.copy(
                gameResult = null,
                ballPosition = null,
                handPosition = null
            )
        }
    }

    fun changeBetAmount(multiply: Boolean) {
        viewModelScope.launch { _soundEvent.emit(SoundEvent.ButtonClick) }
        val currentBet = _uiState.value.betAmount
        val newBet = if (multiply) currentBet * 2 else currentBet / 2
        if (newBet > 0 && newBet <= _uiState.value.balance) {
            _uiState.update { it.copy(betAmount = newBet) }
        }
    }

    fun toggleSeriesMode() {
        viewModelScope.launch { _soundEvent.emit(SoundEvent.ButtonClick) }
        _uiState.update { it.copy(isSeriesMode = !it.isSeriesMode) }
    }

    fun toggleSettingsDialog() {
        viewModelScope.launch { _soundEvent.emit(SoundEvent.ButtonClick) }
        _uiState.update { it.copy(showSettingsDialog = !it.showSettingsDialog) }
    }

    // The core slot machine logic
    fun onBumpClicked() {
        // Don't run if a game is already finished or animating
        if (_uiState.value.gameResult != null || _uiState.value.isAnimating) return

        viewModelScope.launch {
            _soundEvent.emit(SoundEvent.Kick)

            // Set initial state for animation: hands in center, ball not visible
            _uiState.update {
                it.copy(
                    isAnimating = true,
                    gameResult = null,
                    currentHandPosition = 8, // Center position for hands (assuming 18 targets, 6x3 grid, 8 is center-ish)
                    ballPosition = null, // Hide ball initially
                    handPosition = null // Final hand position will be set later
                )
            }

            val totalTargets = _uiState.value.targets.size
            val positions = (0 until totalTargets).shuffled().take(3) // Still need 3 for ball and hand, even if hand is one pos

            val ballPos = positions[0]
            val finalHandPos = positions[1] // This will be the final hand position

            // Short delay before hands start moving
            kotlinx.coroutines.delay(200L)

            // Animate hands to final position
            _uiState.update {
                it.copy(
                    currentHandPosition = finalHandPos // Hands move to their final position
                )
            }

            // Simulate remaining animation duration
            kotlinx.coroutines.delay(1300L) // Total animation time 1.5s (200ms + 1300ms)

            val lossZone = mutableListOf(finalHandPos)
            if (finalHandPos > 0) lossZone.add(finalHandPos - 1)
            if (finalHandPos < totalTargets - 1) lossZone.add(finalHandPos + 1)

            val isLoss = ballPos in lossZone

            if (isLoss) {
                // Loss
                _soundEvent.emit(SoundEvent.GameOver)
                _uiState.update {
                    it.copy(
                        gameResult = GameResult.LOSS,
                        balance = it.balance - it.betAmount,
                        ballPosition = ballPos, // Final ball position
                        handPosition = finalHandPos, // Final hand position
                        isAnimating = false, // Animation finished
                        currentHandPosition = null // Clear current hand position after animation
                    )
                }
            } else {
                // Win scenario
                val winAmount = _uiState.value.betAmount * _uiState.value.multiplier

                // Adjust handPos to be close to ballPos for visual effect
                val possibleHandPositions = mutableListOf<Int>()
                if (ballPos > 0) possibleHandPositions.add(ballPos - 1)
                if (ballPos < totalTargets - 1) possibleHandPositions.add(ballPos + 1)

                val adjustedHandPos = if (possibleHandPositions.isNotEmpty()) {
                    possibleHandPositions.random()
                } else {
                    finalHandPos // Keep the original handPos if no adjacent valid positions
                }

                _uiState.update {
                    it.copy(
                        gameResult = GameResult.WIN,
                        balance = it.balance + winAmount,
                        ballPosition = ballPos, // Final ball position
                        handPosition = adjustedHandPos, // Adjusted hand position for visual effect
                        isAnimating = false, // Animation finished
                        currentHandPosition = null // Clear current hand position after animation
                    )
                }
            }
        }
    }
}