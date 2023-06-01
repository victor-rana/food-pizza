package co.app.food.andromeda.compose.typography.title

import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import co.app.food.andromeda.compose.typography.AndromedaFonts
import co.app.food.andromeda.compose.typography.BaseTypography

/**
 * Title Extra Large typography style
 */
class TitleLargeTypographyStyle : BaseTypography {
    override val fontFamily: FontFamily = AndromedaFonts

    override val fontSize: TextUnit = 21.sp

    override val fontWeight: FontWeight = FontWeight.Bold

    override val lineHeight: TextUnit = 28.sp
}