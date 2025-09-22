package com.vai.vai.vai.vaidebet.vaibrazi.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.vai.vai.vai.vaidebet.vaibrazi.presentation.GameViewModel
import com.vai.vai.vai.vaidebet.vaibrazi.presentation.GameViewModelFactory
import com.vai.vai.vai.vaidebet.vaibrazi.R

import kotlin.random.Random

@Composable
fun GameScreen(level: Int, navController: NavController, gameViewModel: GameViewModel = viewModel(factory = GameViewModelFactory(
    level
)
)) {
    val gameState by gameViewModel.gameState.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.gamescreen_bg),
            contentDescription = "Football field background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        AnimatedVisibility(visible = gameState.isGameWon) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                val particles = remember {
                    List(100) {
                        Offset(
                            x = Random.nextFloat() * 2000 - 1000,
                            y = Random.nextFloat() * 2000 - 1000
                        )
                    }
                }
                Canvas(modifier = Modifier.fillMaxSize()) {
                    particles.forEach {
                        drawCircle(
                            color = Color(Random.nextInt(256), Random.nextInt(256), Random.nextInt(256)),
                            radius = Random.nextFloat() * 20,
                            center = it
                        )
                    }
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "You Win!", style = MaterialTheme.typography.headlineLarge)
                    ThemedButton(text = "Play Again", onClick = { gameViewModel.resetGame() })
                }
            }
        }
        if (!gameState.isGameWon) {
            Column(modifier = Modifier.fillMaxSize()) {
                Row {
                    ThemedButton(text = "Back", onClick = { navController.popBackStack() })
                    Spacer(modifier = Modifier.width(16.dp))
                    ThemedButton(text = "Reset", onClick = { gameViewModel.resetGame() })
                    Spacer(modifier = Modifier.width(16.dp))
                    ThemedButton(text = "Toggle Grid", onClick = { gameViewModel.toggleGrid() })
                }
                Text(text = "Level: $level")
                Text(text = "Moves: ${gameState.moves}")
                GameBoard(gameState = gameState, gameViewModel = gameViewModel)
            }
        }
    }
}