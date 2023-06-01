package co.app.food

import android.content.Context
import android.os.Bundle
import co.app.food.common.RemoteConfigDelegate
import com.google.firebase.analytics.FirebaseAnalytics

private const val EVENT_TEST_FRC = "frc_test_config"

const val KEY_TEST_FRC = "test_frc"

private const val PARAM_TEST_FRC_SOURCE = "source"
private const val PARAM_TEST_FRC_VALUE = "test_config"
private const val PARAM_TEST_FRC_SEGMENT = "segment"
private const val PARAM_DRIVER_ID = "driver_id"

const val SOURCE_FRC_PACKAGED_DEFAULT = "packaged_default"
const val SOURCE_FRC_REMOTE_SEGMENT = "remote_segment"
const val SOURCE_FRC_REMOTE = "remote"
const val SOURCE_FRC_STATIC = "static"

private const val DEFAULT_SEGMENT = "default:%s:%s"
private const val REMOTE_SEGMENT = "remote:%s:%s"
private const val SEGMENT = "%s:%s"

class RemoteConfigCheck(
    private val remoteConfigDelegate: RemoteConfigDelegate
) {

    fun track(context: Context) {
        val testConfig = remoteConfigDelegate.readString(KEY_TEST_FRC)
        val segment = SEGMENT

        val attrs = Bundle().apply {
            putString(PARAM_TEST_FRC_SOURCE, getConfigSource(testConfig))
            putString(PARAM_TEST_FRC_VALUE, testConfig)
            putString(PARAM_TEST_FRC_SEGMENT, segment)
        }

        FirebaseAnalytics.getInstance(context).logEvent(EVENT_TEST_FRC, attrs)
    }

    fun getConfigSource(testConfigValue: String): String {
        val defaultSegment = DEFAULT_SEGMENT

        val remoteSegment = REMOTE_SEGMENT

        return when {
            defaultSegment.equals(testConfigValue, true) -> {
                SOURCE_FRC_PACKAGED_DEFAULT
            }
            remoteSegment.equals(testConfigValue, true) -> {
                SOURCE_FRC_REMOTE_SEGMENT
            }
            testConfigValue.isEmpty() -> {
                SOURCE_FRC_STATIC
            }
            else -> {
                SOURCE_FRC_REMOTE
            }
        }
    }
}
