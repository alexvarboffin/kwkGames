package com.vai.vaidebet.vaibrazil.ui.screens.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vai.vaidebet.vaibrazil.R
import com.vai.vaidebet.vaibrazil.presentation.screens.splash.SplashViewModel
import com.vai.vaidebet.vaibrazil.ui.theme.Yellow
import com.vai.vaidebet.vaibrazil.ui.theme.Black

@Composable
fun SplashScreen(
    viewModel: SplashViewModel,
    onNavigateToHome: () -> Unit
) {
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
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        // Background image
        Image(
            painter = painterResource(id = R.drawable.ic_splash),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Animated logo
            Box(
                modifier = Modifier
                    .size(150.dp)
                    .clip(CircleShape)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_splash_logo),
                    contentDescription = "VAI Logo",
                    modifier = Modifier
                        .fillMaxSize()
                        .scale(scale),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Title
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .background(Yellow)
                    .padding(horizontal = 32.dp, vertical = 16.dp)
            ) {
                Text(
                    text = "VAI SWIPE",
                    fontSize = 48.sp,
                    fontWeight = FontWeight.Bold,
                    color = Black
                )
            }

            Spacer(modifier = Modifier.height(64.dp))

            // Start button with animation
            var alpha by remember { mutableStateOf(0.5f) }
            LaunchedEffect(Unit) {
                while (true) {
                    kotlinx.coroutines.delay(1000)
                    alpha = 1f
                    kotlinx.coroutines.delay(1000)
                    alpha = 0.5f
                }
            }

            Button(
                onClick = { viewModel.onStartClicked() },
                modifier = Modifier.alpha(alpha),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Yellow,
                    contentColor = Black
                )
            ) {
                Text(text = "Click to start", fontSize = 24.sp)
            }
        }
    }
}