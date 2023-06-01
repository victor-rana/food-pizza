package co.app.food.andromeda.card.internal

import co.app.food.andromeda.R
import co.app.food.andromeda.card.*
import co.app.food.andromeda.card.AndromedaCardSnapPoints.NO_SNAP_POINT
import java.lang.Math.max
import kotlin.math.abs

internal class CardPropertyProvider(
    callback: Callback,
    private val config: CardConfig,
    var cardSnapHelper: CardSnapStrategyHelper
) {

    val maxTopPosition by lazy(LazyThreadSafetyMode.NONE) {
        val paddingTop = callback.getCardTopMargin()
        val cardTop = callback.getCardTop()
        when (config) {
            is ResizableNotchCardConfig,
            is FixedCardConfig,
            is DialogCardConfig -> max(paddingTop, cardTop)
            is NotchCardConfig -> paddingTop
        }
    }

    val minTopPosition by lazy(LazyThreadSafetyMode.NONE) {
        var cardBottom = dismissPosition
        if (config.isNonDismissibleNotchCard()) {
            cardBottom -= peekHeight
        }
        cardBottom
    }

    val cardHeight by lazy(LazyThreadSafetyMode.NONE) {
        callback.getCardHeight()
    }

    val dismissPosition by lazy(LazyThreadSafetyMode.NONE) {
        when (config) {
            is DismissibleNotchCardConfig,
            is NonDismissibleNotchCardConfig -> {
                config.cardParentView.height - callback.getCardTopMargin()
            }
            else -> {
                maxTopPosition + cardHeight
            }
        }
    }

    private val peekHeight by lazy(LazyThreadSafetyMode.NONE) {
        dismissPosition - (cardSnapHelper.snapPointsMap.values.maxOrNull() ?: 0)
    }

    fun cardInitialTop(): Int {
        return when {
            config is NotchCardConfig &&
                config.startSnapPointIndex != NO_SNAP_POINT -> {
                cardSnapHelper.snapPointsMap.getValue(
                    config.snapPoints[config.startSnapPointIndex]
                )
            }
            config.isNonDismissibleNotchCard() -> minTopPosition
            else -> maxTopPosition
        }
    }

    fun showDismiss(): Boolean {
        return config is DialogCardConfig
    }

    fun canUserDrag(): Boolean {
        return config is NotchCardConfig
    }

    fun isModal(): Boolean {
        return config.isModal
    }

    fun getLayout() = when (config) {
        is DialogCardConfig,
        is FixedCardConfig -> R.layout.andromeda_dialog_card_layout
        is ResizableNotchCardConfig -> R.layout.andromeda_resizable_notch_card_layout
        is NotchCardConfig -> R.layout.andromeda_notch_card_layout
    }

    fun getAnimationDuration(
        from: Int,
        to: Int
    ): Long {
        return getAnimationDuration(from - to)
    }

    fun getAnimationDuration(
        translationAmount: Int
    ): Long {
        val speed = if (translationAmount > 0)
            config.animationConfig.upwards.speed
        else
            config.animationConfig.downwards.speed
        return abs(translationAmount)
            .times(speed / config.cardParentView.resources.displayMetrics.density).toLong()
    }

    internal interface Callback {

        fun getCardTop(): Int

        fun getCardHeight(): Int

        fun getCardTopMargin(): Int
    }
}
