package com.vai.vai.vaidebet.vaibrazil.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vai.vai.vaidebet.vaibrazil.presentation.screens.home.HomeUiState
import com.vai.vai.vaidebet.vaibrazil.presentation.screens.home.HomeViewModel

import com.vai.vai.vaidebet.vaibrazil.R

import com.walhalla.sdk.domain.model.GameLevel
import com.walhalla.sdk.domain.model.UserProgress
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Regular
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.regular.Star
import compose.icons.fontawesomeicons.solid.Star
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    onLevelSelected: (Int) -> Unit,
    viewModel: HomeViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val uriHandler = LocalUriHandler.current

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.football_field),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
//            Box(
//                modifier = Modifier
//                    .size(150.dp)
//                    //.scale(logoScale)
//                    .clip(shape = CircleShape)
//            ) {
//                Image(
//                    painter = painterResource(id = R.drawable.ic_splash),
//                    contentDescription = null,
//                    modifier = Modifier.fillMaxSize(),
//                    contentScale = ContentScale.Crop
//                )
//            }

            Text(
                text = "Select Level",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(16.dp)
            )

            when (val state = uiState) {
                is HomeUiState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                }

                is HomeUiState.Success -> {
                    val levelsByPage = state.levelsByPage
                    val currentPage = state.currentPage
                    val totalPages = state.totalPages
                    val userProgress = state.userProgress

                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        LevelGrid(
                            levels = levelsByPage.getOrElse(currentPage) { emptyList() },
                            userProgress = userProgress,
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
                        IconButton(
                            onClick = { viewModel.previousPage() },
                            enabled = currentPage > 0
                        ) {
                            Icon(
                                Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Previous"
                            )
                        }
                        Text(
                            text = "${currentPage + 1} / $totalPages",
                            modifier = Modifier.padding(horizontal = 16.dp),
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )
                        IconButton(
                            onClick = { viewModel.nextPage() },
                            enabled = currentPage < totalPages - 1
                        ) {
                            Icon(
                                Icons.AutoMirrored.Filled.ArrowForward,
                                contentDescription = "Next"
                            )
                        }
                    }
                }

                is HomeUiState.Error -> {
                    Text(
                        text = state.message,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(onClick = { uriHandler.openUri("https://haliol.top/Privacyy4") }) {
                    Text(text = "Privacy Policy")
                }
                Button(onClick = { uriHandler.openUri("https://haliol.top/FAQQ4") }) {
                    Text(text = "FAQ")
                }
            }
        }
    }
}

@Composable
fun LevelGrid(
    levels: List<GameLevel>,
    userProgress: UserProgress,
    onLevelSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(4),
        modifier = modifier.padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(levels) { level ->
            val isLocked = level.id > userProgress.highestUnlockedLevel
            val stars = userProgress.stars[level.id.toString()] ?: 0
            LevelItem(
                level = level,
                isLocked = isLocked,
                stars = stars,
                onLevelSelected = onLevelSelected
            )
        }
    }
}

@Composable
fun LevelItem(
    level: GameLevel,
    isLocked: Boolean,
    stars: Int,
    onLevelSelected: (Int) -> Unit
) {
    val grayscaleMatrix = ColorMatrix().apply { setToSaturation(0f) }

    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .background(Color.Yellow.copy(alpha = 0.2f), RoundedCornerShape(16.dp))
            .border(2.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(16.dp))
            .clickable(enabled = !isLocked) { onLevelSelected(level.id) }
            .then(
                if (isLocked) Modifier.graphicsLayer(colorFilter = ColorFilter.colorMatrix(grayscaleMatrix))
                else Modifier
            ),
        contentAlignment = Alignment.Center
    ) {
        if (isLocked) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = "${level.id}", fontSize = 32.sp, fontWeight = FontWeight.Bold)
                Row {
                    Icon(Icons.Default.Lock, contentDescription = "Locked", tint = Color.Yellow)
                }
            }

        } else {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = "${level.id}", fontSize = 32.sp, fontWeight = FontWeight.Bold)
                Row {
                    for (i in 1..3) {
                        Icon(
                            imageVector = if (i <= stars) FontAwesomeIcons.Solid.Star else FontAwesomeIcons.Regular.Star,
                            contentDescription = null,
                            tint = Color.Yellow,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }
        }
    }
}
