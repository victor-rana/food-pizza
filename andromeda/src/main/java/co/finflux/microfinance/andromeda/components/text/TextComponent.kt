package co.app.food.andromeda.components.text

import android.annotation.SuppressLint
import android.content.Intent
import android.util.TypedValue
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.view.updateLayoutParams
import co.app.food.andromeda.Color as AndromedaColor
import co.app.food.andromeda.ComposableHolder
import co.app.food.andromeda.R
import co.app.food.andromeda.components.Height
import co.app.food.andromeda.components.Width
import co.app.food.andromeda.components.text.data.TextComponentData
import co.app.food.andromeda.components.transformToAndroidGravity
import co.app.food.andromeda.extensions.clickable
import co.app.food.andromeda.extensions.dpToPixels
import co.app.food.andromeda.extensions.unclickable
import co.app.food.andromeda.text.AndromedaTextView
import co.app.food.andromeda.toColorToken
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import android.graphics.Color as AndroidColor

@SuppressLint("NonConstantResourceId")
@EpoxyModelClass
abstract class TextComponent : EpoxyModelWithHolder<TextComponent.Holder>() {

    @EpoxyAttribute
    lateinit var textComponentData: TextComponentData

    @EpoxyAttribute
    var deepLinkHandler: (String) -> Unit = {}

    override fun getDefaultLayout(): Int {
        return R.layout.layout_text_component
    }

    override fun bind(holder: Holder) {
        super.bind(holder)
        holder.rootContainer.setBackgroundColor(
            AndroidColor.parseColor(textComponentData.bgColor.value)
        )

        with(holder.textView) {
            typographyStyle = textComponentData.textStyle
            if (!textComponentData.textSpacingExtraEnabled) {
                setLineSpacing(
                    TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP,
                        0.0f,
                        holder.rootContainer.context.resources.displayMetrics
                    ),
                    1.0f
                )
            }

            text = textComponentData.text
            if (textComponentData.textColor != AndromedaColor.DEFAULT) {
                setTextColor(textComponentData.textColor.toColorToken())
            }
            gravity = textComponentData.gravity.transformToAndroidGravity()
            setPadding(
                textComponentData.marginsHorizontal.dpToPixels(),
                textComponentData.marginsVertical.dpToPixels(),
                textComponentData.marginsHorizontal.dpToPixels(),
                textComponentData.marginsVertical.dpToPixels()
            )
        }

        with(holder.rootContainer) {
            updateLayoutParams<ViewGroup.MarginLayoutParams> {
                width = this@TextComponent.textComponentData.width.valueInPx.dpToPixels()
                height = this@TextComponent.textComponentData.height.valueInPx.dpToPixels()
                if (this@TextComponent.textComponentData.width == Width.WRAP) {
                    width = ViewGroup.LayoutParams.WRAP_CONTENT
                }
                if (this@TextComponent.textComponentData.width == Width.FILL) {
                    width = ViewGroup.LayoutParams.MATCH_PARENT
                }
                if (this@TextComponent.textComponentData.height == Height.WRAP) {
                    height = ViewGroup.LayoutParams.WRAP_CONTENT
                }
                if (this@TextComponent.textComponentData.height == Height.FILL) {
                    height = ViewGroup.LayoutParams.MATCH_PARENT
                }
            }

            gravity = textComponentData.gravity.transformToAndroidGravity()
            setPadding(
                textComponentData.paddingHorizontal.dpToPixels(),
                textComponentData.paddingVertical.dpToPixels(),
                textComponentData.paddingHorizontal.dpToPixels(),
                textComponentData.paddingVertical.dpToPixels()
            )
        }
        if (!textComponentData.navigateToOnClick.isNullOrEmpty()) {
            holder.rootContainer.setOnClickListener {
                holder.rootContainer.context.startActivity(
                    Intent(
                        holder.rootContainer.context,
                        Class.forName(textComponentData.navigateToOnClick!!)
                    )
                )
            }
            holder.rootContainer.clickable()
        } else {
            if (textComponentData.isViewClickable()) {
                holder.rootContainer.setOnClickListener {
                    deepLinkHandler(textComponentData.deepLink)
                }
                holder.rootContainer.clickable()
            } else {
                holder.rootContainer.setOnClickListener(null)
                holder.rootContainer.unclickable()
            }
        }
    }

    inner class Holder : ComposableHolder() {
        val rootContainer by bind<LinearLayout>(R.id.rootContainer)
        val textView by bind<AndromedaTextView>(R.id.textView)
    }
}
