package co.app.food.andromeda.components

import android.os.Parcelable
import android.view.ViewGroup

interface ComponentData : Parcelable {
    val id: String
    val width: Width
    val height: Height
    val viewType: String
    val paddingHorizontal: Int
    val paddingVertical: Int
    val gravity: Gravity
}

interface ComponentDataWithClick : ComponentData, ViewClickBehaviour {
    val deepLink: String
    override fun isViewClickable(): Boolean {
        return deepLink.isNotEmpty()
    }
}

interface ComponentDataWithInput : ComponentData {
    val key: String
}

interface ComponentDataWithCollapseAction : ComponentDataWithClick {
    var isCollapsed: Boolean
    var children: MutableList<ComponentData>
    var collapsedChildren: MutableList<ComponentData>
    val expandChildrenOnClick: Boolean
}

interface ComponentDataWithClickableInput : ComponentData, ViewClickBehaviour {
    val deepLink: String
    val key: String
    override fun isViewClickable(): Boolean {
        return deepLink.isNotEmpty()
    }
}

enum class Width(val valueInPx: Int) {
    WRAP(ViewGroup.LayoutParams.WRAP_CONTENT),
    FILL(ViewGroup.LayoutParams.MATCH_PARENT),
    SMALL(150),
    MEDIUM(250),
    LARGE(350),
    EXTRA_LARGE(450),
    IMAGE_SMALL(48),
    IMAGE_MEDIUM(64),
    IMAGE_LARGE(80),
    IMAGE_MAX(300),
    IMAGE_EXTRA_LARGE(120)
}

enum class Height(val valueInPx: Int) {
    WRAP(ViewGroup.LayoutParams.WRAP_CONTENT),
    FILL(ViewGroup.LayoutParams.MATCH_PARENT),
    SMALL(valueInPx = 106),
    MEDIUM(valueInPx = 250),
    LARGE(valueInPx = 350),
    EXTRA_LARGE(valueInPx = 450),
    IMAGE_SMALL(valueInPx = 48),
    IMAGE_MEDIUM(valueInPx = 64),
    IMAGE_LARGE(valueInPx = 80),
    DIVIDER(valueInPx = 1),
    PROGRESS(valueInPx = 6),
    IMAGE_MAX(300),
    IMAGE_EXTRA_LARGE(valueInPx = 120)
}

enum class Gravity {
    NO_GRAVITY,
    START,
    END,
    TOP,
    BOTTOM,
    CENTER,
    CENTERHORIZONTAL,
    CENTERVERTICAL,
    TOP_START,
    TOP_END,
    BOTTOM_START,
    BOTTOM_END,
    CENTERTOP,
    CENTERBOTTOM,
    CENTERRIGHT,
    CENTERLEFT
}

fun Gravity.transformToAndroidGravity(): Int {
    return when (this) {
        Gravity.NO_GRAVITY -> android.view.Gravity.NO_GRAVITY
        Gravity.CENTER -> android.view.Gravity.CENTER
        Gravity.CENTERHORIZONTAL -> android.view.Gravity.CENTER_HORIZONTAL
        Gravity.CENTERVERTICAL -> android.view.Gravity.CENTER_VERTICAL
        Gravity.START -> android.view.Gravity.START
        Gravity.END -> android.view.Gravity.END
        Gravity.TOP -> android.view.Gravity.TOP
        Gravity.BOTTOM -> android.view.Gravity.BOTTOM
        Gravity.BOTTOM_END -> android.view.Gravity.BOTTOM or android.view.Gravity.END
        Gravity.TOP_START -> android.view.Gravity.TOP or android.view.Gravity.START
        Gravity.TOP_END -> android.view.Gravity.TOP or android.view.Gravity.END
        Gravity.BOTTOM_START -> android.view.Gravity.BOTTOM or android.view.Gravity.START
        Gravity.CENTERTOP -> android.view.Gravity.CENTER or android.view.Gravity.TOP
        Gravity.CENTERBOTTOM -> android.view.Gravity.CENTER or android.view.Gravity.BOTTOM
        Gravity.CENTERRIGHT -> android.view.Gravity.CENTER or android.view.Gravity.END
        Gravity.CENTERLEFT -> android.view.Gravity.CENTER or android.view.Gravity.START
    }
}
