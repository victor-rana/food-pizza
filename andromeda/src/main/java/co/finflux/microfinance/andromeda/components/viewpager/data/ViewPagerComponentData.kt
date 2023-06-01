package co.app.food.andromeda.components.viewpager.data

import co.app.food.andromeda.components.ComponentData
import co.app.food.andromeda.components.Gravity
import co.app.food.andromeda.components.Height
import co.app.food.andromeda.components.Width
import co.app.food.andromeda.viewpager.PageItem
import kotlinx.parcelize.Parcelize

@Parcelize
data class ViewPagerComponentData(
    override val id: String = "",
    override val width: Width = Width.FILL,
    override val height: Height = Height.WRAP,
    override val gravity: Gravity = Gravity.NO_GRAVITY,
    override val viewType: String = "tabs",
    override val paddingHorizontal: Int = 0,
    override val paddingVertical: Int = 0,
    val pages: List<PageItem> = emptyList()
) : ComponentData
