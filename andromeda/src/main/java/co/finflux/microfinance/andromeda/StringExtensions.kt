package co.app.food.andromeda

internal fun String.ellipsize(maxLength: Int): String {
    return if (length <= maxLength) {
        this
    } else {
        return this.substring(0, maxLength) + "..."
    }
}
