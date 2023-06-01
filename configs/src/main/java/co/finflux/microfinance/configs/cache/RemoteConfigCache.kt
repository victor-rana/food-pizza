package co.app.food.configs.cache

import android.content.Context
import com.google.firebase.crashlytics.FirebaseCrashlytics
import timber.log.Timber

interface RemoteConfigCache {
    fun save(configs: Map<String, Any>)
    fun getInteger(key: String, defaultValue: Int = 0): Int
    fun getFloat(key: String, defaultValue: Float = 0f): Float
    fun getDouble(key: String, defaultValue: Double = 0.0): Double
    fun getLong(key: String, defaultValue: Long = 0): Long
    fun getBoolean(key: String, defaultValue: Boolean = false): Boolean
    fun getString(key: String, defaultValue: String = ""): String
    fun flush()
}

private const val REMOTE_CONFIG_CACHE_NAME = "remote_configs_cache"

class RemoteConfigCacheImpl(
    val context: Context
) : RemoteConfigCache {

    private val configDao: RemoteConfigDao by lazy {
        val sharedPreferences = context.getSharedPreferences(
            REMOTE_CONFIG_CACHE_NAME,
            Context.MODE_PRIVATE
        )
        RemoteConfigDao(sharedPreferences)
    }

    override fun save(configs: Map<String, Any>) {
        val stringifiedConfigs = configs.mapValues { it.value.toString() }
        configDao.write(stringifiedConfigs)
    }

    override fun getInteger(key: String, defaultValue: Int): Int {
        return get(key, defaultValue)
    }

    override fun getFloat(key: String, defaultValue: Float): Float {
        return get(key, defaultValue)
    }

    override fun getDouble(key: String, defaultValue: Double): Double {
        return get(key, defaultValue)
    }

    override fun getLong(key: String, defaultValue: Long): Long {
        return get(key, defaultValue)
    }

    override fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        return get(key, defaultValue)
    }

    override fun getString(key: String, defaultValue: String): String {
        return get(key, defaultValue)
    }

    override fun flush() {
        configDao.clear()
        Timber.i("Remote Config Cache: Cache flushed")
    }

    @Suppress("UNCHECKED_CAST")
    private fun <T> get(key: String, defaultValue: T): T {
        val value = configDao.read(key) ?: return defaultValue

        Timber.i("Remote Config Cache: Value for $key found in cache, reading from it :$value")
        try {
            return when (defaultValue) {
                is Int -> value.toInt() as T
                is Float -> value.toFloat() as T
                is Long -> value.toLong() as T
                is Double -> value.toDouble() as T
                is Boolean -> value.toBoolean() as T
                is String -> value as T
                else -> defaultValue
            }
        } catch (e: NumberFormatException) {
            val crashlytics = FirebaseCrashlytics.getInstance()
            crashlytics.log("Remote config value for $key cannot be parsed to a number")
            crashlytics.recordException(e)

            return defaultValue
        }
    }
}
