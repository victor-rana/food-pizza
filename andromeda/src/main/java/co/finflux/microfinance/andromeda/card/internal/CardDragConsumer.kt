package co.app.food.andromeda.card.internal

import android.view.View
import co.app.food.andromeda.DragConsumeListener

internal class CardDragConsumer(
    private val cardLayout: View,
    private val maxTopPosition: Int,
    private val minTopPosition: Int,
    private val listener: DragConsumeListener
) {

    fun getConsumedDy(dy: Int): Int {
        val newTop = cardLayout.top.plus(dy)
        var consumedDy = dy
        when {
            maxTopPosition > newTop -> {
                consumedDy = maxTopPosition - cardLayout.top
            }
            minTopPosition < newTop -> {
                consumedDy = minTopPosition - cardLayout.top
            }
            else -> {
                listener(consumedDy)
            }
        }

        return consumedDy
    }

    fun getVerticalDragRange(): Int {
        return minTopPosition - maxTopPosition
    }
}
