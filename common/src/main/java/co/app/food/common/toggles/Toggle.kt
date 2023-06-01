package co.app.food.common.toggles

data class Toggle(
    val name: String,
    val enabled: Boolean
) {
    fun setEnabled(value: Boolean): Toggle = copy(enabled = value)
}
