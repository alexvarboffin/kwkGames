package com.olimpfootball.olimparena

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.olimpfootball.olimparena.data.provideUserPreferencesRepository
import com.olimpfootball.olimparena.ui.theme.PXBFootballTheme


@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PXBFootballTheme {

                val soundManager = SoundManager(applicationContext, provideUserPreferencesRepository(applicationContext))

                SplashScreen(
                    onScreenClick = {
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    },
                    soundManager = soundManager


//                        navController.navigate(Screen.MainMenu.route) {
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