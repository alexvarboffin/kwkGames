package com.cricjojo.cricmost

import android.content.Context
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


import kotlinx.coroutines.delay
import androidx.core.content.edit

@Composable
fun GameScreen(isEndlessMode: Boolean, level: Int, onBack: () -> Unit) {
    val context = LocalContext.current
    var score by remember { mutableIntStateOf(0) }
    var bestScore by remember { mutableIntStateOf(0) } // Placeholder
    var moves by remember { mutableIntStateOf(24) }
    var timeLeft by remember { mutableFloatStateOf(ENDLESS_MODE_TIME) }
    var showTimeoutDialog by remember { mutableStateOf(false) }
    var showLevelEndDialog by remember { mutableStateOf(false) }

    val goalScore = 1000 * level // Example goal

    fun onGameEnd() {
        if (!isEndlessMode) {
            showLevelEndDialog = true
            if (score >= goalScore) {
                val sharedPrefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                val unlockedLevel = sharedPrefs.getInt(KEY_UNLOCKED_LEVEL, 3)
                if (level == unlockedLevel) {
                    sharedPrefs.edit { putInt(KEY_UNLOCKED_LEVEL, unlockedLevel + 1) }
                }
            }
        }
    }

    if (isEndlessMode) {
        LaunchedEffect(Unit) {
            while (timeLeft > 0) {
                delay(1000)
                timeLeft--
            }
            if (score > bestScore) bestScore = score
            showTimeoutDialog = true
        }
    } else {
        if (moves <= 0) onGameEnd()
    }

    val animatedTime by animateFloatAsState(targetValue = timeLeft / ENDLESS_MODE_TIME, label = "")

    Box(modifier = Modifier.Companion.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.ic_game_background),
            contentDescription = "Game Background",
            contentScale = ContentScale.Companion.Crop,
            modifier = Modifier.Companion.fillMaxSize()
        )
        Column(
            modifier = Modifier.Companion
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.Companion.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.full_name),
                fontSize = 32.sp,
                fontWeight = FontWeight.Companion.Bold,
                color = Color.Companion.White,
                modifier = Modifier.Companion.padding(bottom = 16.dp)
            )
            if (isEndlessMode) {
                Row(
                    modifier = Modifier.Companion.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Text("Best: $bestScore", color = Color.Companion.White); Text(
                    "Score: $score",
                    color = Color.Companion.White
                )
                }
            } else {
                Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                    Row(
                        modifier = Modifier.Companion.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        Text("Moves: $moves", color = Color.Companion.White); Text(
                        "Goal: $goalScore",
                        color = Color.Companion.White
                    )
                    }
                    Text("Score: $score", color = Color.Companion.White)
                }
            }
            Spacer(modifier = Modifier.Companion.height(32.dp))
            GameBoard {
                score += 100
                if (isEndlessMode) timeLeft = (timeLeft + 1).coerceAtMost(ENDLESS_MODE_TIME)
                else moves--
            }
            if (isEndlessMode) {
                Spacer(modifier = Modifier.Companion.weight(1f))
                LinearProgressIndicator(
                    progress = { animatedTime },
                    modifier = Modifier.Companion
                        .fillMaxWidth()
                        .height(20.dp)
                )
            }
        }
        if (showTimeoutDialog) {
            TimeoutDialog(score = score) { onBack() }
        }
        if (showLevelEndDialog) {
            LevelEndDialog(
                score = score,
                goal = goalScore,
                onDismiss = { onBack() })
        }
    }
}