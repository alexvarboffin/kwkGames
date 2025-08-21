package com.mostbet.cricmost

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.mostbet.cricmost.ui.theme.PXBFootballTheme

class GameActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val level = intent.getIntExtra("level", 1)
        setContent {
            PXBFootballTheme {
                GameScreen(isEndlessMode = false, level = level, onBack = { finish() })
            }
        }
    }
}