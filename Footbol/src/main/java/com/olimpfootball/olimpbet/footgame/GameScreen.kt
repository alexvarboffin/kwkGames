
package com.olimpfootball.olimpbet.footgame

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun GameScreen(gameViewModel: GameViewModel = viewModel(), onBack: () -> Unit) {
    val uiState by gameViewModel.uiState.collectAsState()

    // The main layout of the game screen
    Box(modifier = Modifier.fillMaxSize()) {
        // TODO: Replace with actual background image from resources
        // Image(painter = painterResource(id = R.drawable.game_background), ...)
        Box(modifier = Modifier.fillMaxSize().background(Color(0xFF006400))) // Dark Green

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            TopBar(balance = uiState.balance)
            Spacer(modifier = Modifier.weight(1f))
            TacticField(
                targets = uiState.targets,
                selectedTargetId = uiState.selectedTargetId,
                onTargetSelect = { gameViewModel.onTargetSelected(it) }
            )
            Spacer(modifier = Modifier.weight(1f))
            GameControls(
                betAmount = uiState.betAmount,
                multiplier = uiState.multiplier,
                onBetAmountChange = { isMultiply -> gameViewModel.changeBetAmount(isMultiply) },
                onBumpClicked = { gameViewModel.onBumpClicked() }
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
                painter = painterResource(id = android.R.drawable.star_on), // Placeholder
                contentDescription = "Rewards",
                tint = Color.Yellow,
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Icon(
                painter = painterResource(id = android.R.drawable.ic_menu_manage), // Placeholder
                contentDescription = "Settings",
                tint = Color.White,
                modifier = Modifier.size(32.dp)
            )
        }
    }
}

@Composable
fun TacticField(targets: List<Target>, selectedTargetId: Int?, onTargetSelect: (Int) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .aspectRatio(1.5f)
            .background(Color.Black.copy(alpha = 0.2f), RoundedCornerShape(16.dp))
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(4),
            modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.SpaceAround,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            items(targets) { target ->
                TargetItem(
                    target = target,
                    isSelected = target.id == selectedTargetId,
                    onSelect = { onTargetSelect(target.id) }
                )
            }
        }
    }
}

@Composable
fun TargetItem(target: Target, isSelected: Boolean, onSelect: () -> Unit) {
    Box(
        modifier = Modifier
            .padding(4.dp)
            .aspectRatio(1f)
            .clip(CircleShape)
            .background(if (isSelected) Color.Yellow.copy(alpha = 0.5f) else Color.White.copy(alpha = 0.3f))
            .clickable(onClick = onSelect),
        contentAlignment = Alignment.Center
    ) {
        if (target.isGoalkeeper) {
            Icon(
                painter = painterResource(id = android.R.drawable.ic_dialog_alert), // Placeholder
                contentDescription = "Goalkeeper",
                tint = Color.Red,
                modifier = Modifier.size(40.dp)
            )
        }
    }
}

@Composable
fun GameControls(
    betAmount: Double,
    multiplier: Double,
    onBetAmountChange: (Boolean) -> Unit,
    onBumpClicked: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            InfoDisplay(label = "Possible gain", value = String.format("%.2f", betAmount * multiplier))
            InfoDisplay(label = "Multiplier", value = String.format("x %.2f", multiplier))
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .background(Color.Black.copy(alpha = 0.3f), RoundedCornerShape(12.dp))
                .padding(8.dp),
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
        Button(
            onClick = onBumpClicked,
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(60.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Yellow)
        ) {
            Text("Bump", color = Color.Black, fontSize = 22.sp, fontWeight = FontWeight.Bold)
        }
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
