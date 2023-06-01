package co.app.food.andromeda.card.internal

internal sealed class SnapStrategy

internal object NoStrategy : SnapStrategy()

internal object SnapHere : SnapStrategy()

internal object DismissSnap : SnapStrategy()

internal class SnapToPosition(val top: Int) : SnapStrategy()
