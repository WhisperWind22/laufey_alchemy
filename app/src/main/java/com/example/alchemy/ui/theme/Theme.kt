package com.example.alchemy.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Typography
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp

private val DarkColorPalette = darkColors(
    primary = Color(0xFF9C27B0),
    primaryVariant = Color(0xFF7B1FA2),
    secondary = Color(0xFF7C4DFF),
    background = Color(0xFF121212),
    surface = Color(0xFF1E1E1E),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = Color.White,
    onSurface = Color.White
)

private val Typography = Typography(
    h6 = TextStyle(
        fontFamily = FontFamily.Default,
        fontSize = 20.sp
    ),
    subtitle1 = TextStyle(
        fontFamily = FontFamily.Default,
        fontSize = 16.sp
    ),
    body1 = TextStyle(
        fontFamily = FontFamily.Default,
        fontSize = 14.sp
    ),
    body2 = TextStyle(
        fontFamily = FontFamily.Default,
        fontSize = 12.sp
    )
)

@Composable
fun AlchemyTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colors = DarkColorPalette,
        typography = Typography,
        content = content
    )
} 