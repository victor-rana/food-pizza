package co.app.food.common.toggles

import android.annotation.SuppressLint
import android.content.Context
import co.app.food.common.BuildConfig
import co.app.food.common.CONFIG_SERVICE
import co.app.food.common.ConfigService
import co.app.food.common.preferences.PreferenceHandlerImpl
import co.app.food.common.toggles.rescue.StopgapToggles
import co.app.food.common.toggles.rescue.ToggleState
import co.app.food.core.SingletonHolder

const val KEY_FIREBASE_SYNC_ENABLED = "firebase_sync_enabled"
const val PREF_FEATURE_TOGGLES = "feature_toggles"

class FeatureToggles private constructor(
    context: Context
) {
    lateinit var stopgapToggles: StopgapToggles
    private val pref = PreferenceHandlerImpl(context, PREF_FEATURE_TOGGLES)

    @SuppressLint("WrongConstant")
    private val configService: ConfigService =
        context.getSystemService(CONFIG_SERVICE) as ConfigService

    fun isEnabled(toggle: String): Boolean {
        val isFireBaseSyncEnabled = BuildConfig.BUILD_TYPE == "release" ||
            pref.readBoolean(KEY_FIREBASE_SYNC_ENABLED, true)
        return if (isFireBaseSyncEnabled) {
            if (isStopgapInitialized()) {
                val toggleState = stopgapToggles.getToggleState(toggle)
                if (toggleState is ToggleState.Available) {
                    return toggleState.value
                }
            }

            configService.remoteConfigDelegate.isEnabled(toggle)
        } else {
            pref.readBoolean(toggle, false)
        }
    }

    fun isEnabled(toggle: String, default: Boolean): Boolean {
        val isFireBaseSyncEnabled = BuildConfig.BUILD_TYPE == "release" ||
            pref.readBoolean(KEY_FIREBASE_SYNC_ENABLED, true)
        return if (isFireBaseSyncEnabled) {
            if (isStopgapInitialized()) {
                val toggleState = stopgapToggles.getToggleState(toggle)
                if (toggleState is ToggleState.Available) {
                    return toggleState.value
                }
            }

            configService.remoteConfigDelegate.isEnabled(toggle, default)
        } else {
            pref.readBoolean(toggle, default)
        }
    }

    fun isStopgapInitialized(): Boolean {
        return ::stopgapToggles.isInitialized
    }

    fun setSegmentIdentifiers() {
        this.stopgapToggles = StopgapToggles()
    }

    fun setEnabled(toggle: String, isEnabled: Boolean) = pref.writeBoolean(toggle, isEnabled)

    companion object : SingletonHolder<FeatureToggles, Context>(::FeatureToggles) {
        @JvmStatic
        override fun getInstance(arg: Context): FeatureToggles = super.getInstance(arg)
    }
}
