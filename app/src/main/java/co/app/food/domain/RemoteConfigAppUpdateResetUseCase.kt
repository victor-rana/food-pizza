package co.app.food.domain

import android.content.SharedPreferences
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Completable.fromCallable

class RemoteConfigAppUpdateResetUseCase(
    private val preferences: SharedPreferences,
    private val fetchRemoteConfigSegmentUseCase: FetchRemoteConfigSegmentUseCase,
    private val resetRemoteConfigUseCase: ResetRemoteConfigUseCase,
    private val remoteConfigDefaultsUseCase: SetRemoteConfigDefaultsUseCase
) {
    fun invoke(): Completable {
        return Completable
            .concatArray(
                fromCallable { resetRemoteConfigUseCase.invoke().blockingSubscribe() },
                remoteConfigDefaultsUseCase.invoke().ignoreElement(),
                fetchRemoteConfigSegmentUseCase.invoke().ignoreElement()
            )
            .doOnComplete { setRemoteConfigIsReset() }
            .onErrorComplete()
    }

    fun setRemoteConfigIsReset() {
        preferences.edit().putBoolean(KEY_IS_REMOTE_CONFIG_RESET, true).apply()
    }
}
