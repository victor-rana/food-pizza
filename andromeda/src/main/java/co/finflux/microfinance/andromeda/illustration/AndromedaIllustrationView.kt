package co.app.food.andromeda.illustration

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import co.app.food.andromeda.R
import co.app.food.andromeda.assets.illustration.Illustration
import co.app.food.andromeda.extensions.readAttributes

/**
 * Andromeda Illustration View
 *
 * XML usage:
 * ```xml
 * <co.app.food.andromeda.illustration.AlohaIllustrationView
 *  android:layout_width="wrap_content"
 *  android:layout_height="wrap_content"
 *  app:illustration_name="auth_spot_connect_facebook" />
 * ```
 *
 * [setIllustration] or `app:illustration_name` to set the illustration.
 *
 */
class AndromedaIllustrationView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : AppCompatImageView(context, attrs) {

    init {
        readAttributes(attrs, R.styleable.AndromedaIllustrationView) { typedArray ->
            val illustrationOrdinal = typedArray.getInt(
                R.styleable.AndromedaIllustrationView_illustration_name,
                -1
            )
            if (illustrationOrdinal != -1) {
                val illustration = Illustration.values()[illustrationOrdinal]
                IllustrationManager.getIllustrationDrawable(context, illustration) { drawable ->
                    setImageDrawable(drawable)
                }
            }
        }
    }

    fun setIllustration(illustration: Illustration) {
        IllustrationManager.getIllustrationDrawable(context, illustration) {
            setImageDrawable(it)
        }
    }
}
