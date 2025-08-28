package com.vai.vaidebet.vaibrazil

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.vai.vaidebet.vaibrazil.ui.AppNavigation
import com.vai.vaidebet.vaibrazil.ui.theme.VAITheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Start SplashActivity first
        startActivity(Intent(this, SplashActivity::class.java))
        finish()
    }
}