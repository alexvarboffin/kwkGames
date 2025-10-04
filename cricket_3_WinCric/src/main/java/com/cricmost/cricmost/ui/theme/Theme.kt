package com.cricmost.cricmost.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val PremiumDarkColorScheme = darkColorScheme(
    primary = PremiumGold,
    background = Charcoal,
    onBackground = OffWhite,
    surface = Charcoal,
    onSurface = OffWhite,
    primaryContainer = PremiumGold,
    onPrimaryContainer = Charcoal
)

@Composable
fun CricketTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = PremiumDarkColorScheme,
        typography = AppTypography,
        content = content
    )
}
