package com.olimpfootball.olimpbet.footgame.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.olimpfootball.olimpbet.footgame.PremiumButton

@Composable
fun WelcomeScreen(onClose: () -> Unit) {
    Box(
        modifier = Modifier.Companion
            .fillMaxSize()
            .background(Color.Companion.Black.copy(alpha = 0.8f))
            .padding(16.dp),
        contentAlignment = Alignment.Companion.Center
    ) {
        Column(
            modifier = Modifier.Companion
                .fillMaxWidth(0.9f)
                .background(Color(0xFF1A1A1A), RoundedCornerShape(16.dp))
                .padding(24.dp),
            horizontalAlignment = Alignment.Companion.CenterHorizontally
        ) {
            Text(
                "HOW TO PLAY?",
                fontSize = 24.sp,
                fontWeight = FontWeight.Companion.Bold,
                color = Color.Companion.White
            )
            Spacer(modifier = Modifier.Companion.height(16.dp))
            Text(
                text = "The main goal is simple — score against the goalkeeper! Every round is a one-on-one showdown where your timing and instinct are everything. Choose the right direction, outsmart the keeper, and send the ball straight into the net.",
                textAlign = TextAlign.Companion.Center,
                color = Color.Companion.White
            )
            Spacer(modifier = Modifier.Companion.height(24.dp))
            PremiumButton(text = "Next") { onClose() }
        }
    }
}