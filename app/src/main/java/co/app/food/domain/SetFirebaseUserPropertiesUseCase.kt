package co.app.food.domain

import android.os.Build
import co.app.food.AppConfig
import com.google.firebase.analytics.FirebaseAnalytics

const val USER_PROP_CPU_ARCHITECTURE = "CPU_ARCHITECTURE"
const val USER_PROP_ANDROID_SDK_INT = "ANDROID_SDK_INT"

class SetFirebaseUserPropertiesUseCase(
    private val appConfig: AppConfig,
    private val firebaseAnalytics: FirebaseAnalytics
) {

    fun invoke() {
        firebaseAnalytics.setUserProperty(
            USER_PROP_CPU_ARCHITECTURE,
            appConfig.getSupportedABIS().joinToString()
        )
        firebaseAnalytics.setUserProperty(
            USER_PROP_ANDROID_SDK_INT,
            Build.VERSION.SDK_INT.toString()
        )
    }
}
