package co.app.food.andromeda.components.viewgroup.data

import co.app.food.andromeda.components.ComponentData
import co.app.food.andromeda.components.ComponentDataWithCollapseAction
import co.app.food.andromeda.components.Gravity
import co.app.food.andromeda.components.Height
import co.app.food.andromeda.components.Width
import kotlinx.parcelize.Parcelize
import co.app.food.andromeda.Color as AndromedaColor

@Parcelize
data class ViewGroupComponentData(
    override val id: String = "",
    override val width: Width = Width.WRAP,
    override val height: Height = Height.WRAP,
    override val gravity: Gravity = Gravity.NO_GRAVITY,
    override val viewType: String = "viewgroup",
    override val paddingHorizontal: Int = 0,
    override val paddingVertical: Int = 0,
    override val deepLink: String = "",
    override var children: MutableList<ComponentData> = mutableListOf(),
    override var collapsedChildren: MutableList<ComponentData> = mutableListOf(),
    override val expandChildrenOnClick: Boolean = false,
    val type: ViewGroupTypes = ViewGroupTypes.LINEAR,
    val background: AndromedaColor = AndromedaColor.TRANSPARENT,
    val radius: Float = 4f,
    val orientation: ORIENTATION = ORIENTATION.VERTICAL,
    val marginsHorizontal: Int = 0,
    val marginsVertical: Int = 0,
    val cardElevation: Float? = 4.0f,
    val spanCount: Int = 3,
    override var isCollapsed: Boolean = false
) : ComponentDataWithCollapseAction
