package co.app.food.andromeda.card.internal

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.core.view.NestedScrollingParent2
import androidx.core.view.NestedScrollingParentHelper
import androidx.core.view.ViewCompat.TYPE_TOUCH
import co.app.food.andromeda.ConsumeNestedScroll
import co.app.food.andromeda.OnNestedScrollStart
import co.app.food.andromeda.OnNestedScrollStop
import co.app.food.andromeda.extensions.toPxInt

internal class CardContentContainer @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null
) : FrameLayout(
    context,
    attributeSet
),
    NestedScrollingParent2 {

    var consumeNestedScroll: ConsumeNestedScroll? = null

    var onNestedScrollStop: OnNestedScrollStop? = null

    var onNestedScrollStart: OnNestedScrollStart? = null

    private var totalConsumedDy: Int = 0

    private var swipeDirectionHelper = SwipeDirectionHelper(threshold = 32f.toPxInt(context))

    private var isContentScrolling: Boolean = false

    private var didCardMove = false

    override fun onFinishInflate() {
        super.onFinishInflate()
        NestedScrollingParentHelper(this)
    }

    override fun onDetachedFromWindow() {
        onNestedScrollStop = null
        consumeNestedScroll = null
        super.onDetachedFromWindow()
    }

    override fun onNestedPreScroll(
        target: View,
        dx: Int,
        dy: Int,
        consumed: IntArray,
        type: Int
    ) {
        if (type != TYPE_TOUCH) {
            if (onNestedScrollStart?.invoke() == false) {
                consumed[1] = dy
            }
        } else if (dy != 0) {
            if (!isContentScrolling) {
                consumeNestedScroll?.let {
                    val consumedDy = it(target, -dy)
                    if (consumedDy == 0) {
                        isContentScrolling = true
                    } else {
                        didCardMove = true
                    }
                    if (didCardMove || totalConsumedDy > 0 || consumedDy != 0) {
                        consumed[1] = dy
                    }
                    if (consumedDy != 0) {
                        swipeDirectionHelper.onSwipe(consumedDy)
                    }
                    totalConsumedDy += consumedDy
                }
            }
        }
    }

    override fun onStopNestedScroll(target: View, type: Int) {
        if (didCardMove) {
            onNestedScrollStop?.invoke(swipeDirectionHelper.getDirection())
        }
        totalConsumedDy = 0
        swipeDirectionHelper.onReleaseSwipe()
        isContentScrolling = false
        didCardMove = false
    }

    override fun onStartNestedScroll(
        child: View,
        target: View,
        axes: Int,
        type: Int
    ): Boolean {
        return target != this &&
            (type == TYPE_TOUCH || onNestedScrollStart?.invoke() == true)
    }

    override fun onNestedScrollAccepted(
        child: View,
        target: View,
        axes: Int,
        type: Int
    ) {
    }

    override fun onNestedScroll(
        target: View,
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int,
        type: Int
    ) {
    }

    override fun onNestedFling(
        target: View,
        velocityX: Float,
        velocityY: Float,
        consumed: Boolean
    ): Boolean {
        return false
    }

    override fun onNestedPreFling(target: View, velocityX: Float, velocityY: Float): Boolean {
        return onNestedScrollStart?.invoke() == false
    }
}
