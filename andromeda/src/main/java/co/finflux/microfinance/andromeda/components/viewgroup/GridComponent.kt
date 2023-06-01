package co.app.food.andromeda.components.viewgroup

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.updateLayoutParams
import co.app.food.andromeda.ComposableHolder
import co.app.food.andromeda.R
import co.app.food.andromeda.components.Gravity
import co.app.food.andromeda.components.Height
import co.app.food.andromeda.components.Width
import co.app.food.andromeda.components.viewgroup.data.ViewGroupComponentData
import co.app.food.andromeda.extensions.clickable
import co.app.food.andromeda.extensions.dpToPixels
import co.app.food.andromeda.extensions.unclickable
import co.app.food.andromeda.list.AndromedaListView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder

@SuppressLint("NonConstantResourceId")
@EpoxyModelClass
abstract class GridComponent : EpoxyModelWithHolder<GridComponent.Holder>() {

    @EpoxyAttribute
    lateinit var gridComponentData: ViewGroupComponentData

    override fun getDefaultLayout(): Int {
        return R.layout.andromeda_grid
    }

    override fun bind(holder: Holder) {
        super.bind(holder)
        holder.rootView.updateLayoutParams<ViewGroup.LayoutParams> {
            width = gridComponentData.width.valueInPx.dpToPixels()
            height = gridComponentData.height.valueInPx.dpToPixels()
            if (gridComponentData.width == Width.WRAP) {
                width = ViewGroup.LayoutParams.WRAP_CONTENT
            }
            if (gridComponentData.width == Width.FILL) {
                width = ViewGroup.LayoutParams.MATCH_PARENT
            }
            if (gridComponentData.height == Height.WRAP) {
                height = ViewGroup.LayoutParams.WRAP_CONTENT
            }
            if (gridComponentData.height == Height.FILL) {
                height = ViewGroup.LayoutParams.MATCH_PARENT
            }
        }
        holder.rootView.setPadding(
            gridComponentData.paddingHorizontal.dpToPixels(),
            gridComponentData.paddingVertical.dpToPixels(),
            gridComponentData.paddingHorizontal.dpToPixels(),
            gridComponentData.paddingVertical.dpToPixels()
        )
        holder.gridERV.setSpanCount(gridComponentData.spanCount)
        holder.gridERV.updateLayoutParams<ConstraintLayout.LayoutParams> {
            topMargin = gridComponentData.marginsVertical.dpToPixels()
            bottomMargin = gridComponentData.marginsVertical.dpToPixels()
            marginStart = gridComponentData.marginsHorizontal.dpToPixels()
            marginEnd = gridComponentData.marginsHorizontal.dpToPixels()

            when (gridComponentData.gravity) {
                Gravity.START -> {
                    this.startToStart = ConstraintSet.PARENT_ID
                }
                Gravity.END -> {
                    this.endToEnd = ConstraintSet.PARENT_ID
                }
                Gravity.TOP -> {
                    this.topToTop = ConstraintSet.PARENT_ID
                }
                Gravity.BOTTOM -> {
                    this.bottomToBottom = ConstraintSet.PARENT_ID
                }
                Gravity.CENTERVERTICAL -> {
                    this.topToTop = ConstraintSet.PARENT_ID
                    this.bottomToBottom = ConstraintSet.PARENT_ID
                    this.endToEnd = ConstraintSet.PARENT_ID
                    this.startToStart = ConstraintSet.PARENT_ID
                }
                Gravity.CENTERVERTICAL -> {
                    this.topToTop = ConstraintSet.PARENT_ID
                    this.bottomToBottom = ConstraintSet.PARENT_ID
                }
                Gravity.CENTERHORIZONTAL -> {
                    this.endToEnd = ConstraintSet.PARENT_ID
                    this.startToStart = ConstraintSet.PARENT_ID
                }
                else -> {
                    this.startToStart = ConstraintSet.PARENT_ID
                    this.topToTop = ConstraintSet.PARENT_ID
                }
            }
        }

        if (gridComponentData.isViewClickable()) {
            holder.rootView.clickable()
        } else {
            holder.rootView.setOnClickListener(null)
            holder.rootView.unclickable()
        }
        holder.gridERV.setUpComponents(gridComponentData.children)
        holder.gridERV.background = null
    }

    class Holder : ComposableHolder() {
        val gridERV by bind<AndromedaListView>(R.id.ervGrid)
        val rootView by bind<ConstraintLayout>(R.id.root)
    }
}
