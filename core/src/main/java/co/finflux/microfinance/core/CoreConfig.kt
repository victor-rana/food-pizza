package co.app.food.core

import com.google.firebase.installations.FirebaseInstallations

class CoreConfig {
    fun getBaseUrl() = "${BuildConfig.BASE_URL}${provider()}"

    /**
     * Refer to
     *    https://firebase.google.com/docs/reference/android/com/google/firebase/installations/FirebaseInstallations
     */
    fun getUniqueId() = FirebaseInstallations.getInstance().id

    fun clientSecret() = BuildConfig.CLIENT_SECRET
    fun clientId() = BuildConfig.CLIENT_ID
    fun provider() = BuildConfig.API_PROVIDER
}
