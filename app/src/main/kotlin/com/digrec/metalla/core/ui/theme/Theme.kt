package com.digrec.metalla.core.ui.theme

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

private val MetallaColorScheme =
    darkColorScheme(
        primary = GoldPrimary,
        onPrimary = GoldOnPrimary,
        primaryContainer = GoldContainer,
        onPrimaryContainer = GoldOnContainer,
        secondary = GoldSecondary,
        tertiary = GoldTertiary,
        background = DarkGrey900,
        surface = DarkGrey800,
        onBackground = GoldSecondary,
        onSurface = GoldSecondary,
    )

@Composable
fun MetallaTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = MetallaColorScheme,
        typography = Typography,
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            content = content,
        )
    }
}
