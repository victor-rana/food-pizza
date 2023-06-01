package co.app.food.common

import androidx.annotation.XmlRes
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface RemoteConfigDelegate : ReadConfig {
    fun lastFetchTimestamp(): Long
    fun fetchConfig(): Single<Result<Boolean>>
    fun fetchConfigFromRemote(): Single<Result<Boolean>>
    fun setDefaults(@XmlRes resourceId: Int): Single<Result<Boolean>>
    fun reset(): Completable
    fun flushCache()
    fun ensureInitialized(): Single<Result<Boolean>>
    fun isEnabled(toggle: String, default: Boolean = false): Boolean
}

const val DEFAULT_RESET_RETRY_COUNT = 2L
