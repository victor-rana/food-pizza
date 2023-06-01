package co.app.food.andromeda.components.button

import android.annotation.SuppressLint
import android.os.Handler
import android.os.Looper
import android.view.ViewGroup
import android.widget.LinearLayout
import co.app.food.andromeda.ComposableHolder
import co.app.food.andromeda.R
import co.app.food.andromeda.button.AndromedaCircularButton
import co.app.food.andromeda.components.button.data.CircularButtonComponentData
import co.app.food.andromeda.components.transformToAndroidGravity
import co.app.food.andromeda.extensions.dpToPixels
import co.app.food.andromeda.icons.IconData
import co.app.food.andromeda.toColorToken
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder

@SuppressLint("NonConstantResourceId")
@EpoxyModelClass
abstract class CircularButtonComponent : EpoxyModelWithHolder<CircularButtonComponent.Holder>() {

    @EpoxyAttribute
    lateinit var circularButtonData: CircularButtonComponentData

    @EpoxyAttribute
    var deepLinkHandler: (String) -> Unit = {}

    override fun getDefaultLayout(): Int {
        return R.layout.layout_button_component
    }

    override fun bind(holder: Holder) {
        super.bind(holder)
        addButtonDynamically(holder, circularButtonData)
    }

    private fun addButtonDynamically(
        holder: Holder,
        circularButtonData: CircularButtonComponentData
    ) {
        val button = AndromedaCircularButton(holder.rootContainer.context)
        val buttonIcon = IconData(
            circularButtonData.iconData.iconName,
            circularButtonData.iconData.iconColor.toColorToken()
        )
        button.init(
            circularButtonType = circularButtonData.style,
            iconData = buttonIcon
        )
        with(circularButtonData) {
            button.isEnabled = isEnabled
            button.setOnClickListener {
                deepLinkHandler(deepLink)
                if (shouldLoadOnClick) {
                    button.showLoader()
                    Handler(Looper.getMainLooper()).postDelayed({
                        button.hideLoader()
                    }, 2000)
                }
            }
        }

        with(holder.rootContainer) {
            removeAllViews()
            gravity = circularButtonData.gravity.transformToAndroidGravity()
            addView(button)
            (layoutParams as ViewGroup.MarginLayoutParams).apply {
                setMargins(
                    circularButtonData.marginsHorizontal.dpToPixels(),
                    circularButtonData.marginsVertical.dpToPixels(),
                    circularButtonData.marginsHorizontal.dpToPixels(),
                    circularButtonData.marginsVertical.dpToPixels()
                )
            }
            setPadding(
                circularButtonData.paddingHorizontal.dpToPixels(),
                circularButtonData.paddingVertical.dpToPixels(),
                circularButtonData.paddingHorizontal.dpToPixels(),
                circularButtonData.paddingVertical.dpToPixels()
            )
        }
    }

    inner class Holder : ComposableHolder() {
        val rootContainer by bind<LinearLayout>(R.id.rootContainer)
    }
}
