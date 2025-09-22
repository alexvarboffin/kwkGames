package com.olimpfootball.olimpbet.football

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun SplashScreen(onScreenClick: () -> Unit,

//navController: NavController,
 soundManager: SoundManager

) {

    val customFont = FontFamily(Font(R.font.baloo))

    DisposableEffect(Unit) {
        soundManager.startBackgroundMusic()
        onDispose { }
    }
    val infiniteTransition = rememberInfiniteTransition()
    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    Box(
        modifier = Modifier.Companion
            .fillMaxSize()
            .background(Color(0xFF1A1A1A)), // Dark background
        contentAlignment = Alignment.Companion.Center
    ) {
        Column(
            horizontalAlignment = Alignment.Companion.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_round),
                contentDescription = "App Icon",
                modifier = Modifier.Companion.size(128.dp)
            )
            Spacer(modifier = Modifier.Companion.height(16.dp))
            Text(style = MaterialTheme.typography.headlineLarge.copy(fontFamily = customFont),
                text = "OLIEMP",
                fontSize = 40.sp,
                fontWeight = FontWeight.Companion.Bold,
                color = Color(0xFFFF9100), // Gold color
                modifier = Modifier.Companion.graphicsLayer {
                    scaleX = scale
                    scaleY = scale
                }
            )
            Text(style = MaterialTheme.typography.headlineLarge.copy(fontFamily = customFont),
                text = "FOOTBALL",
                fontSize = 40.sp,
                fontWeight = FontWeight.Companion.Bold,
                color = Color(0xFFFF9100), // Gold color
                modifier = Modifier.Companion.graphicsLayer {
                    scaleX = scale
                    scaleY = scale
                }
            )
            Spacer(modifier = Modifier.Companion.height(52.dp))
            PremiumButtonAnimated(text = "Click to start") {
                onScreenClick()
            }
        }
    }
}