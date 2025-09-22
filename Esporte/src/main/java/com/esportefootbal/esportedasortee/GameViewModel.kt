package com.esportefootbal.esportedasortee

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

class GameViewModel : ViewModel() {
    var score by mutableStateOf(0)
    var shotsLeft by mutableStateOf(5)
    var message by mutableStateOf<String?>(null)
    var isPaused by mutableStateOf(false)
    var isGameOver by mutableStateOf(false)

    var goalkeeperAnimState by mutableStateOf("gk_idle")
    var playerKicking by mutableStateOf(false)

    var ballTargetPosition by mutableStateOf(Offset(680f, 550f))
    var ballTargetScale by mutableStateOf(1f)
    var ballFrame by mutableStateOf(0)
    var ballRotation by mutableStateOf(0f)

    private var isKicking = false

    fun kick(dragAmount: Offset, context: Context) {
        if (isKicking || shotsLeft <= 0 || isPaused || isGameOver) return

        viewModelScope.launch {
            isKicking = true
            playerKicking = true
            SoundManager.playSound(context, R.raw.kick)
            delay(30 * 7) // Wait for the kick frame

            val distance = dragAmount.getDistance()
            var force = distance * 0.1f // Simplified force calculation

            val targetX = (680f + dragAmount.x * force).coerceIn(330f, 1030f)
            val targetY = (550f + dragAmount.y * force).coerceIn(200f, 450f)

            val goalArea = predictGoalArea(Offset(targetX, targetY))
            val isGoal = Random.nextInt(100) < 70 // 70% chance of goal

            if (isGoal) {
                goalkeeperAnimState = getWrongGoalkeeperAnim(goalArea)
                message = "GOAL!"
                score += 100
                SoundManager.playSound(context, R.raw.goal)
            } else {
                goalkeeperAnimState = getGoalkeeperAnimForArea(goalArea)
                message = "SAVED!"
                SoundManager.playSound(context, R.raw.ball_saved)
            }

            ballTargetPosition = Offset(targetX, targetY)
            ballTargetScale = 0.4f
            startBallAnimation(dragAmount)

            delay(1500)
            ballTargetPosition = Offset(680f, 550f)
            ballTargetScale = 1f
            shotsLeft--
            isKicking = false
            goalkeeperAnimState = "gk_idle"
            message = null

            if (shotsLeft == 0) {
                isGameOver = true
            }
        }
    }

    private fun startBallAnimation(dragAmount: Offset) {
        viewModelScope.launch {
            val rotationSpeed = (dragAmount.x / 50f).coerceIn(-10f, 10f)
            while(isKicking) {
                ballRotation += rotationSpeed
                ballFrame = (ballRotation / 20).toInt() % 7
                if(ballFrame < 0) ballFrame += 7
                delay(20)
            }
        }
    }

    fun restartGame() {
        score = 0
        shotsLeft = 5
        message = null
        isKicking = false
        isGameOver = false
        isPaused = false
        goalkeeperAnimState = "gk_idle"
        ballTargetPosition = Offset(680f, 550f)
        ballTargetScale = 1f
        ballFrame = 0
        ballRotation = 0f
    }

    fun togglePause() {
        isPaused = !isPaused
    }

    private fun predictGoalArea(target: Offset): Int {
        val col = ((target.x - 330f) / (700f / 5)).toInt().coerceIn(0, 4)
        val row = ((target.y - 200f) / (250f / 3)).toInt().coerceIn(0, 2)
        return row * 5 + col
    }

    private fun getGoalkeeperAnimForArea(area: Int): String {
        return when (area) {
            0 -> "gk_save_up_left"
            1, 2 -> "gk_save_center_up"
            3, 4 -> "gk_save_up_right"
            5 -> "gk_save_left"
            6, 7, 8 -> "gk_save_center"
            9 -> "gk_save_right"
            10 -> "gk_save_down_left"
            11, 12 -> "gk_save_center_down"
            13, 14 -> "gk_save_down_right"
            else -> "gk_idle"
        }
    }

    private fun getWrongGoalkeeperAnim(area: Int): String {
        val correctAnim = getGoalkeeperAnimForArea(area)
        val animations = listOf("gk_save_up_left", "gk_save_up_right", "gk_save_left", "gk_save_right", "gk_save_down_left", "gk_save_down_right")
        var randomAnim = animations.random()
        while (randomAnim == correctAnim) {
            randomAnim = animations.random()
        }
        return randomAnim
    }
}
