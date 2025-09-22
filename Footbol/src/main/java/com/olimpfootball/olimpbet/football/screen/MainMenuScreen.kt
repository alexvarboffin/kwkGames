package com.olimpfootball.olimpbet.football.screen

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.olimpfootball.olimpbet.football.PremiumButton
import com.olimpfootball.olimpbet.football.R
import com.olimpfootball.olimpbet.football.Screen
import com.olimpfootball.olimpbet.football.SoundManager
import com.olimpfootball.olimpbet.football.openInCustomTab

@Composable
fun MainMenuScreen(navController: NavController, soundManager: SoundManager) {
    val context = LocalContext.current
    val z = stringResource(R.string.privacyPolicyUrl)
    val faq = stringResource(R.string.faq)

    Column(
        modifier = Modifier.Companion
            .fillMaxSize()
            .background(Color(0xFF0E0C0C)) // Dark background
            .padding(16.dp),
        horizontalAlignment = Alignment.Companion.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.app_name_full),
            fontSize = 40.sp,
            fontWeight = FontWeight.Companion.Bold,
            color = Color.Companion.White
        )
        Spacer(modifier = Modifier.Companion.height(64.dp))

        PremiumButton(text = stringResource(R.string.playGame)) { navController.navigate(Screen.TacticField.createRoute(1)) }
        Spacer(modifier = Modifier.Companion.height(16.dp))
        //PremiumButton(text = "Select Level") { navController.navigate(Screen.LevelSelect.route) }
        Spacer(modifier = Modifier.Companion.height(16.dp))
        PremiumButton(text = stringResource(R.string.settings)) { navController.navigate(Screen.Settings.route) }
        Spacer(modifier = Modifier.Companion.height(16.dp))
        PremiumButton(text = stringResource(R.string.rewards)) { navController.navigate(Screen.Rewards.route) }
        Spacer(modifier = Modifier.Companion.height(16.dp))
        PremiumButton(text = stringResource(R.string.privacyPolicy)) {
            openInCustomTab(
                context, url = z
            )
        }
        Spacer(modifier = Modifier.Companion.height(16.dp))
        PremiumButton(text = "FAQ") { openInCustomTab(context, faq) }
    }
}