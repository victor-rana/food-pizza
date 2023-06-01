package co.app.food.andromeda.components.button.data

import co.app.food.andromeda.Color as AndromedaColor
import co.app.food.andromeda.assets.icon.Icon
import co.app.food.andromeda.button.AndromedaCircularButton.*
import co.app.food.andromeda.components.ComponentDataWithClick
import co.app.food.andromeda.components.Gravity
import co.app.food.andromeda.components.Height
import co.app.food.andromeda.components.Width
import kotlinx.parcelize.Parcelize

@Parcelize
data class CircularButtonComponentData(
    override val id: String = "",
    override val width: Width = Width.FILL,
    override val height: Height = Height.WRAP,
    override val gravity: Gravity = Gravity.CENTER,
    override val viewType: String = "button_circular",
    override val paddingHorizontal: Int = 0,
    override val paddingVertical: Int = 0,
    override val deepLink: String = "",
    val style: CircularButtonType = CircularButtonType.CIRCULAR_PRIMARY_REGULAR,
    val iconData: ButtonIconData = ButtonIconData(
        iconName = Icon.ADD,
        iconColor = AndromedaColor.WHITE
    ),
    val marginsHorizontal: Int = 0,
    val marginsVertical: Int = 0,
    val isEnabled: Boolean = true,
    val shouldLoadOnClick: Boolean = false
) : ComponentDataWithClick
