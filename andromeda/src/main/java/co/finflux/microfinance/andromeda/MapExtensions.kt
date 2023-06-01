package co.app.food.andromeda

internal fun Map<Float, Int>.getKeyOfValue(value: Int): Float {
    keys.forEach {
        if (getValue(it) == value) {
            return it
        }
    }
    return -1f
}
