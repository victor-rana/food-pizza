package co.app.food.domain

import co.app.food.R
import co.app.food.common.RemoteConfigDelegate
import io.reactivex.rxjava3.core.Single
import java.util.concurrent.TimeUnit

private const val MAX_IO_TIMEOUT = 600L

class SetRemoteConfigDefaultsUseCase(
    private val remoteConfigDelegate: RemoteConfigDelegate
) {
    fun invoke(): Single<Result<Boolean>> {
        return remoteConfigDelegate
            .setDefaults(getDefaultsResId())
            .flatMap { remoteConfigDelegate.ensureInitialized() }
            .timeout(MAX_IO_TIMEOUT, TimeUnit.SECONDS)
    }

    private fun getDefaultsResId(): Int {
        return R.xml.defaults
    }
}
