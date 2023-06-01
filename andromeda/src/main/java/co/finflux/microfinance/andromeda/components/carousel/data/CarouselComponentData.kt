package co.app.food.andromeda.components.carousel.data

import co.app.food.andromeda.components.ComponentData
import co.app.food.andromeda.components.Gravity
import co.app.food.andromeda.components.Height
import co.app.food.andromeda.components.Width
import kotlinx.parcelize.Parcelize

@Parcelize
data class CarouselComponentData(
    override val id: String = "",
    override val width: Width = Width.FILL,
    override val height: Height = Height.WRAP,
    override val gravity: Gravity = Gravity.NO_GRAVITY,
    override val viewType: String = "carousel",
    override val paddingHorizontal: Int = 0,
    override val paddingVertical: Int = 0,
    val children: List<ComponentData> = emptyList()
) : ComponentData
