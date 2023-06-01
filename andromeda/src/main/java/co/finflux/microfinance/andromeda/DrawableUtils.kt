package co.app.food.andromeda

import android.content.Context
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.GradientDrawable.RECTANGLE
import android.graphics.drawable.StateListDrawable
import android.util.StateSet

internal fun generateRoundedRectangleDrawable(
    context: Context,
    solidColorAttr: Int,
    cornerRadius: Float,
    strokeWidth: Int = 0,
    strokeColorAttr: Int = 0
): Drawable {
    return generateRoundedRectangleDrawable(
        context,
        solidColorAttr,
        floatArrayOf(
            cornerRadius,
            cornerRadius,
            cornerRadius,
            cornerRadius
        ),
        strokeWidth,
        strokeColorAttr
    )
}

internal fun generateRoundedRectangleDrawable(
    context: Context,
    solidColorAttr: Int,
    cornerRadius: FloatArray,
    strokeWidth: Int = 0,
    strokeColorAttr: Int = 0
): Drawable {

    require(cornerRadius.size == 4) {
        "cornerRadius array should have 4 values. CornerRadius values for each corner"
    }

    return GradientDrawable().apply {
        shape = RECTANGLE
        cornerRadii = floatArrayOf(
            cornerRadius[0],
            cornerRadius[0],
            cornerRadius[1],
            cornerRadius[1],
            cornerRadius[2],
            cornerRadius[2],
            cornerRadius[3],
            cornerRadius[3]
        )
        setColor(
            AndromedaAttributeManager.getColorFromAttribute(
                context,
                solidColorAttr
            )
        )
        if (strokeWidth > 0) {
            setStroke(
                strokeWidth,
                AndromedaAttributeManager.getColorFromAttribute(
                    context,
                    strokeColorAttr
                )
            )
        }
    }
}

internal fun generateSelector(
    normalStateDrawable: Drawable,
    pressedStateDrawable: Drawable
): StateListDrawable {
    return StateListDrawable().apply {
        addState(intArrayOf(android.R.attr.state_pressed), pressedStateDrawable)
        addState(StateSet.WILD_CARD, normalStateDrawable)
    }
}
