package co.app.food.common.di

import android.content.Context
import co.app.food.common.*
import co.app.food.common.R
import co.app.food.common.preferences.PreferenceHandlerImpl
import co.app.food.common.session.SessionManager
import co.app.food.common.toggles.FeatureToggles
import co.app.food.common.utils.TimeImpl
import co.app.food.configs.*
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class ConfigModule {

    @Provides
    fun providesFirebaseRemoteConfig() = FirebaseRemoteConfig.getInstance()

    @Provides
    fun provideConfigProvider(firebaseRemoteConfig: FirebaseRemoteConfig): ConfigProvider =
        ConfigProviderImpl(firebaseRemoteConfig)

    @Provides
    fun providesConfigServiceDependency(
        @ApplicationContext context: Context,
        sessionManager: SessionManager
    ): ConfigServiceDependency {
        return object : ConfigServiceDependency {
            override fun getContext(): Context {
                return context
            }

            override fun getDefaultResId(): Int {
                return R.xml.defaults
            }

            override fun getUserIdProvider(): UserIdProvider {
                return object : UserIdProvider {
                    override fun get(): String? {
                        return sessionManager.getUserId()
                    }
                }
            }

            override fun getSessionInfo(): SessionInfo {
                return object : SessionInfo {
                    override fun isSessionValid(): Boolean {
                        return sessionManager.isSessionActive()
                    }
                }
            }
        }
    }

    @Provides
    fun providesConfigService(configServiceDependency: ConfigServiceDependency): ConfigService {
        return ConfigService.getInstance(configServiceDependency)
    }

    @Provides
    fun provideRemoteConfigDelegate(
        configProvider: ConfigProvider,
        firebaseRemoteConfig: FirebaseRemoteConfig,
        configServiceDependency: ConfigServiceDependency,
        remoteConfigStatsCollector: RemoteConfigStatsCollector,
    ): RemoteConfigDelegate {
        return ConfigProviderDelegate(
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

    @Provides
    fun provideFeatureToggles(context: Context): FeatureToggles {
        return FeatureToggles.getInstance(context)
    }

    @Provides
    fun providesRemoteConfigStatsCollector(
        configServiceDependency: ConfigServiceDependency
    ): RemoteConfigStatsCollector {
        return RemoteConfigStatsCollector(
            configServiceDependency.getUserIdProvider(),
            FirebaseAnalytics.getInstance(configServiceDependency.getContext()),
            configServiceDependency.getContext().resources
        )
    }
}
