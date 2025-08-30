package com.esportedasorte.esportefootbal

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun GameUI(viewModel: GameViewModel, onPauseClick: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Score: ${viewModel.score}", style = TextStyle(color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold))
            Button(onClick = onPauseClick) {
                Text("PAUSE", fontSize = 14.sp)
            }
            Text("Shots Left: ${viewModel.shotsLeft}", style = TextStyle(color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold))
        }

        viewModel.message?.let {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(it, style = TextStyle(color = Color.White, fontSize = 48.sp, fontWeight = FontWeight.Bold))
            }
        }

        if (viewModel.isGameOver) {
            GameOverPanel(viewModel = viewModel)
        }

        if (viewModel.isPaused) {
            PausePanel(onResumeClick = { viewModel.togglePause() })
        }
    }
}

@Composable
fun GameOverPanel(viewModel: GameViewModel) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("GAME OVER", style = TextStyle(color = Color.White, fontSize = 64.sp, fontWeight = FontWeight.Bold))
            Text("Final Score: ${viewModel.score}", style = TextStyle(color = Color.White, fontSize = 32.sp, fontWeight = FontWeight.Bold))
            Spacer(modifier = Modifier.height(32.dp))
            Button(onClick = { viewModel.restartGame() }) {
                Text("RESTART", fontSize = 14.sp)
            }
        }
    }
}

@Composable
fun PausePanel(onResumeClick: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("PAUSED", style = TextStyle(color = Color.White, fontSize = 64.sp, fontWeight = FontWeight.Bold))
            Spacer(modifier = Modifier.height(32.dp))
            Button(onClick = onResumeClick) {
                Text("RESUME", fontSize = 14.sp)
            }
        }
    }
}