package com.vai.vai.vai.vaidebet.vaibrazi.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = PremiumGold,
    onPrimary = Charcoal,
    background = Charcoal,
    onBackground = OffWhite,
    surface = Charcoal,
    onSurface = OffWhite
)

@Composable
fun VAITheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = AppTypography,
        content = content
    )
}