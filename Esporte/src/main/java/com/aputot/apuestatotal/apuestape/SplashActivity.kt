package com.aputot.apuestatotal.apuestape

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.aputot.apuestatotal.apuestape.ui.theme.PenaltiKicksTheme


class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PenaltiKicksTheme {
                SplashScreen(
                    onStartClick = {
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    }
                )
            }
        }
    }
}
