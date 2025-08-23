package com.olimpfootball.olimpbet.footgame.screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.olimpfootball.olimpbet.footgame.PremiumButton

@Composable
fun RewardsScreen(onClose: () -> Unit) {
    val context = LocalContext.current
    val rewards = listOf(250, 500, 1000, 1500, 2000, 3000, 4500)
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
            Row(
                modifier = Modifier.Companion.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Companion.CenterVertically
            ) {
                Text(
                    "Special Reward",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Companion.Bold,
                    color = Color.Companion.White
                )
                IconButton(onClick = onClose) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = "Close",
                        tint = Color.Companion.White
                    )
                }
            }
            Spacer(modifier = Modifier.Companion.height(16.dp))
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(rewards.size) { index ->
                    Column(
                        modifier = Modifier.Companion
                            .background(
                                Color.Companion.Black.copy(alpha = 0.3f),
                                androidx.compose.foundation.shape.RoundedCornerShape(12.dp)
                            )
                            .padding(16.dp),
                        horizontalAlignment = Alignment.Companion.CenterHorizontally
                    ) {
                        Text("Day ${index + 1}", color = Color.Companion.Gray)
                        Spacer(modifier = Modifier.Companion.height(8.dp))
                        Text("⭐", fontSize = 32.sp)
                        Spacer(modifier = Modifier.Companion.height(8.dp))
                        Text(
                            rewards[index].toString(),
                            color = Color.Companion.White,
                            fontWeight = FontWeight.Companion.Bold
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.Companion.height(16.dp))
            PremiumButton(text = "Claim") {
                Toast.makeText(context, "Reward Claimed!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}