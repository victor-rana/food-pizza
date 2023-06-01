package co.app.food.configs

import androidx.annotation.VisibleForTesting
import com.google.android.gms.tasks.Tasks
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class ConfigProviderImpl(
    private val firebaseRemoteConfig: FirebaseRemoteConfig
) : ConfigProvider {

    @VisibleForTesting
    var defaultConfigs: Map<String, String> = mapOf()

    override fun <T> get(key: String, defValue: T): T {
        val localDefault = defaultConfigs[key] ?: defValue.toString()
        return getConfigBasedOnType(key, defValue, localDefault)
    }

    @Suppress("UNCHECKED_CAST")
    private fun <T> getConfigBasedOnType(key: String, defValue: T, localDefault: String): T {
        return when (defValue) {
            is Boolean -> firebaseRemoteConfig.getBoolean(key) as T
            is String -> firebaseRemoteConfig.getString(key) as T
            is Long -> firebaseRemoteConfig.getLong(key) as T
            is Float, Double -> firebaseRemoteConfig.getDouble(key) as T
            is Int -> firebaseRemoteConfig.getLong(key) as T
            else -> defValue
        }
    }

    override fun <T> getFromRemoteAsync(key: String, defValue: T): Single<T> {
        return Single.fromCallable {
            firebaseRemoteConfig.getValue(key) as T
        }
            .subscribeOn(Schedulers.io())
    }

    override fun setDefaults(defaults: Map<String, String>) {
        defaultConfigs = defaults
    }

    override fun isEnabled(key: String, default: Boolean): Boolean {
        return firebaseRemoteConfig.getBoolean(key)
    }

    override fun syncAll(): Single<Map<String, Any>> {
        return getConfigsFromRemote()
    }

    private fun getConfigsFromRemote(): Single<Map<String, Any>> {
        return Single.fromCallable {
            val task = firebaseRemoteConfig.fetchAndActivate()
            Tasks.await(task)
            if (task.isSuccessful) {
                firebaseRemoteConfig.all
            } else
                throw Throwable("Fetch failed")
        }
            .map {
                it.mapValues { entry ->
                    entry.value as Any
                }
            }
            .subscribeOn(Schedulers.io())
    }
}
