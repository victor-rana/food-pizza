package co.app.food.common.toggles.rescue

sealed class ToggleState {
    data class Available(val value: Boolean) : ToggleState()
    object UseDefault : ToggleState()
}
