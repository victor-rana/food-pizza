package co.app.food.common

import co.app.food.common.toggles.Experiment
import co.app.food.common.utils.Optional
import io.reactivex.rxjava3.core.Single
import java.util.*

interface ReadConfig {
    fun readString(key: String, default: String = ""): String
    fun <T> readValueForLocale(
        key: String,
        locale: Locale,
        clazz: Class<T>
    ): LocalizedValue<T>

    fun readLong(key: String, default: Long = 0L): Long
    fun readInteger(key: String, default: Int = 0): Int
    fun readBoolean(key: String, default: Boolean = false): Boolean
    fun readFloat(key: String, default: Float = 0F): Float
    fun readDouble(key: String, default: Double = 0.0): Double
    fun readExperiment(key: String): Experiment? {
        return null
    }

    fun readExperimentFromRemoteAsync(key: String): Single<Optional<Experiment>> {
        return Single.just(Optional.empty())
    }
}
