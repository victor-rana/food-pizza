package co.app.food.andromeda.card

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.accessibility.AccessibilityEvent
import android.widget.ImageView
import androidx.core.view.ViewCompat
import androidx.core.view.doOnLayout
import androidx.customview.widget.ViewDragHelper
import co.app.food.andromeda.*
import co.app.food.andromeda.button.AndromedaCircularButton
import co.app.food.andromeda.card.AndromedaCardSnapPoints.NO_SNAP_POINT
import co.app.food.andromeda.card.internal.*
import co.app.food.andromeda.card.internal.CardRootViewGroup.Companion.EXPAND_DIRECTION_DOWN
import co.app.food.andromeda.card.internal.CardRootViewGroup.Companion.EXPAND_DIRECTION_UP
import co.app.food.andromeda.extensions.isInTouchArea
import co.app.food.andromeda.extensions.makeInvisible
import co.app.food.andromeda.extensions.makeVisible
import co.app.food.andromeda.extensions.toPxFloat
import kotlin.math.max

class AndromedaCard(private val config: CardConfig) :
    CardSnapStrategyHelper.Callback,
    CardPropertyProvider.Callback {

    private var viewDragHelper: ViewDragHelper? = null

    private var cardSnapHelper = CardSnapStrategyHelper(
        config,
        this
    )

    private var cardSnapAnimator: CardSnapAnimator

    private var cardPropertyProvider = CardPropertyProvider(
        this,
        config,
        cardSnapHelper
    )

    private val cardLayout: View = inflateLayout()

    private val cardContainerRoot = cardLayout.findViewById<View>(R.id.card_content_container_root)

    private var cardDragConsumer: CardDragConsumer? = null

    private var cardEventProcessor: CardEventProcessor? = null

    private var cardTop = -1

    private var contentViewHeight = 0

    private var layoutListener: View.OnLayoutChangeListener? = null

    private var systemWindowsLayoutListener: View.OnLayoutChangeListener? = null

    private var scrimAnimator: AndromedaCardScrimColorAnimator? = null

    private var dismissButtonAnimator: DialogCardDismissButtonAnimator? = null

    /**
     * This field is used for accessibility purposes. While TalkBack is on,
     * the card can be moved up and down by double tapping notch. This field decides the direction
     * the card should move.
     */
    private var snapDirection = Direction.UPWARDS

    var cardEventListener: CardEventListener? = null

    /**
     * a field to decide animation should be performed when cards content height changed.
     * If the content change already has some animation or default animation doesn't look good, disable it by setting false
     */
    var animateHeightChange = true

    init {
        cardSnapAnimator = CardSnapAnimator(
            cardContainerRoot,
            config.animationConfig
        )
        bindCardProperties()
    }

    fun show(showListener: () -> Unit = {}) {
        config.cardParentView.addView(
            cardLayout
        )
        val parentLayoutParams = config.cardParentView.layoutParams
        var parentHeight = parentLayoutParams.height
        // Constraint Layout with height set to match_constraint(0dp) will return parentLayoutParams.height as 0
        // config.cardParentView.measuredHeight will have actual height value only after measure pass is done. It is async.
        // So always prefer layoutParams.height, have view.measuredHeight as fallback for the ConstraintLayout case
        if (parentHeight == 0) {
            parentHeight = config.cardParentView.measuredHeight
        }
        var parentWidth = parentLayoutParams.width
        // Similar to the height handling mentioned above
        if (parentWidth == 0) {
            parentWidth = config.cardParentView.measuredWidth
        }
        cardLayout.layoutParams.apply {
            width = parentWidth
            height = parentHeight
        }
        addContent()
        showCard(showListener)
    }

    fun dismiss(
        dismissListener: () -> Unit = {}
    ) {
        dismiss(dismissListener, false)
    }

    /**
     * @param height The new height to which the card to be moved
     *
     * @param animate A boolean indicating whether the card should be moved to the new height with animation or not
     * true indicates card will be animated according to the animation card has internally. false will move the card immediately to new position
     * setting it false also will help you to custom animation defined by you to change height.
     * vale of this field is true by default
     *
     * @param onHeightChanged a lambda to perform any action when the card height change is completed.
     * This is particularly useful when you choose to have built-in animation for height change. See animate param for more info
     */
    fun setHeight(
        height: Int,
        animate: Boolean = true,
        onHeightChanged: () -> Unit = {}
    ) {
        // card height is calculated from the bottom. dismissPosition indicate the position at which the card is not visible at all
        // ie, card top of the card is aligned with the bottom of the parent containing it.
        // subtracting height from this give the position of card's top according to new height value passed
        var newPosition = cardPropertyProvider.dismissPosition - height
        // In case of dialog card, above calculated position will be including the close button height which is outside the card
        // since products want t show the content of specified height, space from top of the card to top of the close button has to be removed.
        if (cardPropertyProvider.showDismiss()) {
            val extra = getCardHeight() - cardLayout.findViewById<CardContentContainer>(
                R.id.card_content_container
            ).height
            newPosition -= extra
        }
        val duration = if (animate) {
            cardPropertyProvider.getAnimationDuration(
                from = cardTop,
                to = newPosition
            )
        } else {
            0
        }
        cardSnapAnimator.snapToPosition(
            translateFrom = cardTop,
            translateTo = newPosition,
            duration = duration,
            doOnEnd = {
                cardTop = newPosition
                onHeightChanged()
                changeExpandDirection()
                processCardEvent(false)
            }
        )
    }

    /**
     * Collapse acts similar to [dismiss] for every type of cards except Non-dismissible notch card
     * For Non-dismissible notch card this method will move the card to peekHeight position
     */
    fun collapse(
        collapseListener: () -> Unit = {}
    ) {
        val cardContainerRoot = cardLayout.findViewById<View>(R.id.card_content_container_root)
        moveCard(
            from = cardContainerRoot.top,
            to = cardPropertyProvider.minTopPosition,
            doOnEnd = {
                processCardEvent(false)
                collapseListener()
                if (cardPropertyProvider.minTopPosition == cardPropertyProvider.dismissPosition) {
                    cardLayout.findViewById<CardContentContainer>(R.id.card_content_container)
                        .removeAllViews()
                    config.cardParentView.removeView(cardLayout)
                } else {
                    changeNotchClickActionInfo(cardPropertyProvider.minTopPosition)
                }
                changeExpandDirection()
                changeSnapDirection()
            }
        )
    }

    fun getState(): AndromedaCardState {
        return cardEventProcessor?.state ?: AndromedaCardState.UNKNOWN
    }

    /**
     * Updates the snap points. It will NOT add new snap points to existing ones.
     * @param snapPoints will replace the existing snap points
     * @param startSnapPointIndex is the point to which the card should move after updating snap points
     * If no value is passed, card will move to the lowest value of snap point in the snap points list
     */
    fun setSnapPoints(
        snapPoints: List<Float>,
        startSnapPointIndex: Int = NO_SNAP_POINT
    ) {
        if (config is NotchCardConfig) {
            config.apply {
                this.snapPoints = snapPoints
                this.startSnapPointIndex = startSnapPointIndex
            }
            updateComponents(false)
            moveCard(
                from = cardTop,
                to = cardPropertyProvider.cardInitialTop(),
                doOnEnd = {
                    processCardEvent(false)
                }
            )
        }
    }

    /**
     * @return the height of card at that time. Height depends on the state of the card(EXPANDED, COLLAPSED, SNAPPED etc)
     */
    fun getHeight(): Int {
        return cardLayout.height - cardContainerRoot.top - getCardTopMargin()
    }

    /**
     * Makes the card to be within the window bounds. Useful for activities with [View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN] flag to show the card above the keyboard
     */
    fun setFitsSystemWindows() {
        cardLayout.fitsSystemWindows = true
        systemWindowsLayoutListener = View.OnLayoutChangeListener { _, _, top, _, _, _, _, _, _ ->
            cardLayout.removeOnLayoutChangeListener(layoutListener)
            layoutListener = null
            cardTop = top
            updateComponents(false)
        }
        cardContainerRoot.addOnLayoutChangeListener(systemWindowsLayoutListener)
    }

    /**
     * expands the card from current postion to the fully visible(top most) position
     * @param expandListener function to be invoked once the animation is complete
     */
    fun expand(
        expandListener: () -> Unit = {}
    ) {
        moveCard(
            from = cardTop,
            to = cardPropertyProvider.maxTopPosition,
            doOnEnd = {
                processCardEvent(false)
                expandListener()
                changeExpandDirection()
            }
        )
    }

    /**
     * @return whether the card is visible or not. It return true when card is in [AndromedaCardState.EXPANDING] (not fully visible, animating to become visible)
     * and in [AndromedaCardState.DISMISSING] (card is animating to become fully invisible/Dismissed)
     */
    fun isShowing() = getState() != AndromedaCardState.UNKNOWN &&
        getState() != AndromedaCardState.DISMISSED

    /** @suppress */
    override fun getMaxTopPosition(): Int {
        return cardPropertyProvider.maxTopPosition
    }

    /** @suppress */
    override fun getDismissPosition(): Int {
        return cardPropertyProvider.dismissPosition
    }

    /** @suppress */
    override fun getCardTop(): Int {
        return cardLayout.bottom - cardContainerRoot.height
    }

    /**
     * @return height of the card in pixels.
     */
    override fun getCardHeight(): Int {
        return cardContainerRoot.height
    }

    /** @suppress */
    override fun getCardTopMargin(): Int {
        return cardLayout.findViewById<CardRootViewGroup>(R.id.card_root).paddingTop
    }

    private fun inflateLayout(): View {
        return LayoutInflater.from(config.contentView.context)
            .inflate(cardPropertyProvider.getLayout(), null)
    }

    private fun bindCardProperties() {

        if (cardPropertyProvider.showDismiss()) {
            cardLayout.findViewById<AndromedaCircularButton>(R.id.dialog_card_dismiss).makeVisible()
        }

        val cornerRadius = 16f.toPxFloat(cardLayout.context)
        cardLayout.findViewById<CardContentContainer>(R.id.card_content_container).background =
            generateRoundedRectangleDrawable(
                cardLayout.context,
                R.attr.fill_background_primary,
                floatArrayOf(
                    cornerRadius,
                    cornerRadius,
                    0f,
                    0f
                )
            )

        if (cardPropertyProvider.isModal()) {
            cardLayout.findViewById<CardRootViewGroup>(R.id.card_root).apply {
                scrimAnimator = AndromedaCardScrimColorAnimator(background.mutate())
                isClickable = true
                isFocusable = true
            }
        } else {
            cardLayout.findViewById<CardRootViewGroup>(R.id.card_root).background = null
        }
    }

    private fun setAccessibilityOptions() {
        cardContainerRoot.removeClickActionInfo()
        cardLayout.findViewById<CardContentContainer>(R.id.card_content_container)
            .removeClickActionInfo()

        if (config.isNotchCard()) {
            changeSnapDirection()

            if (isExpanded()) {
                changeNotchClickActionInfo(cardPropertyProvider.maxTopPosition)
            }

            if (isCollapsed()) {
                changeNotchClickActionInfo(cardPropertyProvider.minTopPosition)
            }

            if (cardLayout.context.isTalkBackOn()) {
                cardLayout.findViewById<ImageView>(R.id.notch).setOnClickListener {
                    snapToNextPoint(
                        snapDirection,
                        cardContainerRoot.top
                    )
                }
            }
        }
    }

    /**
     * Facilitates bottom to top and top to bottom card movement. The next point will be determined
     * by [snapDirection].
     * When TalkBack is on, the card movement can be performed by double tapping the notch.
     * When the card is in collapsed state, the [snapDirection] will be [Direction.UPWARDS] and double tapping on
     * notch moves the card to one point up. The card moves upwards until it expands fully. Then
     * the [snapDirection] will be changed to [Direction.DOWNWARD]. Double tapping on notch moves the card one point
     * down until it reaches collapsed state for non-dismissible notch card, or dismisses the card in case of
     * dismissible notch card.
     */
    private fun snapToNextPoint(
        direction: Direction,
        viewTop: Int
    ) {
        cardEventProcessor?.onDragEnd(direction, cardEventListener)

        var nextSnapPoint = cardSnapHelper.absoluteSnapPoints.find {
            if (direction == Direction.UPWARDS) {
                it < viewTop
            } else {
                it > viewTop
            }
        }

        /**
         * For dismissible notch card if there is no downward snap point, dismiss the card
         */
        if (nextSnapPoint == null &&
            direction == Direction.DOWNWARD &&
            !config.isNonDismissibleNotchCard()
        ) {
            dismiss()
            return
        }

        /**
         * If there is no upward snap point, expand the card
         */
        if (nextSnapPoint == null && direction == Direction.UPWARDS) {
            nextSnapPoint = cardPropertyProvider.maxTopPosition
        }

        if (nextSnapPoint != null) {
            moveCard(
                from = viewTop,
                to = nextSnapPoint,
                doOnEnd = {
                    processCardEvent(true)
                    changeExpandDirection()
                    changeSnapDirection()
                    changeNotchClickActionInfo(nextSnapPoint)
                }
            )
        }
    }

    /**
     * Determines card expansion percentage based on absolute snap point
     */
    private fun cardExpandPercentage(absoluteSnapPoint: Int): Int {
        val snapPoint = cardSnapHelper.snapPointsMap.getKeyOfValue(absoluteSnapPoint)

        return when {
            absoluteSnapPoint == cardPropertyProvider.maxTopPosition -> 100
            snapPoint != -1f -> (snapPoint * 100).toInt()
            else -> 0
        }
    }

    private fun changeNotchClickActionInfo(absoluteSnapPoint: Int) {
        val cardControl = cardLayout.context.resources.getString(R.string.accessibilityCardControl)
        val expandedPercentage = cardExpandPercentage(absoluteSnapPoint)
        cardLayout.findViewById<ImageView>(R.id.notch).contentDescription =
            "$cardControl $expandedPercentage%"

        cardLayout.findViewById<ImageView>(R.id.notch).changeClickActionInfo(
            if (snapDirection == Direction.UPWARDS) {
                cardLayout.context.resources.getString(R.string.accessibilityExpand)
            } else {
                cardLayout.context.resources.getString(R.string.accessibilityMinimize)
            }
        )
    }

    /**
     * Changes [snapDirection] based on card state.
     * The [snapDirection] changes to [Direction.DOWNWARD] if card is fully expanded
     * and [snapDirection] changes to [Direction.UPWARDS] if card is collapsed.
     */
    private fun changeSnapDirection() {
        if (isExpanded()) {
            snapDirection = Direction.DOWNWARD
        }

        if (isCollapsed()) {
            snapDirection = Direction.UPWARDS
        }
    }

    private fun shiftAccessibilityFocus() {
        cardLayout.findViewById<CardContentContainer>(R.id.card_content_container)
            .sendAccessibilityEvent(AccessibilityEvent.TYPE_VIEW_FOCUSED)
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initCardInteractions() {
        if (cardPropertyProvider.canUserDrag()) {
            initDragComponents()

            cardLayout.findViewById<CardRootViewGroup>(R.id.card_root)
                .setOnTouchListener { _, event ->
                    viewDragHelper?.processTouchEvent(event)
                    cardContainerRoot.isInTouchArea(event)
                }

            cardLayout.findViewById<CardContentContainer>(R.id.card_content_container).apply {
                consumeNestedScroll = ::onScroll
                onNestedScrollStop = ::onScrollStop
                onNestedScrollStart = ::onScrollStart
            }
        }

        if (cardPropertyProvider.showDismiss()) {
            cardLayout.findViewById<AndromedaCircularButton>(R.id.dialog_card_dismiss)
                .setOnClickListener {
                    dismissDialogCard()
                }
        }

        if (cardPropertyProvider.isModal() &&
            config !is FixedCardConfig
        ) {
            cardLayout.findViewById<CardRootViewGroup>(R.id.card_root).setOnClickListener {
                dismiss(isUserAction = true)
            }
        }
    }

    private fun addContent() {
        cardContainerRoot.makeInvisible()
        cardLayout.findViewById<CardContentContainer>(R.id.card_content_container).removeAllViews()
        cardLayout.findViewById<CardContentContainer>(R.id.card_content_container).addView(
            config.contentView
        )
    }

    private fun showCard(doOnEnd: () -> Unit) {
        cardLayout.doOnLayout {
            cardSnapHelper.initSnapPoints()
            initEventProcessing()
            cardEventProcessor?.cardShowing()
            if (cardPropertyProvider.showDismiss()) {
                dismissButtonAnimator = DialogCardDismissButtonAnimator()
                dismissButtonAnimator?.animateSlideUp(
                    cardLayout.findViewById<AndromedaCircularButton>(
                        R.id.dialog_card_dismiss
                    )
                )
            }
            cardTop = cardPropertyProvider.cardInitialTop()
            moveCard(
                from = cardPropertyProvider.dismissPosition,
                to = cardTop,
                doOnStart = {
                    cardContainerRoot.makeVisible()
                    observeLayoutChanges()
                },
                doOnEnd = {
                    initCardInteractions()
                    processCardEvent(false)
                    doOnEnd()
                    changeExpandDirection()
                    setAccessibilityOptions()
                    if (config.isModal) {
                        shiftAccessibilityFocus()
                    }
                }
            )
            scrimAnimator?.fadeIn()
        }
    }

    private fun onViewDragFinish(
        direction: Direction,
        state: State,
        viewTop: Int
    ) {
        if (state != State.IDLE) {
            snap(
                direction,
                viewTop
            )
        }
    }

    private fun onScroll(target: View, dy: Int): Int {
        // If scrolling down, let the content scroll if card is in expanded state,
        // consume the scroll for card movement if card is not expanded or content is not scrollable downwards anymore
        // While swiping up always try to expand the card, if card doesn't consume it, scroll the card content
        val consumedDy = if (dy > 0 &&
            isExpanded() &&
            target.canScrollVertically(-dy)
        ) {
            0
        } else {
            cardDragConsumer?.getConsumedDy(dy) ?: 0
        }

        ViewCompat.offsetTopAndBottom(
            cardContainerRoot,
            consumedDy
        )

        return consumedDy
    }

    private fun onScrollStop(direction: Direction) {
        snap(
            direction,
            cardContainerRoot.top
        )
    }

    private fun onScrollStart(): Boolean {
        return getState() == AndromedaCardState.EXPANDED
    }

    private fun snap(
        direction: Direction,
        viewTop: Int
    ) {
        cardEventProcessor?.onDragEnd(direction, cardEventListener)
        val snapStrategy = cardSnapHelper.getSnapStrategy(
            direction,
            viewTop
        )

        when (snapStrategy) {
            DismissSnap -> dismiss(isUserAction = true)
            is SnapToPosition -> {
                cardTop = viewTop + snapStrategy.top
                moveCard(
                    from = viewTop,
                    to = cardTop,
                    doOnEnd = {
                        processCardEvent(true)
                        changeExpandDirection()
                        changeSnapDirection()
                        changeNotchClickActionInfo(cardTop)
                    }
                )
            }
        }
    }

    private fun moveCard(
        from: Int,
        to: Int,
        doOnStart: () -> Unit = {},
        doOnEnd: () -> Unit = {}
    ) {
        cardSnapAnimator.snapToPosition(
            from,
            to,
            cardPropertyProvider.getAnimationDuration(
                from,
                to
            ),
            doOnStart,
            doOnEnd
        )
    }

    private fun processCardEvent(isUserAction: Boolean) {
        cardEventProcessor?.onEvent(
            cardContainerRoot.top,
            cardEventListener,
            isUserAction
        )
    }

    /**
     * @return true indicates that there has been a change in layout hierarchy and bottom of card container changed
     * false indicate layout pass triggered by the content update inside card
     */
    private fun isLayoutChanged(bottom: Int, oldBottom: Int): Boolean {
        return bottom != oldBottom
    }

    private fun updateComponents(retainSnapPoints: Boolean) {
        val snapPoints = cardSnapHelper.snapPointsMap
        cardPropertyProvider = CardPropertyProvider(
            callback = this,
            config = config,
            cardSnapHelper = cardSnapHelper
        )
        cardSnapHelper = CardSnapStrategyHelper(
            config = config,
            callback = this
        )
        if (retainSnapPoints) {
            cardSnapHelper.useSnapPoints(snapPoints)
        } else {
            cardSnapHelper.initSnapPoints()
            cardPropertyProvider.cardSnapHelper = cardSnapHelper
        }
        if (cardPropertyProvider.canUserDrag()) {
            initDragComponents()
        }
        initEventProcessing()
    }

    private fun initDragComponents() {
        val consumer = CardDragConsumer(
            cardContainerRoot,
            cardPropertyProvider.maxTopPosition,
            cardPropertyProvider.minTopPosition
        ) {
            val height = cardPropertyProvider.cardHeight.toFloat()
            val marginTop = getCardTopMargin()
            cardEventProcessor?.onDragCard(
                it,
                (height - cardContainerRoot.top + marginTop) / height,
                cardEventListener
            )
        }.also {
            cardDragConsumer = it
        }

        viewDragHelper = ViewDragHelper.create(
            cardLayout.findViewById<CardRootViewGroup>(R.id.card_root),
            1.0f,
            ViewDragHelperCallback(
                cardContainerRoot,
                consumer,
                ::onViewDragFinish
            )
        )
    }

    private fun initEventProcessing() {
        cardEventProcessor = CardEventProcessor(
            config,
            CardPositions(
                cardPropertyProvider.maxTopPosition,
                cardPropertyProvider.dismissPosition,
                cardPropertyProvider.minTopPosition
            ),
            cardSnapHelper.snapPointsMap
        )
    }

    private fun observeLayoutChanges() {
        contentViewHeight = config.contentView.height
        layoutListener = View.OnLayoutChangeListener { _, _, _, _, bottom, _, _, _, oldBottom ->
            reactToLayoutChange(bottom, oldBottom)
        }
        cardLayout.addOnLayoutChangeListener(layoutListener)
    }

    private fun reactToLayoutChange(bottom: Int, oldBottom: Int) {
        // This will keep the card in previous position after a layout pass if the whole layout hasn't changed
        if (isLayoutChanged(bottom, oldBottom).not()) {
            // If the layout pass is triggered by content height change, then we need to adjust height smoothly
            if (config.contentView.height != contentViewHeight) {
                if (config !is NotchCardConfig) {
                    updateCardHeight()
                } else if (config is ResizableNotchCardConfig) {
                    if (isExpanded()) {
                        updateCardHeight()
                    } else {
                        updateComponents(true)
                    }
                }
                contentViewHeight = config.contentView.height
            }
        } else {
            // There has been change in card bounds, need to re-init components to dragging to work
            if (config.isResizableCard()) {
                updateComponents(false)
            }
            cardTop = cardContainerRoot.top
        }
    }

    private fun updateCardHeight() {
        val newTop = max(
            getCardTopMargin(),
            cardTop + (contentViewHeight - config.contentView.height)
        )
        val duration = if (animateHeightChange) {
            cardPropertyProvider.getAnimationDuration(cardTop, newTop)
        } else {
            0
        }
        cardSnapAnimator.snapToPosition(
            translateFrom = cardTop,
            translateTo = newTop,
            duration = duration,
            doOnEnd = {
                cardTop = newTop
                updateComponents(config is ResizableNotchCardConfig)
            }
        )
    }

    private fun isExpanded(): Boolean {
        return getState() == AndromedaCardState.EXPANDED ||
            cardTop == getMaxTopPosition()
    }

    private fun isCollapsed(): Boolean {
        return getState() == AndromedaCardState.COLLAPSED
    }

    private fun dismiss(
        dismissListener: () -> Unit = {},
        isUserAction: Boolean
    ) {
        cardEventProcessor?.cardDismissing()
        moveCard(
            from = cardContainerRoot.top,
            to = cardPropertyProvider.dismissPosition,
            doOnEnd = {
                processCardEvent(isUserAction)
                cardLayout.findViewById<CardContentContainer>(R.id.card_content_container)
                    .removeAllViews()
                config.cardParentView.removeView(cardLayout)
                dismissListener()
                cardLayout.removeOnLayoutChangeListener(layoutListener)
                layoutListener = null
                cardContainerRoot.removeOnLayoutChangeListener(systemWindowsLayoutListener)
                systemWindowsLayoutListener = null
            }
        )
        scrimAnimator?.fadeOut()
    }

    private fun changeExpandDirection() {
        if (config is ResizableNotchCardConfig) {
            val cardContentContainer = cardLayout.findViewById<CardRootViewGroup>(R.id.card_root)
            cardContentContainer.expandDirection = if (getState() == AndromedaCardState.EXPANDED) {
                EXPAND_DIRECTION_UP
            } else {
                EXPAND_DIRECTION_DOWN
            }
        }
    }

    private fun dismissDialogCard() {
        cardLayout.postDelayed(
            { dismiss(isUserAction = true) }, 50
        )
        dismissButtonAnimator?.animateSlideDown(
            cardLayout.findViewById<AndromedaCircularButton>(R.id.dialog_card_dismiss)
        )
    }
}
