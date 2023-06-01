package co.app.food.andromeda.card.internal

import co.app.food.andromeda.card.AndromedaCardSnapPoints.NO_SNAP_POINT
import co.app.food.andromeda.card.CardConfig
import co.app.food.andromeda.card.DialogCardConfig
import co.app.food.andromeda.card.NotchCardConfig
import co.app.food.andromeda.getKeyOfValue
import java.util.*

internal class CardSnapStrategyHelper(
    private val config: CardConfig,
    private val callback: Callback
) {

    val absoluteSnapPoints = mutableListOf<Int>()

    val snapPointsMap = HashMap<Float, Int>()

    fun initSnapPoints() {
        if (config is NotchCardConfig &&
            absoluteSnapPoints.isEmpty()
        ) {
            if (config.isNonDismissibleNotchCard() &&
                config.snapPoints.isEmpty()
            ) {
                throw IllegalArgumentException(
                    "Snap points cannot be empty for Non-Dismissible Notch Cards."
                )
            }
            populateAbsoluteSnapPoints(config.snapPoints)
        }
    }

    fun useSnapPoints(snapPoints: Map<Float, Int>) {
        val cardBottom = callback.getDismissPosition()
        val height = cardBottom - callback.getMaxTopPosition()
        absoluteSnapPoints.clear()
        snapPointsMap.clear()
        snapPoints.values.forEach {
            if (it > height) {
                absoluteSnapPoints.add(it)
                snapPointsMap[snapPoints.getKeyOfValue(it)] = it
            }
        }
        absoluteSnapPoints.sort()
    }

    fun getSnapStrategy(
        direction: Direction,
        currentPosition: Int
    ): SnapStrategy {
        return when (config) {
            is DialogCardConfig -> getDialogCardStrategy(
                currentPosition,
                direction
            )
            is NotchCardConfig -> getNotchSnapCardStrategy(
                currentPosition,
                direction,
                config.snapPoints
            )
            else -> NoStrategy
        }
    }

    private fun getDialogCardStrategy(
        currentPosition: Int,
        direction: Direction
    ): SnapStrategy {
        return when (direction) {
            Direction.UPWARDS -> {
                val translation = callback.getMaxTopPosition()
                    .minus(currentPosition)
                SnapToPosition(translation)
            }
            Direction.DOWNWARD -> {
                DismissSnap
            }
        }
    }

    private fun getNotchSnapCardStrategy(
        currentPosition: Int,
        direction: Direction,
        snapPoints: List<Float>
    ): SnapStrategy {
        if (absoluteSnapPoints.isEmpty()) {
            populateAbsoluteSnapPoints(snapPoints)
        }

        var possibleSnapPoint = when (direction) {
            Direction.UPWARDS ->
                absoluteSnapPoints.lastOrNull { it <= currentPosition } ?: NO_SNAP_POINT
            Direction.DOWNWARD -> absoluteSnapPoints.firstOrNull { it >= currentPosition }
                ?: NO_SNAP_POINT
        }

        if (direction == Direction.DOWNWARD &&
            possibleSnapPoint == NO_SNAP_POINT &&
            config.isNonDismissibleNotchCard()
        ) {
            absoluteSnapPoints.maxOrNull()?.let {
                possibleSnapPoint = it
            }
        }

        return getSnapStrategyInternal(
            currentPosition,
            possibleSnapPoint,
            direction
        )
    }

    private fun getSnapStrategyInternal(
        currentPosition: Int,
        possibleSnapPoint: Int,
        direction: Direction
    ): SnapStrategy {
        return when {
            possibleSnapPoint != NO_SNAP_POINT -> {
                SnapToPosition(
                    possibleSnapPoint.minus(currentPosition)
                )
            }
            else -> {
                getDialogCardStrategy(
                    currentPosition,
                    direction
                )
            }
        }
    }

    private fun populateAbsoluteSnapPoints(snapPoints: List<Float>) {
        val cardBottom = callback.getDismissPosition()
        val height = cardBottom - callback.getMaxTopPosition()
        var absoluteSnapPoint: Int
        snapPoints.forEach {
            absoluteSnapPoint = cardBottom.minus(height.times(it)).toInt()
            absoluteSnapPoints.add(absoluteSnapPoint)
            snapPointsMap[it] = absoluteSnapPoint
        }

        absoluteSnapPoints.sort()
    }

    internal interface Callback {

        fun getMaxTopPosition(): Int

        fun getDismissPosition(): Int
    }
}
