package co.app.food.andromeda.card.internal

import android.view.View
import androidx.customview.widget.ViewDragHelper
import co.app.food.andromeda.ViewDragFinishListener
import co.app.food.andromeda.extensions.toPxInt

internal class ViewDragHelperCallback(
    private val cardLayout: View,
    private val consumer: CardDragConsumer,
    private val viewDragFinishListener: ViewDragFinishListener
) : ViewDragHelper.Callback() {

    private var state = State.IDLE

    private val swipeDirectionHelper = SwipeDirectionHelper(32f.toPxInt(cardLayout.context))

    override fun tryCaptureView(child: View, pointerId: Int): Boolean {
        return child == cardLayout
    }

    override fun clampViewPositionVertical(child: View, top: Int, dy: Int): Int {
        val consumedDy = consumer.getConsumedDy(dy)
        swipeDirectionHelper.onSwipe(consumedDy)
        if (state != State.SWIPING &&
            consumedDy != 0
        ) {
            state = State.SWIPING
        }

        return child.top.plus(consumedDy)
    }

    override fun clampViewPositionHorizontal(child: View, left: Int, dx: Int): Int {
        return child.left
    }

    override fun getViewVerticalDragRange(child: View): Int {
        return consumer.getVerticalDragRange()
    }

    override fun onViewReleased(releasedChild: View, xvel: Float, yvel: Float) {
        super.onViewReleased(releasedChild, xvel, yvel)
        viewDragFinishListener(
            swipeDirectionHelper.getDirection(),
            state,
            releasedChild.top
        )
        state = State.IDLE
        swipeDirectionHelper.onReleaseSwipe()
    }
}
