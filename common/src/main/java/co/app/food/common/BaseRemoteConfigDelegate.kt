package co.app.food.common

import androidx.annotation.XmlRes
import co.app.food.configs.RemoteConfigStatsCollector
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber

private const val EVENT_FRC_INITIALIZED = "frc_initialized"
private const val EVENT_FRC_DEFAULTS = "frc_defaults"

abstract class BaseRemoteConfigDelegate(
    private val firebaseRemoteConfig: FirebaseRemoteConfig,
    private val remoteConfigStatsCollector: RemoteConfigStatsCollector
) : RemoteConfigDelegate {

    private var fetchRemoteConfigCache: Single<Result<Boolean>> =
        Single.just(Result.success(false)).cache()

    abstract fun fetchFromNetwork(): Single<Result<Boolean>>

    abstract fun shouldFetch(): Boolean

    override fun fetchConfig(): Single<Result<Boolean>> {
        if (shouldFetch()) {
            return fetchConfigFromRemote()
        }
        return fetchRemoteConfigCache
    }

    override fun fetchConfigFromRemote(): Single<Result<Boolean>> {
        fetchRemoteConfigCache = fetchFromNetwork().cache()
        return fetchRemoteConfigCache
    }

    override fun reset(): Completable {
        return Completable
            .create { emitter ->
                firebaseRemoteConfig
                    .reset()
                    .addOnSuccessListener {
                        if (emitter.isDisposed) return@addOnSuccessListener
                        emitter.onComplete()
                    }
                    .addOnFailureListener {
                        if (emitter.isDisposed) return@addOnFailureListener
                        emitter.onError(it)
                    }
            }
            .subscribeOn(Schedulers.io())
            .doOnError(Timber::e)
            .retry(DEFAULT_RESET_RETRY_COUNT)
    }

    override fun ensureInitialized(): Single<Result<Boolean>> {
        return Single.create<Result<Boolean>> { emitter ->
            firebaseRemoteConfig
                .ensureInitialized()
                .addOnSuccessListener {
                    if (!emitter.isDisposed) {
                        emitter.onSuccess(Result.success(true))
                    }
                }
                .addOnFailureListener {
                    if (!emitter.isDisposed) {
                        emitter.onError(it)
                    }
                }
        }
            .compose(remoteConfigStatsCollector.collectStats(EVENT_FRC_INITIALIZED, ""))
            .subscribeOn(Schedulers.io())
    }

    override fun setDefaults(@XmlRes resourceId: Int): Single<Result<Boolean>> {
        return Single.create<Result<Boolean>> { emitter ->
            firebaseRemoteConfig.setDefaultsAsync(resourceId)
                .addOnSuccessListener {
                    if (!emitter.isDisposed) {
                        emitter.onSuccess(Result.success(true))
                    }
                }.addOnFailureListener {
                    if (!emitter.isDisposed) {
                        emitter.onError(it)
                    }
                }
        }
            .compose(
                remoteConfigStatsCollector.collectStatsForDefaults(
                    EVENT_FRC_DEFAULTS,
                    resourceId
                )
            )
            .subscribeOn(Schedulers.io())
    }
}
