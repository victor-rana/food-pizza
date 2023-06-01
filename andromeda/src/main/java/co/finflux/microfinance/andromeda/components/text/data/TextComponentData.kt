package co.app.food.andromeda.components.text.data

import co.app.food.andromeda.components.ComponentDataWithClick
import co.app.food.andromeda.components.Gravity
import co.app.food.andromeda.components.Height
import co.app.food.andromeda.components.Width
import co.app.food.andromeda.text.TypographyStyle
import kotlinx.parcelize.Parcelize
import co.app.food.andromeda.Color as AndromedaColor

@Parcelize
data class TextComponentData(
    override val width: Width = Width.FILL,
    override val height: Height = Height.WRAP,
    override val id: String = "",
    override val gravity: Gravity = Gravity.NO_GRAVITY,
    override val viewType: String = "text",
    override val paddingHorizontal: Int = 0,
    override val paddingVertical: Int = 0,
    override val deepLink: String = "",
    var navigateToOnClick: String? = null,
    val isClickable: Boolean = false,
    val text: String = "",
    val size: Float = 16.0f,
    val textStyle: TypographyStyle = TypographyStyle.TITLE_MODERATE_BOLD_DEFAULT,
    val textSpacingExtraEnabled: Boolean = true,
    val showDivider: Boolean = true,
    val bgColor: AndromedaColor = AndromedaColor.TRANSPARENT,
    val textColor: AndromedaColor = AndromedaColor.DEFAULT,
    val marginsHorizontal: Int = 0,
    val marginsVertical: Int = 0,
) : ComponentDataWithClick
