package co.app.food.domain

import co.app.food.common.RemoteConfigDelegate
import co.app.food.KEY_TEST_FRC
import co.app.food.RemoteConfigCheck

class GetRemoteConfigSourceUseCase(
    private val remoteConfigDelegate: RemoteConfigDelegate,
    private val remoteConfigCheck: RemoteConfigCheck
) {
    fun invoke(): String {
        val testConfigValue = remoteConfigDelegate.readString(KEY_TEST_FRC)
        return remoteConfigCheck.getConfigSource(testConfigValue)
    }
}
