package com.aputot.apuestatotal.apuestape

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun InfoPanel(onBackClick: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Penalty Kicks", style = TextStyle(color = Color.White, fontSize = 48.sp, fontWeight = FontWeight.Bold))
        Text("Originally by CodeThisLab", style = TextStyle(color = Color.White, fontSize = 24.sp))
        Spacer(modifier = Modifier.height(32.dp))
        Button(onClick = onBackClick) {
            Text("BACK")
        }
    }
}
