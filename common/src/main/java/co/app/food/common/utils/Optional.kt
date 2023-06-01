package co.app.food.common.utils

class Optional<T> private constructor(private val value: T) {

    fun isPresent() = value != EMPTY
    fun get(): T = value
    fun <R> ifPresent(exp: (T) -> R): R? = if (isPresent()) exp(get()) else null

    companion object {
        val EMPTY = Any()
        @JvmStatic
        fun <T : Any> of(value: T) = Optional(value)

        @Suppress("UNCHECKED_CAST")
        @JvmStatic
        fun <T> empty(): Optional<T> = Optional(EMPTY) as Optional<T>
    }
}
