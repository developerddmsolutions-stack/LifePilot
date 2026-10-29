package com.lifepilot.personal.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = ElectricTeal,
    onPrimary = NavyDarkBackground,
    primaryContainer = NavySurfaceVariantDark,
    onPrimaryContainer = ElectricTeal,
    secondary = SoftBlueAccent,
    background = NavyDarkBackground,
    surface = NavySurfaceDark,
    surfaceVariant = NavySurfaceVariantDark,
    onBackground = TextPrimaryDark,
    onSurface = TextPrimaryDark,
    onSurfaceVariant = TextSecondaryDark,
    error = AccentRed
)

private val LightColorScheme = lightColorScheme(
    primary = ElectricTealMuted,
    onPrimary = LightSurface,
    background = LightBackground,
    surface = LightSurface,
    surfaceVariant = LightSurfaceVariant,
    onBackground = TextPrimaryLight,
    onSurface = TextPrimaryLight,
    onSurfaceVariant = TextSecondaryLight
)

@Composable
fun LifePilotTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}