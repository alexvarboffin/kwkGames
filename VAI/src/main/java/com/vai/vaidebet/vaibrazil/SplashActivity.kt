package com.vai.vaidebet.vaibrazil

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.vai.vaidebet.vaibrazil.ui.screens.splash.SplashScreen
import com.vai.vaidebet.vaibrazil.ui.theme.VAITheme

class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VAITheme {
                SplashScreen(onNavigateToHome = {
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                })
            }
        }
    }
}
