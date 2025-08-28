package com.vai.vaidebet.vaibrazil

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.vai.vaidebet.vaibrazil.ui.AppNavigation
import com.vai.vaidebet.vaibrazil.ui.theme.VAITheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VAITheme {
                AppNavigation()
            }
        }
    }
}
