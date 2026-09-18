package com.minorproject.cardscannerai.ui.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColors = lightColorScheme(
    primary = Color(0xFF2E7D32),
    onPrimary = Color(0xFFFFFFFF),
    primaryContainer = Color(0xFFDFF4E0),
    onPrimaryContainer = Color(0xFF103314),
    secondary = Color(0xFFC62828),
    onSecondary = Color(0xFFFFFFFF),
    secondaryContainer = Color(0xFFFBE1E1),
    onSecondaryContainer = Color(0xFF5B1010),
    tertiary = Color(0xFF43A047),
    onTertiary = Color(0xFFFFFFFF),
    tertiaryContainer = Color(0xFFE6F6E7),
    onTertiaryContainer = Color(0xFF1D4F20),
    background = Color(0xFFF4FBF4),
    onBackground = Color(0xFF16311A),
    surface = Color(0xFFFFFFFF),
    onSurface = Color(0xFF16311A),
    surfaceVariant = Color(0xFFE1F0E2),
    onSurfaceVariant = Color(0xFF4E6A51),
    outline = Color(0xFF9FBEA3)
)

private val DarkColors = darkColorScheme(
    primary = Color(0xFF81C784),
    onPrimary = Color(0xFF153018),
    primaryContainer = Color(0xFF214D25),
    onPrimaryContainer = Color(0xFFCFF0D1),
    secondary = Color(0xFFE57373),
    onSecondary = Color(0xFF4A1010),
    secondaryContainer = Color(0xFF5C1F1F),
    onSecondaryContainer = Color(0xFFF7D9D9),
    tertiary = Color(0xFFA5D6A7),
    onTertiary = Color(0xFF113016),
    tertiaryContainer = Color(0xFF1D4421),
    onTertiaryContainer = Color(0xFFD7F1D8),
    background = Color(0xFF0D170E),
    onBackground = Color(0xFFE7F3E8),
    surface = Color(0xFF132014),
    onSurface = Color(0xFFE7F3E8),
    surfaceVariant = Color(0xFF243527),
    onSurfaceVariant = Color(0xFFC0D6C2),
    outline = Color(0xFF5C755F)
)

@Composable
fun CardScannerTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = if (androidx.compose.foundation.isSystemInDarkTheme()) DarkColors else LightColors,
        typography = Typography,
        content = content
    )
}
