package co.app.food.andromeda.card.internal

import co.app.food.andromeda.card.AndromedaCardState
import co.app.food.andromeda.card.CardConfig
import co.app.food.andromeda.card.CardEventListener
import co.app.food.andromeda.card.NotchCardConfig
import co.app.food.andromeda.getKeyOfValue

internal class CardEventProcessor(
    private val config: CardConfig,
    private val positions: CardPositions,
    private val snapPoints: Map<Float, Int>
) {

    var state: AndromedaCardState = AndromedaCardState.EXPANDED
        private set

    fun onEvent(
        top: Int,
        eventListener: CardEventListener?,
        isUserAction: Boolean
    ) {

        when (top) {
            positions.maxTopPosition -> dispatchExpandedEvent(eventListener)
            positions.dismissPosition -> dispatchDismissedEvent(eventListener, isUserAction)
            positions.minTopPosition -> dispatchCollapsedEvent(eventListener, isUserAction)
            else -> dispatchSnapEvent(top, eventListener)
        }
    }

    fun onDragCard(
        dragOffset: Int,
        heightPercentage: Float,
        eventListener: CardEventListener?
    ) {
        if (config is NotchCardConfig) {
            state = AndromedaCardState.DRAGGING
            eventListener?.onCardDrag(dragOffset, heightPercentage)
        }
    }

    fun onDragEnd(direction: Direction, eventListener: CardEventListener?) {
        eventListener?.onCardDragEnd(direction == Direction.UPWARDS)
    }

    fun restoreState(previousState: AndromedaCardState) {
        state = previousState
    }

    fun cardShowing() {
        state = AndromedaCardState.EXPANDING
    }

    fun cardDismissing() {
        state = AndromedaCardState.DISMISSING
    }

    private fun dispatchExpandedEvent(
        eventListener: CardEventListener?
    ) {
        state = AndromedaCardState.EXPANDED
        if (config is NotchCardConfig) {
            eventListener?.onCardExpanded()
        }
    }

    private fun dispatchDismissedEvent(
        eventListener: CardEventListener?,
        isUserAction: Boolean
    ) {
        state = AndromedaCardState.DISMISSED
        eventListener?.onCardDismiss(isUserAction)
    }

    private fun dispatchCollapsedEvent(
        eventListener: CardEventListener?,
        isUserAction: Boolean
    ) {
        if (config.isNonDismissibleNotchCard()) {
            state = AndromedaCardState.COLLAPSED
            eventListener?.onCardCollapse(isUserAction)
        } else {
            state = AndromedaCardState.DISMISSED
            eventListener?.onCardDismiss(isUserAction)
        }
    }

    private fun dispatchSnapEvent(
        top: Int,
        eventListener: CardEventListener?
    ) {
        if (config is NotchCardConfig &&
            snapPoints.containsValue(top)
        ) {
            state = AndromedaCardState.SNAPPED
            eventListener?.onCardSnapToPoint(
                snapPoints.getKeyOfValue(top)
            )
        }
    }
}
