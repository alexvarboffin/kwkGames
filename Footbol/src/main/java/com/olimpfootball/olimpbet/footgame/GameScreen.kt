package com.olimpfootball.olimpbet.footgame

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.olimpfootball.olimpbet.footgame.R

@Composable
fun GameScreen(gameViewModel: GameViewModel = viewModel(), onBack: () -> Unit) {
    val uiState by gameViewModel.uiState.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        // Background Layer
        Column(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = R.drawable.ic_game_background),
                contentDescription = "Stadium Background",
                modifier = Modifier.fillMaxWidth().weight(1f),
                contentScale = ContentScale.FillWidth
            )
            Box(
                modifier = Modifier.fillMaxWidth().weight(1f).background(Color(0xFF3A8A53))
            )
        }

        // Foreground UI Layer
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            TopBar(balance = uiState.balance)
            TacticField(
                targets = uiState.targets,
                ballPosition = uiState.ballPosition,
                leftHandPosition = uiState.leftHandPosition,
                rightHandPosition = uiState.rightHandPosition
            )
            GameControls(
                betAmount = uiState.betAmount,
                multiplier = uiState.multiplier,
                isSeriesMode = uiState.isSeriesMode,
                onBetAmountChange = { isMultiply -> gameViewModel.changeBetAmount(isMultiply) },
                onBumpClicked = { gameViewModel.onBumpClicked() },
                onToggleSeries = { gameViewModel.toggleSeriesMode() }
            )
        }

        // Show result dialog
        if (uiState.gameResult != null) {
            GameResultDialog(
                result = uiState.gameResult!!,
                onPlayAgain = { gameViewModel.resetGame() }
            )
        }
    }
}


@Composable
fun TopBar(balance: Double) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(String.format("Balance: %.2f", balance), color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Row {
            Icon(
                imageVector = Icons.Default.CardGiftcard,
                contentDescription = "Rewards",
                tint = Color.Yellow,
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Icon(
                imageVector = Icons.Default.Settings,
                contentDescription = "Settings",
                tint = Color.White,
                modifier = Modifier.size(32.dp)
            )
        }
    }
}


@Composable
fun TacticField(
    targets: List<Target>,
    ballPosition: Int?,
    leftHandPosition: Int?,
    rightHandPosition: Int?
) {
    Box(
        modifier = Modifier.fillMaxWidth(0.9f).aspectRatio(1.5f)
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_gate),
            contentDescription = "Goal",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(6),
            modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.SpaceAround,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            items(targets) { target ->
                TargetItem(
                    target = target,
                    ballPosition = ballPosition,
                    leftHandPosition = leftHandPosition,
                    rightHandPosition = rightHandPosition
                )
            }
        }
    }
}

@Composable
fun TargetItem(
    target: Target,
    ballPosition: Int?,
    leftHandPosition: Int?,
    rightHandPosition: Int?
) {
    Box(
        modifier = Modifier.padding(4.dp).aspectRatio(1f),
        contentAlignment = Alignment.Center
    ) {
        // If the game is finished, determine what to show
        if (ballPosition != null) {
            when (target.id) {
                ballPosition -> Image(painter = painterResource(id = R.drawable.ic_ball), contentDescription = "Ball")
                leftHandPosition -> Image(painter = painterResource(id = R.drawable.ic_hand_left), contentDescription = "Left Hand")
                rightHandPosition -> Image(painter = painterResource(id = R.drawable.ic_hand_right), contentDescription = "Right Hand")
                else -> Image(painter = painterResource(id = R.drawable.ic_target_ball), contentDescription = "Target") // Show default target in other spots
            }
        } else {
            // Before the game ends, show the default target image
            Image(
                painter = painterResource(id = R.drawable.ic_target_ball),
                contentDescription = "Target",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Fit
            )
        }
    }
}

@Composable
fun GameControls(
    betAmount: Double,
    multiplier: Double,
    isSeriesMode: Boolean,
    onBetAmountChange: (Boolean) -> Unit,
    onBumpClicked: () -> Unit,
    onToggleSeries: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Top part of controls (Info, etc.)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            InfoDisplay(label = "Possible gain", value = String.format("%.2f", betAmount * multiplier))
            InfoDisplay(label = "Multiplier", value = String.format("x %.2f", multiplier))
            SeriesToggle(isSeriesMode = isSeriesMode, onToggle = onToggleSeries)
        }
        Spacer(modifier = Modifier.height(16.dp))

        // Bet Amount Controls
        Row(
            modifier = Modifier.fillMaxWidth(0.8f).background(Color.Black.copy(alpha = 0.3f), RoundedCornerShape(12.dp)).padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Amount:", color = Color.White, fontSize = 18.sp)
            Text(String.format("%.2f", betAmount), color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Row {
                Button(onClick = { onBetAmountChange(false) }) { Text("÷2") }
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = { onBetAmountChange(true) }) { Text("x2") }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        // Main Action Button
        Button(
            onClick = onBumpClicked,
            modifier = Modifier.fillMaxWidth(0.8f).height(60.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Yellow)
        ) {
            Text("Bump", color = Color.Black, fontSize = 22.sp, fontWeight = FontWeight.Bold)
        }
        Spacer(modifier = Modifier.height(16.dp))

        // Main Ball Image
        Image(
            painter = painterResource(id = R.drawable.ic_ball),
            contentDescription = "Main Ball",
            modifier = Modifier.size(80.dp)
        )
    }
}


@Composable
fun InfoDisplay(label: String, value: String) {
    Card(
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Black.copy(alpha = 0.3f))
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(label, color = Color.Gray, fontSize = 14.sp)
            Text(value, color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun SeriesToggle(isSeriesMode: Boolean, onToggle: () -> Unit) {
    Card(
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Black.copy(alpha = 0.3f))
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Series", color = Color.Gray, fontSize = 14.sp)
            Switch(checked = isSeriesMode, onCheckedChange = { onToggle() })
        }
    }
}

@Composable
fun GameResultDialog(result: GameResult, onPlayAgain: () -> Unit) {
    AlertDialog(
        onDismissRequest = onPlayAgain,
        title = {
            Text(
                text = if (result == GameResult.WIN) "You Won!" else "You Lost",
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            )
        },
        text = { Text(if (result == GameResult.WIN) "Congratulations!" else "Better luck next time.") },
        confirmButton = {
            Button(onClick = onPlayAgain) {
                Text("Play Again")
            }
        }
    )
}


@Preview(showBackground = true)
@Composable
fun GameScreenPreview() {
    // This preview won't work correctly with a real ViewModel.
    // For a working preview, you might need to pass a mock ViewModel or use a different approach.
    GameScreen(onBack = {})
}
