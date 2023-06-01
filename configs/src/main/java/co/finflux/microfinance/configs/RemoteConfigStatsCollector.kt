package co.app.food.configs

import android.content.res.Resources
import android.content.res.Resources.NotFoundException
import android.os.Bundle
import androidx.annotation.XmlRes
import com.google.firebase.analytics.FirebaseAnalytics
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.core.SingleSource

const val PARAM_USER_ID = "user_id"
const val PARAM_FETCH_ID = "fetch_id"
const val PARAM_DURATION = "duration"
const val PARAM_ERROR_MESSAGE = "error_message"
const val PARAM_TIMESTAMP = "timestamp"
const val PARAM_VALUE_RES_NOT_FOUND = "n/a"

const val EVENT_SUCCESS = "%s_success"
const val EVENT_FAILURE = "%s_failure"
const val EVENT_INITIATED = "%s_initiate"
const val EVENT_CACHE_UPDATE_SUCCESS = "frc_cache_update_success"
const val EVENT_CACHE_CLEAR = "frc_cache_cleared"
const val EVENT_CACHE_DEFAULTS_SET = "frc_cache_defaults_set"
const val EVENT_CACHE_UPDATE_REJECT = "frc_cache_update_rejected"

class RemoteConfigStatsCollector(
    private val userIdProvider: UserIdProvider,
    private val firebaseAnalytics: FirebaseAnalytics,
    private val resources: Resources
) {

    inline fun <reified T> collectStats(
        name: String,
        fetchId: String
    ): (upstream: Single<T>) -> SingleSource<T> {
        return {
            var startTime = 0L
            it.doOnSubscribe {
                startTime = System.currentTimeMillis()
                trackSubscription(fetchId, name)
            }
                .doOnEvent { result, error ->
                    val duration = System.currentTimeMillis() - startTime
                    when {
                        result != null -> trackSuccess(fetchId, name, duration)
                        error != null -> trackError(fetchId, name, duration, error)
                    }
                }
        }
    }

    inline fun <reified T> collectStatsForDefaults(
        name: String,
        @XmlRes defaults: Int
    ): (upstream: Single<T>) -> SingleSource<T> {
        return {
            val defaultsName = getResourceName(defaults)
            var startTime = 0L

            it.doOnSubscribe {
                startTime = System.currentTimeMillis()
                trackSubscription(defaultsName, name)
            }
                .doOnEvent { result, error ->
                    val duration = System.currentTimeMillis() - startTime
                    when {
                        result != null -> trackSuccess(defaultsName, name, duration)
                        error != null -> trackError(defaultsName, name, duration, error)
                    }
                }
        }
    }

    @Suppress("SwallowedException")
    fun getResourceName(defaults: Int): String {
        return try {
            resources.getResourceName(defaults)
        } catch (exception: NotFoundException) {
            PARAM_VALUE_RES_NOT_FOUND
        }
    }

    fun trackError(fetchId: String, name: String, duration: Long, error: Throwable) {
        val attrs = Bundle().apply {
            putString(PARAM_USER_ID, userIdProvider.get())
            putString(PARAM_FETCH_ID, fetchId)
            putString(PARAM_ERROR_MESSAGE, error.message ?: "N/A")

            putLong(PARAM_DURATION, duration)
            putLong(PARAM_TIMESTAMP, System.currentTimeMillis())
        }
        firebaseAnalytics.logEvent(String.format(EVENT_FAILURE, name), attrs)
    }

    fun trackSuccess(fetchId: String, name: String, duration: Long) {
        val attrs = Bundle().apply {
            putString(PARAM_USER_ID, userIdProvider.get())
            putString(PARAM_FETCH_ID, fetchId)

            putLong(PARAM_DURATION, duration)
            putLong(PARAM_TIMESTAMP, System.currentTimeMillis())
        }
        firebaseAnalytics.logEvent(String.format(EVENT_SUCCESS, name), attrs)
    }

    fun trackSubscription(fetchId: String, name: String) {
        val attrs = Bundle().apply {
            putString(PARAM_USER_ID, userIdProvider.get())
            putString(PARAM_FETCH_ID, fetchId)
            putLong(PARAM_TIMESTAMP, System.currentTimeMillis())
        }
        firebaseAnalytics.logEvent(String.format(EVENT_INITIATED, name), attrs)
    }

    fun trackConfigCacheUpdateSuccess() {
        trackConfigCacheEvent(EVENT_CACHE_UPDATE_SUCCESS)
    }

    fun trackConfigCacheClear() {
        trackConfigCacheEvent(EVENT_CACHE_CLEAR)
    }

    fun trackConfigCacheDefaultsSet() {
        trackConfigCacheEvent(EVENT_CACHE_DEFAULTS_SET)
    }

    fun trackConfigCacheUpdateReject() {
        trackConfigCacheEvent(EVENT_CACHE_UPDATE_REJECT)
    }

    private fun trackConfigCacheEvent(eventName: String) {
        val attrs = Bundle().apply {
            putString(PARAM_USER_ID, userIdProvider.get())
            putLong(PARAM_TIMESTAMP, System.currentTimeMillis())
        }

        firebaseAnalytics.logEvent(eventName, attrs)
    }
}

interface UserIdProvider {
    fun get(): String?
}
