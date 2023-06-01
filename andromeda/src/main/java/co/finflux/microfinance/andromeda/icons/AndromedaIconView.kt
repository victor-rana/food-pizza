package co.app.food.andromeda.icons

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import co.app.food.andromeda.R
import co.app.food.andromeda.assets.icon.Icon
import co.app.food.andromeda.extensions.readAttributes

/**
 * Andromeda Andromeda Icon View Component
 *
 * ```xml
 * <co.app.food.andromeda.icons.AndromedaIconView
 *     android:id="@+id/icon_view"
 *     android:layout_width="wrap_content"
 *     android:layout_height="wrap_content"
 *     app:icon_name="navigation_24_history"
 *     app:icon_color_token="?attr/fill_active_primary" />
 * ```
 *
 * Use [setIcon] to set icon
 * Use `app:icon_name` and `app:icon_color_token` to set values in xml
 * @see [Icon]
 *
 *
 */
open class AndromedaIconView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : AppCompatImageView(context, attrs) {

    init {
        readAttributes(attrs, R.styleable.AndromedaIconView) { typedArray ->
            val iconNameIndex = typedArray.getInt(R.styleable.AndromedaIconView_icon_name, -1)
            val tintColor = typedArray.getInt(R.styleable.AndromedaIconView_icon_color_token, 0)
            if (iconNameIndex != -1) {
                val iconName = Icon.values()[iconNameIndex]
                setIcon(iconName, tintColor)
            }
        }
    }

    open fun setIcon(iconName: Icon, tintColorToken: Int) {
        setImageDrawable(IconManager.getIconDrawable(context, iconName, tintColorToken))
    }
}
