package co.app.food.andromeda.components.viewgroup

import android.annotation.SuppressLint
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.view.updateLayoutParams
import co.app.food.andromeda.ExpandHandler
import co.app.food.andromeda.R
import co.app.food.andromeda.components.Height
import co.app.food.andromeda.components.Width
import co.app.food.andromeda.components.transformToAndroidGravity
import co.app.food.andromeda.components.viewgroup.data.ViewGroupComponentData
import co.app.food.andromeda.extensions.clickable
import co.app.food.andromeda.extensions.dpToPixels
import co.app.food.andromeda.extensions.unclickable
import com.airbnb.epoxy.*
import android.graphics.Color as AndroidColor

@SuppressLint("NonConstantResourceId")
@EpoxyModelClass
abstract class LinearGroupComponent(models: List<EpoxyModel<*>>) :
    EpoxyModelGroup(R.layout.layout_linear_group_component, models) {

    @EpoxyAttribute
    lateinit var viewGroupData: ViewGroupComponentData
    @EpoxyAttribute
    var expandHandler: ExpandHandler = {}

    override fun bind(holder: ModelGroupHolder) {
        super.bind(holder)
        val root = holder.rootView as LinearLayout
        with(root) {
            orientation = this@LinearGroupComponent.viewGroupData.orientation.direction
            setBackgroundColor(AndroidColor.parseColor(viewGroupData.background.value))
            updateLayoutParams<ViewGroup.MarginLayoutParams> {
                width = this@LinearGroupComponent.viewGroupData.width.valueInPx
                height = this@LinearGroupComponent.viewGroupData.height.valueInPx
                if (this@LinearGroupComponent.viewGroupData.width == Width.WRAP) {
                    width = ViewGroup.LayoutParams.WRAP_CONTENT
                }
                if (this@LinearGroupComponent.viewGroupData.width == Width.FILL) {
                    width = ViewGroup.LayoutParams.MATCH_PARENT
                }
                if (this@LinearGroupComponent.viewGroupData.height == Height.WRAP) {
                    height = ViewGroup.LayoutParams.WRAP_CONTENT
                }
                if (this@LinearGroupComponent.viewGroupData.height == Height.FILL) {
                    height = ViewGroup.LayoutParams.MATCH_PARENT
                }
                setMargins(
                    viewGroupData.marginsHorizontal.dpToPixels(),
                    viewGroupData.marginsVertical.dpToPixels(),
                    viewGroupData.marginsHorizontal.dpToPixels(),
                    viewGroupData.marginsVertical.dpToPixels()
                )
                gravity = viewGroupData.gravity.transformToAndroidGravity()
                setPadding(
                    viewGroupData.paddingHorizontal.dpToPixels(),
                    viewGroupData.paddingVertical.dpToPixels(),
                    viewGroupData.paddingHorizontal.dpToPixels(),
                    viewGroupData.paddingVertical.dpToPixels()
                )
                isBaselineAligned = false
            }

            if (viewGroupData.deepLink.isNotEmpty()) {
                clickable()
            } else {
                setOnClickListener(null)
                unclickable()
            }

            if (viewGroupData.expandChildrenOnClick) {
                clickable()
                setOnClickListener {
                    expandHandler(viewGroupData.id)
                }
            }
        }
    }
}
