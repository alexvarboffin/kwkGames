package com.olimpfootball.olimpbet.football

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.olimpfootball.olimpbet.football.MainActivity
import com.olimpfootball.olimpbet.football.SoundManager
import com.olimpfootball.olimpbet.football.data.provideUserPreferencesRepository
import com.olimpfootball.olimpbet.football.ui.theme.PXBFootballTheme


class SplashScreenActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PXBFootballTheme {

                val soundManager = SoundManager(applicationContext, provideUserPreferencesRepository(applicationContext))

                com.olimpfootball.olimpbet.football.SplashScreen(
                    onScreenClick = {
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    },
                    soundManager = soundManager


//                            navController.navigate(Screen.MainMenu.route) {
//                        popUpTo(Screen.Splash.route) { inclusive = true }
//                    }
                )
            }
        }
    }
}

//@Composable
//fun SplashScreen(onScreenClick: () -> Unit) {
//    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
//    val scale by infiniteTransition.animateFloat(
//        initialValue = 1f,
//        targetValue = 1.05f,
//        animationSpec = infiniteRepeatable(
//            animation = tween(1000),
//            repeatMode = RepeatMode.Reverse
//        ),
//        label = "pulseAnimation"
//    )
//
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(Color(0xFF0288D1)) // Blue background
//            .clickable { onScreenClick() },
//        contentAlignment = Alignment.Center
//    ) {
//        Column(
//            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.Center
//        ) {
//            Text(
//                text = "PXB Football",
//                fontSize = 48.sp,
//                fontWeight = FontWeight.Bold,
//                color = Color.White,
//                modifier = Modifier.scale(scale)
//            )
//            Text(
//                text = "Click to start",
//                fontSize = 24.sp,
//                color = Color.White.copy(alpha = 0.8f),
//                modifier = Modifier.padding(top = 32.dp)
//            )
//        }
//    }
//}