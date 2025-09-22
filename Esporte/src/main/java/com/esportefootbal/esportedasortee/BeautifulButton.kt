package com.esportedasorte.esportefootbal

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

@Composable
fun BeautifulButton(onClick: () -> Unit, text: String, modifier: Modifier = Modifier.Companion) {
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
            modifier = Modifier.Companion
                .background(
                    Brush.Companion.verticalGradient(
                        colors = listOf(
                            Color(0xFFFF9100),
                            Color(0xFFF50057)
                        )
                    ), RoundedCornerShape(16.dp)
                )
                .border(
                    BorderStroke(2.dp, Color.Companion.White),
                    androidx.compose.foundation.shape.RoundedCornerShape(16.dp)
                )
                .padding(16.dp),
            contentAlignment = Alignment.Companion.Center
        ) {
            Text(text = text, fontWeight = FontWeight.Companion.Bold)
        }
    }
}