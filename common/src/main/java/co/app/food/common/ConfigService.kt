package co.app.food.common

import android.annotation.SuppressLint
import android.content.Context
import co.app.food.common.preferences.PreferenceHandlerImpl
import co.app.food.common.utils.TimeImpl
import co.app.food.configs.ConfigProvider
import co.app.food.configs.ConfigProviderImpl
import co.app.food.configs.RemoteConfigStatsCollector
import co.app.food.configs.SessionInfo
import co.app.food.configs.UserIdProvider
import co.app.food.core.SingletonHolder
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.gson.Gson

const val CONFIG_SERVICE = "config_service"

@SuppressLint("MissingPermission")
class ConfigService private constructor(
    private val configServiceDependency: ConfigServiceDependency
) {
    lateinit var remoteConfigDelegate: RemoteConfigDelegate
        private set
    private var firebaseAnalytics =
        FirebaseAnalytics.getInstance(configServiceDependency.getContext())
    private val firebaseRemoteConfig = FirebaseRemoteConfig.getInstance()
    private var configProvider: ConfigProvider =
        ConfigProviderImpl(firebaseRemoteConfig)
    private val remoteConfigStatsCollector = RemoteConfigStatsCollector(
        configServiceDependency.getUserIdProvider(),
        firebaseAnalytics,
        configServiceDependency.getContext().resources
    )

    init {
        initConfigProviderDelegate()
    }

    private fun initConfigProviderDelegate() {
        remoteConfigDelegate = ConfigProviderDelegate(
            configProvider,
            firebaseRemoteConfig,
            configServiceDependency.getContext(),
            preferenceHandler = PreferenceHandlerImpl(
                configServiceDependency.getContext(),
                ConfigProviderDelegate.PREF_CONFIG_PROVIDER
            ),
            time = TimeImpl(),
            gson = Gson(),
            sessionInfo = configServiceDependency.getSessionInfo(),
            remoteConfigStatsCollector = remoteConfigStatsCollector
        )
    }

    companion object : SingletonHolder<ConfigService, ConfigServiceDependency>(::ConfigService) {
        @JvmStatic
        override fun getInstance(arg: ConfigServiceDependency): ConfigService =
            super.getInstance(arg)
    }
}

interface ConfigServiceDependency {
    fun getContext(): Context
    fun getDefaultResId(): Int
    fun getUserIdProvider(): UserIdProvider
    fun getSessionInfo(): SessionInfo
}
