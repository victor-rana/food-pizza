package co.app.food.andromeda.components.button

import android.annotation.SuppressLint
import android.os.Handler
import android.os.Looper
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.view.updateLayoutParams
import co.app.food.andromeda.ComposableHolder
import co.app.food.andromeda.R
import co.app.food.andromeda.button.AndromedaButton
import co.app.food.andromeda.button.IconPosition
import co.app.food.andromeda.components.Height
import co.app.food.andromeda.components.Width
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import co.app.food.andromeda.components.button.data.ButtonComponentData
import co.app.food.andromeda.components.transformToAndroidGravity
import co.app.food.andromeda.extensions.dpToPixels
import co.app.food.andromeda.icons.IconData
import co.app.food.andromeda.toColorToken

@SuppressLint("NonConstantResourceId")
@EpoxyModelClass
abstract class ButtonComponent : EpoxyModelWithHolder<ButtonComponent.Holder>() {

    @EpoxyAttribute
    lateinit var buttComponentData: ButtonComponentData

    override fun getDefaultLayout(): Int {
        return R.layout.layout_button_component
    }

    override fun bind(holder: Holder) {
        super.bind(holder)
        addButtonDynamically(holder, buttComponentData)
    }

    private fun addButtonDynamically(holder: Holder, buttComponentData: ButtonComponentData) {
        val button = AndromedaButton(holder.rootContainer.context)
        button.init(
            buttonType = buttComponentData.style,
            radius = buttComponentData.radius,
            text = buttComponentData.text
        )
        with(buttComponentData) {
            if (iconData != null) {
                val buttonIcon = IconData(iconData.iconName, iconData.iconColor.toColorToken())
                button.setIcon(iconData = buttonIcon, position = iconPosition ?: IconPosition.LEFT)
            }
            button.isEnabled = isEnabled
            button.setDebouncedClickListener {
                if (shouldLoadOnClick) {
                    button.showLoader()
                    Handler(Looper.getMainLooper()).postDelayed({
                        button.hideLoader()
                    }, 2000)
                }
            }
        }

        if (buttComponentData.style != AndromedaButton.ButtonType.PRIMARY_POSITIVE_TINY ||
            buttComponentData.style != AndromedaButton.ButtonType.SECONDARY_POSITIVE_TINY ||
            buttComponentData.style != AndromedaButton.ButtonType.TERTIARY_POSITIVE_TINY
        ) {
            with(holder.rootContainer) {
                removeAllViews()
                gravity = buttComponentData.gravity.transformToAndroidGravity()
                addView(button)
                (layoutParams as ViewGroup.MarginLayoutParams).apply {
                    setMargins(
                        buttComponentData.marginsHorizontal.dpToPixels(),
                        buttComponentData.marginsVertical.dpToPixels(),
                        buttComponentData.marginsHorizontal.dpToPixels(),
                        buttComponentData.marginsVertical.dpToPixels()
                    )
                }
            }
            button.setPadding(
                buttComponentData.paddingHorizontal.dpToPixels(),
                buttComponentData.paddingVertical.dpToPixels(),
                buttComponentData.paddingHorizontal.dpToPixels(),
                buttComponentData.paddingVertical.dpToPixels()
            )
        }
        if (
            buttComponentData.style == AndromedaButton.ButtonType.PRIMARY_POSITIVE_TINY ||
            buttComponentData.style == AndromedaButton.ButtonType.PRIMARY_DESTRUCTIVE_TINY ||
            buttComponentData.style == AndromedaButton.ButtonType.SECONDARY_DESTRUCTIVE_TINY ||
            buttComponentData.style == AndromedaButton.ButtonType.SECONDARY_POSITIVE_TINY ||
            buttComponentData.style == AndromedaButton.ButtonType.SECONDARY_STATIC_WHITE_TINY ||
            buttComponentData.style == AndromedaButton.ButtonType.TERTIARY_POSITIVE_TINY ||
            buttComponentData.style == AndromedaButton.ButtonType.TERTIARY_DESTRUCTIVE_TINY
        ) {
            holder.rootContainer.updateLayoutParams<ViewGroup.LayoutParams> {
                width = buttComponentData.width.valueInPx.dpToPixels()
                height = buttComponentData.height.valueInPx.dpToPixels()
                if (buttComponentData.width == Width.WRAP) {
                    width = LinearLayout.LayoutParams.WRAP_CONTENT
                }
                if (buttComponentData.width == Width.FILL) {
                    width = LinearLayout.LayoutParams.MATCH_PARENT
                }
                if (buttComponentData.height == Height.WRAP) {
                    height = LinearLayout.LayoutParams.WRAP_CONTENT
                }
                if (buttComponentData.height == Height.FILL) {
                    height = LinearLayout.LayoutParams.MATCH_PARENT
                }
            }
            button.updateLayoutParams<LinearLayout.LayoutParams> {
                gravity = buttComponentData.gravity.transformToAndroidGravity()
            }
        } else {
            button.updateLayoutParams<LinearLayout.LayoutParams> {
                width = buttComponentData.width.valueInPx.dpToPixels()
                height = buttComponentData.height.valueInPx.dpToPixels()
                if (buttComponentData.width == Width.WRAP) {
                    width = LinearLayout.LayoutParams.WRAP_CONTENT
                }
                if (buttComponentData.width == Width.FILL) {
                    width = LinearLayout.LayoutParams.MATCH_PARENT
                }
                if (buttComponentData.height == Height.WRAP) {
                    height = LinearLayout.LayoutParams.WRAP_CONTENT
                }
                if (buttComponentData.height == Height.FILL) {
                    height = LinearLayout.LayoutParams.MATCH_PARENT
                }
            }
        }
    }

    inner class Holder : ComposableHolder() {
        val rootContainer by bind<LinearLayout>(R.id.rootContainer)
    }
}
