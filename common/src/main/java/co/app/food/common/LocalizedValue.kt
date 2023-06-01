package co.app.food.common

sealed class LocalizedValue<T> {
    data class Available<T>(val value: T) : LocalizedValue<T>()
    data class MissingFirebaseKey<T>(
        val key: String,
        val type: Class<T>
    ) : LocalizedValue<T>()

    data class MissingLocaleKey<T>(
        val key: String,
        val type: Class<T>
    ) : LocalizedValue<T>()

    data class LocalizedJsonSyntaxException<T>(
        val key: String,
        val type: Class<T>
    ) : LocalizedValue<T>()
}
