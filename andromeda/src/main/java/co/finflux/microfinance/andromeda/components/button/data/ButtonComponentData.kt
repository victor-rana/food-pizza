package co.app.food.andromeda.components.button.data

import co.app.food.andromeda.button.AndromedaButton
import co.app.food.andromeda.button.IconPosition
import co.app.food.andromeda.components.ComponentDataWithClick
import co.app.food.andromeda.components.Gravity
import co.app.food.andromeda.components.Height
import co.app.food.andromeda.components.Width
import kotlinx.parcelize.Parcelize

@Parcelize
data class ButtonComponentData(
    override val id: String = "",
    override val width: Width = Width.FILL,
    override val height: Height = Height.WRAP,
    override val gravity: Gravity = Gravity.CENTER,
    override val viewType: String = "button",
    override val paddingHorizontal: Int = 0,
    override val paddingVertical: Int = 0,
    override val deepLink: String = "",
    val style: AndromedaButton.ButtonType = AndromedaButton.ButtonType.PRIMARY_POSITIVE_FULL_WIDTH,
    val text: String = "",
    val iconData: ButtonIconData? = null,
    val iconPosition: IconPosition? = null,
    val marginsHorizontal: Int = 0,
    val marginsVertical: Int = 0,
    val isEnabled: Boolean = true,
    val radius: Float = 0.0f,
    val shouldLoadOnClick: Boolean = false,
) : ComponentDataWithClick
