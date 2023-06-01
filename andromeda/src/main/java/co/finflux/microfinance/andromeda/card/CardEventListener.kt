package co.app.food.andromeda.card

/**
 * [AndromedaCard] events listener interface. Not all events are associated with every type of card
 *
 */
interface CardEventListener {

    fun onCardDismiss(isUserAction: Boolean) {}

    fun onCardExpanded() {}

    fun onCardCollapse(isUserAction: Boolean) {}

    /**
     * Invoked when card is dragged by user in either direction
     * @param dragOffset indicates the amount dragged by user after last event.
     * Positive values indicate dragging downwards
     * Negative values indicate dragging upwards
     *
     * @param heightPercentage indicates the current height in percentage. Value ranges from 1.0 to 0.0
     * 1.0 indicates the expanded state and 0.0 indicates dismissed state
     */
    fun onCardDrag(dragOffset: Int, heightPercentage: Float) {}

    fun onCardSnapToPoint(snapPoint: Float) {}

    fun onCardDragEnd(isGoingUp: Boolean) {}
}
