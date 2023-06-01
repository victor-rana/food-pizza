package co.app.food.andromeda.card

enum class AndromedaCardState {

    /**
     * When a card is created, but not displayed card will be in UNKNOWN state.
     * Card goes to this state when some exception happen as well.
     */
    UNKNOWN,

    /**
     * Card's state changes to EXPANDING when [AndromedaCard.show] is called. Once the expand animation is completed, state will be changed to [EXPANDED] or  [COLLAPSED] or [SNAPPED] based on the type of the card and configuration
     */
    EXPANDING,

    /**
     * EXPANDED is a state applicable for all types of card. A card reaches this state when it is fully displayed and it cannot be moved upwards anymore
     * DialogCard and Fixed cards will be in EXPANDED state after calling [AndromedaCard.show] method
     */
    EXPANDED,

    /**
     * COLLAPSED state is applicable only to NonDismissibleNotchCards. When the card NonDismissibleNotchCards is moved to its lowest position state will be changed to COLLAPSED.
     * When [AndromedaCard.show] is called on NonDismissibleNotchCards, it will be moved to COLLAPSED state.
     */
    COLLAPSED,

    /**
     * Card transitions to this state when it starts animating to DISMISSED state. Card can be in state as a result of user action as well as [AndromedaCard.dismiss] api call
     */
    DISMISSING,

    /**
     * Card moves to this state when user swipes down(if applicable) or clicks on dismiss button.
     * Card will not be visible to user in this state. It is advised not to call [AndromedaCard.show] on a card in this state
     */
    DISMISSED,

    /**
     * Applicable only to NotchCards(both Dismissible and NonDismissible). When user swipes between EXPANDED and COLLAPSED(DISMISSED) state, cards might stick to a predefined point which isn't either EXPANDED or COLLAPSED(DISMISSED)
     */
    SNAPPED,

    /**
     * It is the state when user drags the card up or down. When user releases the touch card moves to any of the other state based on direction of drag and position
     * It is applicable only to NotchCards(both Dismissible and NonDismissible).
     */
    DRAGGING
}
