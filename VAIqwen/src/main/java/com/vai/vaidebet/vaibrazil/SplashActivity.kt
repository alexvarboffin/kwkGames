package com.vai.vaidebet.vaibrazil

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.vai.vaidebet.vaibrazil.presentation.screens.splash.SplashViewModel
import com.vai.vaidebet.vaibrazil.ui.screens.splash.SplashScreen
import com.vai.vaidebet.vaibrazil.ui.theme.VAITheme

class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VAITheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val viewModel = SplashViewModel()
                    SplashScreen(
                        viewModel = viewModel,
                        onNavigateToHome = {
                            startActivity(Intent(this, MainActivity::class.java))
                            finish()
                        }
                    )
                }
            }
        }
    }
}