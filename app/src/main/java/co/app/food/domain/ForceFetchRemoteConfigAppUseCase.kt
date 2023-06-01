package co.app.food.domain

import android.content.SharedPreferences
import co.app.food.SOURCE_FRC_PACKAGED_DEFAULT
import co.app.food.SOURCE_FRC_REMOTE_SEGMENT
import co.app.food.common.RemoteConfigDelegate
import co.app.food.common.session.SessionManager
import io.reactivex.rxjava3.core.Completable

const val KEY_IS_REMOTE_CONFIG_RESET = "key_is_remote_config_reset"

class ForceFetchRemoteConfigAppUseCase(
    private val sessionManager: SessionManager,
    private val preferences: SharedPreferences,
    private val remoteConfigDelegate: RemoteConfigDelegate,
    private val remoteConfigAppUpdateResetUseCase: RemoteConfigAppUpdateResetUseCase,
    private val getRemoteConfigSourceUseCase: GetRemoteConfigSourceUseCase
) {
    fun invoke(): Completable {
        if (shouldFetchWithSegments()) {
            return remoteConfigAppUpdateResetUseCase
                .invoke()
                .onErrorComplete()
        }

        return remoteConfigDelegate
            .fetchConfig()
            .ignoreElement()
            .onErrorComplete()
    }

    private fun shouldFetchWithSegments() =
        sessionManager.isSessionActive() && shouldResetRemoteConfig()

    private fun shouldResetRemoteConfig(): Boolean {
        return !isRemoteSegmentSet() || !isRemoteConfigReset()
    }

    private fun isRemoteConfigReset() =
        preferences.getBoolean(KEY_IS_REMOTE_CONFIG_RESET, false)

    private fun isRemoteSegmentSet(): Boolean {
        val configSource = getRemoteConfigSourceUseCase.invoke()
        return configSource == SOURCE_FRC_REMOTE_SEGMENT ||
            configSource == SOURCE_FRC_PACKAGED_DEFAULT
    }
}
