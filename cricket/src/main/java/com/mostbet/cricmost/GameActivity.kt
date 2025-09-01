package com.mostbet.cricmost

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

import com.mostbet.cricmost.GameScreen
import com.mostbet.cricmost.ui.theme.CricketTheme

class GameActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CricketTheme {
                GameScreen(isEndlessMode = false, level = 1, onBack = { finish() })
            }
        }
    }
}
