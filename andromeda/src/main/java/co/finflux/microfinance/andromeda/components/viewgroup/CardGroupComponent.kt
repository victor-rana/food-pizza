package co.app.food.andromeda.components.viewgroup

import android.annotation.SuppressLint
import android.view.Gravity
import android.view.ViewGroup.LayoutParams
import android.widget.LinearLayout
import androidx.cardview.widget.CardView
import co.app.food.andromeda.ExpandHandler
import co.app.food.andromeda.R
import co.app.food.andromeda.components.Height
import co.app.food.andromeda.components.Width
import co.app.food.andromeda.components.transformToAndroidGravity
import co.app.food.andromeda.components.viewgroup.data.ViewGroupComponentData
import co.app.food.andromeda.extensions.clickable
import co.app.food.andromeda.extensions.dpToPixels
import co.app.food.andromeda.extensions.unclickable
import co.app.food.andromeda.toColorToken
import com.airbnb.epoxy.*

@SuppressLint("NonConstantResourceId")
@EpoxyModelClass
abstract class CardGroupComponent(models: List<EpoxyModel<*>>) :
    EpoxyModelGroup(R.layout.layout_card_group_component, models) {

    @EpoxyAttribute
    lateinit var viewGroup: ViewGroupComponentData

    @EpoxyAttribute
    var expandHandler: ExpandHandler = {}

    override fun bind(holder: ModelGroupHolder) {
        super.bind(holder)
        val cardView = holder.rootView as CardView
        val linearLayout =
            holder.rootView.findViewById<LinearLayout>(R.id.epoxy_model_group_child_container)

        setLinearLayoutParams(linearLayout)
        setCardViewParams(cardView)

        with(holder.rootView) {
            if (viewGroup.isViewClickable()) {
                clickable()
            } else {
                setOnClickListener(null)
                unclickable()
            }

            if (viewGroup.expandChildrenOnClick) {
                clickable()
                setOnClickListener {
                    expandHandler(viewGroup.id)
                }
            }
        }
    }

    private fun setCardViewParams(cardView: CardView) {
        with(cardView) {
            radius = this@CardGroupComponent.viewGroup.radius
            val layoutParams = cardView.layoutParams as android.view.ViewGroup.MarginLayoutParams
            layoutParams.apply {
                width = this@CardGroupComponent.viewGroup.width.valueInPx.dpToPixels()
                height = this@CardGroupComponent.viewGroup.height.valueInPx.dpToPixels()
                when {
                    this@CardGroupComponent.viewGroup.width == Width.WRAP -> {
                        width = LayoutParams.WRAP_CONTENT
                    }
                    this@CardGroupComponent.viewGroup.width == Width.FILL -> {
                        width = LayoutParams.MATCH_PARENT
                    }
                    this@CardGroupComponent.viewGroup.height == Height.WRAP -> {
                        height = LayoutParams.WRAP_CONTENT
                    }
                    this@CardGroupComponent.viewGroup.height == Height.FILL -> {
                        height = LayoutParams.MATCH_PARENT
                    }
                }
                setMargins(
                    this@CardGroupComponent.viewGroup.marginsHorizontal.dpToPixels(),
                    this@CardGroupComponent.viewGroup.marginsVertical.dpToPixels(),
                    this@CardGroupComponent.viewGroup.marginsHorizontal.dpToPixels(),
                    this@CardGroupComponent.viewGroup.marginsVertical.dpToPixels()
                )
            }
            cardElevation = viewGroup.cardElevation ?: 4.0f
            foregroundGravity = Gravity.NO_GRAVITY
            setCardBackgroundColor(viewGroup.background.toColorToken())
            this.setContentPadding(
                viewGroup.paddingHorizontal.dpToPixels(),
                viewGroup.paddingVertical.dpToPixels(),
                viewGroup.paddingHorizontal.dpToPixels(),
                viewGroup.paddingVertical.dpToPixels()
            )
        }
    }

    private fun setLinearLayoutParams(linearLayout: LinearLayout) {
        linearLayout.orientation = this@CardGroupComponent.viewGroup.orientation.direction
        linearLayout.gravity = this@CardGroupComponent.viewGroup.gravity.transformToAndroidGravity()
    }
}
