package co.app.food.andromeda.badge

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import co.app.food.andromeda.R
import co.app.food.andromeda.databinding.AndromedaBadgeBinding
import co.app.food.andromeda.ellipsize
import co.app.food.andromeda.extensions.*
import co.app.food.andromeda.tokens.border_static_white
import co.app.food.andromeda.tokens.fill_error_primary

/**
 * Andromeda Badge
 *
 * XML usage
 * ```xml
 * <co.app.food.andromeda.badge.AndromedaBadge
 *  android:layout_width="wrap_content"
 *  android:layout_height="wrap_content"
 *  app:badge_text="3"
 *  app:show_stroke="true"/>
 * ```
 *
 * Kotlin usage
 * ```kotlin
 * val badge = AndromedaBadge(this)
 * badge.setBadgeCount(2)
 * badge.showStroke()
 * // Add to parent
 * ```
 *
 * Use [setBadgeText] to set Badge text
 * Use [showStroke] or [hideStroke] to show/hide surrounding border on the badge
 *
 * ### Accessibility
 *
 * Use [setAccessibilityDescription] or `app:accessibility_description` attribute to set
 * accessibility description for screen readers.
 *
 */
class AndromedaBadge @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {
    private val binding: AndromedaBadgeBinding =
        AndromedaBadgeBinding.inflate(LayoutInflater.from(context), this)

    init {
        gravity = Gravity.CENTER
        var showStroke = false
        readAttributes(attrs, R.styleable.AndromedaBadge) { typedArray ->
            val badgeText = typedArray.getStringViaResources(R.styleable.AndromedaBadge_badge_text)
            if (!badgeText.isNullOrBlank()) {
                setBadgeText(badgeText)
            }
            showStroke = typedArray.getBoolean(
                R.styleable.AndromedaBadge_show_stroke,
                false
            )
            val accessibilityDescription =
                typedArray.getStringViaResources(
                    R.styleable.AndromedaBadge_accessibility_description
                )
            if (!accessibilityDescription.isNullOrBlank()) {
                setAccessibilityDescription(accessibilityDescription)
            }
        }
        setStroke(showStroke)
        setBackground()
    }

    fun setAccessibilityDescription(description: String) {
        binding.tvBadgeText.contentDescription = description
    }

    fun setBadgeText(badgeText: String) {
        binding.tvBadgeText.text = badgeText.take(3)
    }

    fun showStroke() {
        val drawable = binding.tvBadgeText.background.mutate() as GradientDrawable
        drawable.setStroke(1f.toPxInt(context), border_static_white)
    }

    fun hideStroke() {
        val drawable = binding.tvBadgeText.background.mutate() as GradientDrawable
        drawable.setStroke(
            0f.toPxInt(context),
            ContextCompat.getColor(context, R.color.andromeda_transparent)
        )
    }

    private fun setBackground() {
        val drawable = binding.tvBadgeText.getBackgroundDrawable<GradientDrawable>()
        drawable.setColor(fill_error_primary)
    }

    private fun setStroke(showStroke: Boolean) {
        if (showStroke) {
            showStroke()
        } else {
            hideStroke()
        }
    }

    private fun Int.sanitize(): String = toString().ellipsize(2)
}
