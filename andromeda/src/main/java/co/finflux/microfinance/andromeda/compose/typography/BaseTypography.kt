package co.app.food.andromeda.compose.typography

import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit

interface BaseTypography {
    val fontFamily: FontFamily
    val fontSize: TextUnit
    val fontWeight: FontWeight
    val lineHeight: TextUnit
}
