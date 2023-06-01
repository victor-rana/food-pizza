package co.app.food.andromeda.exception

class ColorTokenException(private val source: String) : Exception() {

    override val message: String?
        get() = "Color Token was not provided for $source"
}
