package co.app.food.andromeda.icons

import android.content.Context
import android.graphics.drawable.Drawable
import co.app.food.andromeda.AndromedaAttributeManager
import co.app.food.andromeda.R
import co.app.food.andromeda.assets.icon.Icon
import co.app.food.andromeda.extensions.getDrawableCompat
import co.app.food.andromeda.extensions.tint
import co.app.food.andromeda.icons.IconManager.getIconDrawable

/**
 * Andromeda Icon Manager
 *
 * [getIconDrawable] Get icon using iconName and tintColor
 * @see [Icon]
 */
object IconManager {

    fun getIconDrawable(context: Context, icon: Icon, tintColorToken: Int): Drawable {
        val drawable = context.getDrawableCompat(icon.getDrawableResId())
            ?: requireNotNull(context.getDrawableCompat(R.drawable.andromeda_icon_placeholder))
        drawable.mutate()
        return when (tintColorToken) {
            -1 -> {
                drawable.tint(
                    AndromedaAttributeManager.getColorFromAttribute(
                        context,
                        R.attr.icon_static_white
                    )
                )
            }
            -999 -> {
                drawable.tint(
                    AndromedaAttributeManager.getColorFromAttribute(
                        context,
                        R.attr.fill_active_primary
                    )
                )
            }
            0 -> {
                drawable
            }
            else -> {
                drawable.tint(tintColorToken)
            }
        }
    }
}
