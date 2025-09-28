package com.cricjojo.cricmost

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.cricjojo.cricmost.ui.theme.GoldDark
import com.cricjojo.cricmost.ui.theme.GoldLight

@Composable
fun BeautifulButton(onClick: () -> Unit, text: String, modifier: Modifier = Modifier) {
    Button(
        onClick = onClick,
        modifier = modifier.width(180.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Companion.Transparent,
            contentColor = Color.Companion.White
        ),
        contentPadding = PaddingValues()
    ) {
        Box(
            modifier = Modifier
                .border(3.dp, Color.Black, RoundedCornerShape(12.dp))
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(GoldLight, GoldDark)
                    ),
                    shape = RoundedCornerShape(12.dp)
                )
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(text = text, fontWeight = FontWeight.Bold, color = Color.Black)
        }
    }
}