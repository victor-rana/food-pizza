package co.app.food.andromeda.text

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.widget.TextViewCompat
import co.app.food.andromeda.Color as AndromedaColor
import co.app.food.andromeda.R
import co.app.food.andromeda.extensions.readAttributes
import android.graphics.Color as AndroidColor

/**
 * Andromeda TextView Component
 *
 * XML Usage:
 * ```xml
 * <co.app.food.andromeda.text.AndromedaTextView
 *  android:layout_width="wrap_content"
 *  android:layout_height="wrap_content"
 *  android:text="Some text"
 *  app:typographyStyle="title_hero_default" />
 * ```
 *
 * You can set the Typography Style to the AndromedaTextView by setting the value of [typographyStyle]
 * or the xml attribute `app:typographyStyle`. By default the value of [typographyStyle] is
 * [TypographyStyle.TITLE_HERO_DEFAULT]
 *
 */

open class AndromedaTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : AppCompatTextView(context, attrs) {

    var typographyStyle: TypographyStyle = TypographyStyle.TITLE_HERO_DEFAULT
        set(value) {
            field = value
            setStyle(value.style, true)
        }

    private var customTextColor: Int = -1

    private var spacingAdd = 0f

    init {
        readAttributes(attrs, R.styleable.AndromedaTextView) { typedArray ->
            val styleOrdinal = typedArray.getInt(R.styleable.AndromedaTextView_typographyStyle, 0)
            typographyStyle = TypographyStyle.values()[styleOrdinal]
            this.customTextColor =
                typedArray.getColor(R.styleable.AndromedaTextView_textColor, this.customTextColor)
        }
        updateTextColor()
    }

    private fun updateTextColor() {
        if (customTextColor != -1) {
            setTextColor(customTextColor)
        }
    }

    override fun setLineSpacing(add: Float, mult: Float) {
        super.setLineSpacing(add, mult)
        spacingAdd = add
    }

    override fun setText(text: CharSequence?, type: BufferType?) {
        super.setText(text, type)
        // on Kitkat and below lineSpacingExtra have effect on single line text as well.
        // Setting lineSpacingExtra will mess up the alignment in pre-lollipop devices for single line text
        if (lineCount < 2) {
            super.setLineSpacing(0f, 1f)
        } else {
            // If the lineSpacingExtra was same before text change, don't trigger another redraw by setting same lineSpacingExtra
            if (lineSpacingExtra != spacingAdd) {
                super.setLineSpacing(spacingAdd, 1f)
            }
        }
    }

    fun setTextColor(color: AndromedaColor) {
        when (color) {
            AndromedaColor.DEFAULT,
            AndromedaColor.PRIMARY -> {
                TextViewCompat.setTextAppearance(this, typographyStyle.style)
            }
            else -> super.setTextColor(AndroidColor.parseColor(color.value))
        }
    }
}
