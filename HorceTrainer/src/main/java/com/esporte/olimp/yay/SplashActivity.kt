package com.esporte.olimp.yay

import android.content.Intent
import android.os.Bundle

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.esporte.olimp.yay.splash.SplashScreen

class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HorseTrainerTheme {
                SplashScreen(onStartClick = {
                    startActivity(Intent(this, GameActivity::class.java))
                    finish()
                })
            }
        }
    }
}