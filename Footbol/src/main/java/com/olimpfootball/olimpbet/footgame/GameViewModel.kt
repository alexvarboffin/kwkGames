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
    val handPosition: Int? = null,
    val isAnimating: Boolean = false,
    val animatedBallPosition: Int? = null,
    val animatedHandPosition: Int? = null,
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

            _uiState.update { it.copy(isAnimating = true, gameResult = null) } // Reset gameResult and start animation

            val totalTargets = _uiState.value.targets.size
            val positions = (0 until totalTargets).shuffled().take(3)

            val ballPos = positions[0]
            val handPos = positions[1] // Use one position for both hands

            // Store animated positions
            _uiState.update {
                it.copy(
                    animatedBallPosition = ballPos,
                    animatedHandPosition = handPos
                )
            }

            // Simulate animation duration
            kotlinx.coroutines.delay(1500L) // 1.5 seconds for animation

            val isLoss = (ballPos == handPos)

            if (isLoss) {
                // Loss
                _soundEvent.emit(SoundEvent.GameOver)
                _uiState.update {
                    it.copy(
                        gameResult = GameResult.LOSS,
                        balance = it.balance - it.betAmount,
                                                ballPosition = ballPos, // Final position
                        handPosition = handPos, // Final position
                        isAnimating = false // Animation finished
                    )
                }
            } else {
                // Win
                val winAmount = _uiState.value.betAmount * _uiState.value.multiplier
                _uiState.update {
                    it.copy(
                        gameResult = GameResult.WIN,
                        balance = it.balance + winAmount,
                                                ballPosition = ballPos, // Final position
                        handPosition = handPos, // Final position
                        isAnimating = false // Animation finished
                    )
                }
            }
        }
    }
}