package com.aputot.totapupe.apuestatotal

import androidx.compose.foundation.background
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.aputot.totapupe.apuestatotal.ui.theme.GoldDark
import com.aputot.totapupe.apuestatotal.ui.theme.GoldLight

@Composable
fun BeautifulButton(onClick: () -> Unit, text: String, modifier: Modifier = Modifier) {
    Button(
        onClick = onClick,
        modifier = modifier.width(180.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            contentColor = Color.White
        ),
        contentPadding = PaddingValues()
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp)) // Premium touch: rounded corners
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(GoldLight, GoldDark) // Apply the gradient
                    )
                )
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(text = text, color = Color.Black) // Adjust text color for contrast
        }
    }
}