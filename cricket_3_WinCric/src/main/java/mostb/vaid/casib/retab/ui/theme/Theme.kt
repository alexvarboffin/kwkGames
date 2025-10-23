package mostb.vaid.casib.retab.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val PremiumDarkColorScheme = darkColorScheme(
    primary = PremiumBlue,
    background = DarkBlue,
    onBackground = LightSilver,
    surface = DarkBlue,
    onSurface = LightSilver,
    primaryContainer = PremiumBlue,
    onPrimaryContainer = DarkBlue
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