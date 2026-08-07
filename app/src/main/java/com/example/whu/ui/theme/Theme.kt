package com.example.whu.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = JoyDarkPrimary,
    secondary = JoyDarkSecondary,
    tertiary = JoyDarkTertiary,
    background = JoyDarkBackground,
    surface = JoyDarkSurface,
    onPrimary = JoyDarkBackground,
    onSecondary = JoyDarkBackground,
    onTertiary = JoyDarkBackground,
    onBackground = JoyLightBackground,
    onSurface = JoyLightBackground,
)

private val LightColorScheme = lightColorScheme(
    primary = JoyLightPrimary,
    secondary = JoyLightSecondary,
    tertiary = JoyLightTertiary,
    background = JoyLightBackground,
    surface = JoyLightSurface,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = JoyDarkBackground,
    onSurface = JoyDarkBackground,
)

@Composable
fun WhuTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
