package com.olimpfootball.olimpbet.footgame.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.olimpfootball.olimpbet.footgame.PremiumButton
import com.olimpfootball.olimpbet.footgame.Screen
import com.olimpfootball.olimpbet.footgame.SoundManager
import com.olimpfootball.olimpbet.footgame.openInCustomTab

@Composable
fun MainMenuScreen(navController: NavController, soundManager: SoundManager) {
    val context = LocalContext.current

    Column(
        modifier = Modifier.Companion
            .fillMaxSize()
            .background(Color(0xFF1A1A1A)) // Dark background
            .padding(16.dp),
        horizontalAlignment = Alignment.Companion.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "OLIMP Game",
            fontSize = 40.sp,
            fontWeight = FontWeight.Companion.Bold,
            color = Color.Companion.White
        )
        Spacer(modifier = Modifier.Companion.height(64.dp))

        PremiumButton(text = "Go to Game") { navController.navigate(Screen.TacticField.createRoute(1)) }
        Spacer(modifier = Modifier.Companion.height(16.dp))
        //PremiumButton(text = "Select Level") { navController.navigate(Screen.LevelSelect.route) }
        Spacer(modifier = Modifier.Companion.height(16.dp))
        PremiumButton(text = "Settings") { navController.navigate(Screen.Settings.route) }
        Spacer(modifier = Modifier.Companion.height(16.dp))
        PremiumButton(text = "Rewards") { navController.navigate(Screen.Rewards.route) }
        Spacer(modifier = Modifier.Companion.height(16.dp))
        PremiumButton(text = "Privacy Policy") {
            openInCustomTab(
                context,
                "https://sportsga.top/Privacy2"
            )
        }
        Spacer(modifier = Modifier.Companion.height(16.dp))
        PremiumButton(text = "FAQ") { openInCustomTab(context, "https://sportsga.top/FAQ2") }
    }
}