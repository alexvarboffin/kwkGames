package com.horsewin.onewin.firstwin

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import com.horsewin.onewin.firstwin.ui.splash.SplashScreen
import com.horsewin.onewin.firstwin.ui.theme.HorceTrainerTheme
import kotlinx.coroutines.delay

class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HorceTrainerTheme {
                SplashScreen(onStartClick = {
                    startActivity(Intent(this, GameActivity::class.java))
                    finish()
                })
            }
        }
    }
}