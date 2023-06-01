package co.app.food.andromeda

import android.view.View
import co.app.food.andromeda.card.internal.Direction
import co.app.food.andromeda.card.internal.State
import co.app.food.andromeda.components.ComponentData

typealias OnMenuItemClickListener = (Int) -> Unit
internal typealias ContextualActionListener = () -> Unit
typealias NavBackHandler = () -> Unit
typealias OnClearInput = () -> Unit
internal typealias DragConsumeListener = (Int) -> Unit
internal typealias ViewDragFinishListener = (Direction, State, Int) -> Unit
internal typealias ConsumeNestedScroll = (View, Int) -> Int
internal typealias OnNestedScrollStop = (Direction) -> Unit
internal typealias OnNestedScrollStart = () -> Boolean
typealias ViewComponentNotDrawnHandler = (ComponentData) -> Unit
typealias ValidateHandler = (String?) -> Unit
typealias ExpandHandler = (String) -> Unit
