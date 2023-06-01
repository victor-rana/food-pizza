package co.app.food.common

import android.content.Context
import androidx.annotation.XmlRes
import co.app.food.core.PreferenceHandler
import co.app.food.common.toggles.Experiment
import co.app.food.common.utils.Optional
import co.app.food.common.utils.Time
import co.app.food.common.utils.XmlUtils
import co.app.food.configs.ConfigProvider
import co.app.food.configs.RemoteConfigStatsCollector
import co.app.food.configs.SessionInfo
import co.app.food.configs.cache.RemoteConfigCache
import co.app.food.configs.cache.RemoteConfigCacheImpl
import co.app.food.configs.usecases.SetConfigCacheDefaultsUseCase
import co.app.food.configs.usecases.UpdateConfigCacheUseCase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.gson.Gson
import com.google.gson.JsonParseException
import com.google.gson.JsonParser
import com.google.gson.JsonSyntaxException
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber
import java.util.*
import java.util.concurrent.TimeUnit

class ConfigProviderDelegate(
    private val configProvider: ConfigProvider,
    firebaseRemoteConfig: FirebaseRemoteConfig,
    private val context: Context,
    private val xmlUtils: XmlUtils = XmlUtils(),
    private val preferenceHandler: PreferenceHandler,
    private val time: Time,
    private val gson: Gson,
    private val remoteConfigStatsCollector: RemoteConfigStatsCollector,
    private val sessionInfo: SessionInfo
) : BaseRemoteConfigDelegate(firebaseRemoteConfig, remoteConfigStatsCollector) {
    private var syncing: Boolean = false
    private val remoteConfigCache: RemoteConfigCache by lazy {
        RemoteConfigCacheImpl(context)
    }

    private val updateConfigCacheUseCase: UpdateConfigCacheUseCase by lazy {
        UpdateConfigCacheUseCase(
            sessionInfo,
            remoteConfigCache,
            remoteConfigStatsCollector
        )
    }

    private val setConfigCacheDefaultsUseCase: SetConfigCacheDefaultsUseCase by lazy {
        SetConfigCacheDefaultsUseCase(
            remoteConfigCache
        )
    }

    override fun fetchFromNetwork(): Single<Result<Boolean>> {
        return Single.create { emitter ->
            configProvider.syncAll()
                .doOnSubscribe {
                    syncing = true
                }.doOnEvent { _, _ ->
                    syncing = false
                }
                .subscribe({
                    if (!emitter.isDisposed) {
                        emitter.onSuccess(Result.success(true))
                    }
                    updateConfigCacheUseCase.invoke(it)
                    preferenceHandler.writeLong(KEY_CP_LAST_SYNC_TIME_MILLIS, time.now())
                }, {
                    if (!emitter.isDisposed) {
                        emitter.onError(it)
                    }
                    Timber.e(it)
                })
        }
    }

    override fun setDefaults(@XmlRes resourceId: Int): Single<Result<Boolean>> {
        return Single.fromCallable {
            val xmlDefaults = xmlUtils.getDefaultsFromXml(context, resourceId)
            configProvider.setDefaults(xmlDefaults)
            setConfigCacheDefaultsUseCase.invoke(xmlDefaults)
        }
            .subscribeOn(Schedulers.io())
            .flatMap { super.setDefaults(resourceId) }
    }

    override fun shouldFetch(): Boolean {
        return syncing.not() && getFetchDiffInMillis() >= getCacheExpiryInMillis()
    }

    override fun lastFetchTimestamp(): Long {
        return preferenceHandler.readLong(KEY_CP_LAST_SYNC_TIME_MILLIS)
    }

    override fun flushCache() {
        return remoteConfigCache.flush()
    }

    override fun isEnabled(toggle: String, default: Boolean): Boolean {
        return remoteConfigCache.getBoolean(toggle, default)
    }

    override fun readString(key: String, default: String): String {
        return remoteConfigCache.getString(key, default)
    }

    override fun <T> readValueForLocale(
        key: String,
        locale: Locale,
        clazz: Class<T>
    ): LocalizedValue<T> {
        return try {
            val configValue = remoteConfigCache.getString(key, "")

            val localizedJsonObject = JsonParser
                .parseString(configValue)
                .asJsonObject
                .getAsJsonObject(locale.toString())

            localizedJsonObject?.let { LocalizedValue.Available(gson.fromJson(it, clazz)) }
                ?: LocalizedValue.MissingLocaleKey(key, clazz).also {
                    logReadLocaleException(
                        key,
                        clazz,
                        "readLocalizeInstance Missing Locale exception"
                    )
                }
        } catch (jsonSyntaxException: JsonSyntaxException) {
            Timber.e(jsonSyntaxException)
            LocalizedValue.LocalizedJsonSyntaxException(key, clazz)
        }
    }

    override fun readLong(key: String, default: Long): Long {
        return remoteConfigCache.getLong(key, default)
    }

    override fun readInteger(key: String, default: Int): Int {
        return remoteConfigCache.getInteger(key, default)
    }

    override fun readBoolean(key: String, default: Boolean): Boolean {
        return remoteConfigCache.getBoolean(key, default)
    }

    override fun readFloat(key: String, default: Float): Float {
        return remoteConfigCache.getFloat(key, default)
    }

    override fun readDouble(key: String, default: Double): Double {
        return remoteConfigCache.getDouble(key, default)
    }

    override fun readExperiment(key: String): Experiment? {
        return try {
            val experiment: String = configProvider.get(key, "")
            return experiment.asType(Experiment::class.java)
        } catch (e: JsonParseException) {
            Timber.e(e, "Failed to parse litmus experiment for key : $key")
            null
        }
    }

    override fun readExperimentFromRemoteAsync(key: String): Single<Optional<Experiment>> {
        return configProvider.getFromRemoteAsync(key, "")
            .map { experiment ->
                try {
                    experiment.asType(Experiment::class.java)?.let { Optional.of(it) }
                        ?: Optional.empty()
                } catch (e: JsonParseException) {
                    Timber.e(e, "Failed to parse litmus experiment for key : $key")
                    Optional.empty()
                }
            }
    }

    private fun getCacheExpiryInMillis() = TimeUnit.MINUTES.toMillis(FETCH_INTERVAL_IN_MIN)

    private fun getFetchDiffInMillis() = time.now() - lastFetchTimestamp()

    private fun <T> logReadLocaleException(key: String, clazz: Class<T>, message: String) {
        val exception = RuntimeException("$message :: Key: $key :: Class:: $clazz")
        Timber.e(exception)
    }

    private fun <T> String?.asType(clazz: Class<T>): T? =
        gson.newBuilder().create().fromJson(this, clazz)

    companion object {
        const val FETCH_INTERVAL_IN_MIN: Long = 60
        const val KEY_CP_LAST_SYNC_TIME_MILLIS = "config_provider_last_sync_time_millis"
        const val PREF_CONFIG_PROVIDER = "pref_config_provider"
    }
}
