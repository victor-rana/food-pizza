package co.app.food.domain

import co.app.food.common.RemoteConfigDelegate
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.core.Single
import java.util.concurrent.TimeUnit

const val MAX_FETCH_TIMEOUT = 7L

class FetchRemoteConfigSegmentUseCase(
    private val remoteConfigDelegate: RemoteConfigDelegate,
    private val scheduler: Scheduler,
    private val setFirebaseUserPropertiesUseCase: SetFirebaseUserPropertiesUseCase
) {
    fun invoke(): Single<FetchRemoteConfigSegmentResult> {
        return Single
            .fromCallable { setUserProperties() }
            .flatMap {
                remoteConfigDelegate
                    .fetchConfigFromRemote()
                    .timeout(MAX_FETCH_TIMEOUT, TimeUnit.SECONDS, scheduler)
                    .onErrorReturn { Result.success(true) }
            }
            .map { FetchRemoteConfigSegmentResult.Success }
    }

    private fun setUserProperties() {
        setFirebaseUserPropertiesUseCase.invoke()
    }

    sealed class FetchRemoteConfigSegmentResult {
        object Success : FetchRemoteConfigSegmentResult()
    }
}
