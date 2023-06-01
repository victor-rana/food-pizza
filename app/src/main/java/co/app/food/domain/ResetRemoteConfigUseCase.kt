package co.app.food.domain

import co.app.food.common.RemoteConfigDelegate
import io.reactivex.rxjava3.core.Completable

class ResetRemoteConfigUseCase(private val remoteConfigDelegate: RemoteConfigDelegate) {

    fun invoke(): Completable {
        return resetFirebaseRemoteConfig()
    }

    // Reset any fetched configs before we update packaged defaults for driver segment
    private fun resetFirebaseRemoteConfig(): Completable {
        return remoteConfigDelegate.reset()
    }
}
