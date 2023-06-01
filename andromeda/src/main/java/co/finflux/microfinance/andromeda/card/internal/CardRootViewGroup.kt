package co.app.food.andromeda.card.internal

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View.MeasureSpec.AT_MOST
import android.view.View.MeasureSpec.EXACTLY
import android.view.View.MeasureSpec.makeMeasureSpec
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import co.app.food.andromeda.R
import co.app.food.andromeda.extensions.readAttributes

internal class CardRootViewGroup @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null
) : ConstraintLayout(context, attributeSet) {

    private var measureOnNoLayoutChange = true

    var expandDirection = EXPAND_DIRECTION_UP

    init {
        readAttributes(attributeSet, R.styleable.CardRootViewGroup) {
            measureOnNoLayoutChange = it.getBoolean(
                R.styleable.CardRootViewGroup_measure_on_no_layout_change,
                measureOnNoLayoutChange
            )
        }
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        // If there is a change is size of the overall available height and/or width, everything happen as usual
        // No logic or special handling done for the card
        // It will happen when the first time card has height after measurement, height changed due to keyboard open/close events
        if (changed) {
            super.onLayout(changed, left, top, right, bottom)
        } else {
            // If there is no change in overall card dimension,
            // we still need to assign the child bounds to the changes to take place which triggered this layout pass
            val child = getChildAt(0)
            var childTop = child.top
            var childBottom = child.bottom
            // In case of Notch cards card dimensions doesn't change once measured, so this flag is set to false for Notch card
            // resulting no more measuring of child
            // But for dialog and fixed cards, card height depends on content height. For these card flag is set to true and measured on every layout pass
            if (measureOnNoLayoutChange) {
                child.measure(
                    makeMeasureSpec(width, EXACTLY),
                    makeMeasureSpec(height - paddingTop, AT_MOST)
                )
                if (expandDirection == EXPAND_DIRECTION_UP) {
                    childTop = child.bottom - child.measuredHeight
                } else {
                    childBottom = child.top + child.measuredHeight
                }
            }
            child.layout(child.left, childTop, child.right, childBottom)
        }
    }

    override fun fitSystemWindows(insets: Rect): Boolean {
        if (fitsSystemWindows) {
            ViewCompat.offsetTopAndBottom(this, -(bottom - insets.bottom))
        }
        return super.fitSystemWindows(insets)
    }

    companion object {
        const val EXPAND_DIRECTION_UP = 1
        const val EXPAND_DIRECTION_DOWN = 2
    }
}
