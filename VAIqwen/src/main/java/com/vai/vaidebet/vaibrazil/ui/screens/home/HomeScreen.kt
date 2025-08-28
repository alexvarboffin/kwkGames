package com.vai.vaidebet.vaibrazil.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vai.vaidebet.vaibrazil.R
import com.vai.vaidebet.vaibrazil.domain.model.GameLevel
import com.vai.vaidebet.vaibrazil.presentation.screens.home.HomeUiState
import com.vai.vaidebet.vaibrazil.presentation.screens.home.HomeViewModel
import com.vai.vaidebet.vaibrazil.ui.theme.Yellow
import com.vai.vaidebet.vaibrazil.ui.theme.Black
import com.vai.vaidebet.vaibrazil.ui.theme.Green

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onLevelSelected: (Int) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Animated logo
        AnimatedLogo()

        Text(
            text = "Select Level",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(16.dp),
            color = Yellow
        )

        when {
            uiState.isLoading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    color = Yellow
                )
            }
            uiState.error != null -> {
                Text(
                    text = "Error: ${uiState.error}",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
            else -> {
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    LevelGrid(
                        levelsByPage = uiState.levelsByPage,
                        currentPage = uiState.currentPage,
                        onLevelSelected = onLevelSelected
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = { viewModel.previousPage() },
                        enabled = uiState.currentPage > 0,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Yellow,
                            contentColor = Black,
                            disabledContainerColor = Color.Gray
                        )
                    ) {
                        Text(text = "<")
                    }
                    Text(
                        text = "${uiState.currentPage + 1} / ${uiState.totalPages}",
                        modifier = Modifier.padding(horizontal = 16.dp),
                        color = Yellow
                    )
                    Button(
                        onClick = { viewModel.nextPage() },
                        enabled = uiState.currentPage < uiState.totalPages - 1,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Yellow,
                            contentColor = Black,
                            disabledContainerColor = Color.Gray
                        )
                    ) {
                        Text(text = ">")
                    }
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = { /* TODO: Open Privacy Policy */ },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Yellow,
                    contentColor = Black
                )
            ) {
                Text(text = "Privacy Policy")
            }
            Button(
                onClick = { /* TODO: Open FAQ */ },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Yellow,
                    contentColor = Black
                )
            ) {
                Text(text = "FAQ")
            }
        }
    }
}

@Composable
fun AnimatedLogo() {
    var scale by remember { mutableStateOf(1f) }
    LaunchedEffect(Unit) {
        while (true) {
            kotlinx.coroutines.delay(1500)
            scale = 1.1f
            kotlinx.coroutines.delay(1500)
            scale = 1f
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_splash_logo),
            contentDescription = "VAI Logo",
            modifier = Modifier
                .size(100.dp)
                .scale(scale)
                .clip(RoundedCornerShape(16.dp)),
            contentScale = androidx.compose.ui.layout.ContentScale.Crop
        )
    }
}

@Composable
fun LevelGrid(
    levelsByPage: Map<Int, List<GameLevel>>,
    currentPage: Int,
    onLevelSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val levels = levelsByPage[currentPage] ?: emptyList()

    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = modifier.padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(levels) { level ->
            LevelCard(
                level = level,
                onLevelSelected = onLevelSelected
            )
        }
    }
}

@Composable
fun LevelCard(
    level: GameLevel,
    onLevelSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .aspectRatio(1f)
            .clip(RoundedCornerShape(16.dp))
            .background(Green)
            .border(2.dp, Yellow, RoundedCornerShape(16.dp))
            .clickable { onLevelSelected(level.id) },
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "${level.id}",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Yellow
            )
            Spacer(modifier = Modifier.height(8.dp))
            // Display star rating based on solution moves
            Row {
                val stars = when {
                    level.solutionMoves <= 5 -> 3
                    level.solutionMoves <= 10 -> 2
                    else -> 1
                }
                for (i in 1..3) {
                    Text(
                        text = if (i <= stars) "★" else "☆",
                        fontSize = 16.sp,
                        color = if (i <= stars) Yellow else Color.Gray
                    )
                }
            }
        }
    }
}