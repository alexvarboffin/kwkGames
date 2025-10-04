package com.olimpfootball.olimparena.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.olimpfootball.olimparena.Screen
import com.olimpfootball.olimparena.SettingItem
import com.olimpfootball.olimparena.SoundManager

@Composable
fun SettingsScreen(navController: NavController, soundManager: SoundManager) {


    Box(
        modifier = Modifier.Companion
            .fillMaxSize()
            .background(Color.Companion.Black.copy(alpha = 0.8f)),
        contentAlignment = Alignment.Companion.Center
    ) {
        Column(
            modifier = Modifier.Companion
                .fillMaxWidth(0.9f)
                .background(Color(0xFF1A1A1A), RoundedCornerShape(16.dp))
                .padding(24.dp),
            horizontalAlignment = Alignment.Companion.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.Companion.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Companion.CenterVertically
            ) {
                Text(
                    "Settings",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Companion.Bold,
                    color = Color.Companion.White
                )
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = "Close",
                        tint = Color.Companion.White
                    )
                }
            }
            Spacer(modifier = Modifier.Companion.height(24.dp))
            SettingItem(text = "User Name") {
                Text("User#00001", color = Color.Yellow, fontSize = 18.sp)
            }
            Spacer(modifier = Modifier.Companion.height(16.dp))
            SettingItem(text = "Music") {
                Switch(checked = !soundManager.isMuted.value, onCheckedChange = {
                    soundManager.toggleMute()
                })
            }
            Spacer(modifier = Modifier.Companion.height(16.dp))
//            SettingItem(text = "Notifications") {
//                Icon(imageVector = Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, tint = Color.White)
//            }
            Spacer(modifier = Modifier.Companion.height(16.dp))
            SettingItem(
                text = "How to play?",
                onClick = { navController.navigate(Screen.Welcome.route) })
            Spacer(modifier = Modifier.Companion.height(32.dp))
            Text(
                "Delete Profile",
                color = Color.Companion.Red,
                modifier = Modifier.Companion.clickable { /* TODO */ })
        }
    }
}