package co.app.food.andromeda.compose.typography.caption

import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import co.app.food.andromeda.compose.typography.AndromedaFonts
import co.app.food.andromeda.compose.typography.BaseTypography

/**
 * Title Extra Large typography style
 */
class CaptionSmallBookTypographyStyle : BaseTypography {
    override val fontFamily: FontFamily = AndromedaFonts

    override val fontSize: TextUnit = 12.sp

    override val fontWeight: FontWeight = FontWeight.W400

    override val lineHeight: TextUnit = 16.sp
}
