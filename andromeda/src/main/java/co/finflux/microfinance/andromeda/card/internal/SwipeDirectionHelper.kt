package co.app.food.andromeda.card.internal

import kotlin.math.abs

internal class SwipeDirectionHelper(private val threshold: Int) {

    private lateinit var direction: Direction

    private var tempDirection = Direction.UPWARDS

    private var draggedDistance = 0

    fun onSwipe(dy: Int) {
        val swipeDirection = if (dy > 0) Direction.DOWNWARD else Direction.UPWARDS
        if (swipeDirection == tempDirection) {
            draggedDistance += dy
            if (abs(draggedDistance) >= threshold) {
                direction = swipeDirection
            }
        } else {
            draggedDistance = dy
        }
        tempDirection = swipeDirection
    }

    fun getDirection(): Direction {
        if (::direction.isInitialized.not()) {
            direction = if (tempDirection == Direction.UPWARDS) {
                Direction.DOWNWARD
            } else {
                Direction.UPWARDS
            }
        }
        return direction
    }

    fun onReleaseSwipe() {
        draggedDistance = 0
    }
}
