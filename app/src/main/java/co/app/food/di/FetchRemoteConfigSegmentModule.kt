package co.app.food.di

import android.content.Context
import android.preference.PreferenceManager
import co.app.food.common.RemoteConfigDelegate
import co.app.food.common.session.SessionManager
import co.app.food.AppConfig
import co.app.food.RemoteConfigCheck
import co.app.food.domain.*
import com.google.firebase.analytics.FirebaseAnalytics
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.reactivex.rxjava3.schedulers.Schedulers

@Module
@InstallIn(SingletonComponent::class)
class FetchRemoteConfigSegmentModule {

    @Provides
    fun providesFetchRemoteConfigSegmentUseCase(
        remoteConfigDelegate: RemoteConfigDelegate,
        setFirebaseUserPropertiesUseCase: SetFirebaseUserPropertiesUseCase
    ) = FetchRemoteConfigSegmentUseCase(
        remoteConfigDelegate,
        Schedulers.io(),
        setFirebaseUserPropertiesUseCase
    )

    @Provides
    fun providesRemoteConfigAppUpdateResetUseCase(
        @ApplicationContext context: Context,
        fetchRemoteConfigSegmentUseCase: FetchRemoteConfigSegmentUseCase,
        resetRemoteConfigUseCase: ResetRemoteConfigUseCase,
        remoteConfigDefaultsUseCase: SetRemoteConfigDefaultsUseCase
    ): RemoteConfigAppUpdateResetUseCase {
        return RemoteConfigAppUpdateResetUseCase(
            PreferenceManager.getDefaultSharedPreferences(context),
            fetchRemoteConfigSegmentUseCase,
            resetRemoteConfigUseCase,
            remoteConfigDefaultsUseCase
        )
    }

    @Provides
    fun providesSetRemoteConfigDefaultsUseCase(
        remoteConfigDelegate: RemoteConfigDelegate
    ) = SetRemoteConfigDefaultsUseCase(remoteConfigDelegate)

    @Provides
    fun providesResetRemoteConfigUseCase(
        remoteConfigDelegate: RemoteConfigDelegate
    ): ResetRemoteConfigUseCase {
        return ResetRemoteConfigUseCase(remoteConfigDelegate)
    }

    @Provides
    fun providesSetFirebaseUserPropertiesUseCase(
        @ApplicationContext context: Context,
        appConfig: AppConfig
    ): SetFirebaseUserPropertiesUseCase {
        return SetFirebaseUserPropertiesUseCase(appConfig, FirebaseAnalytics.getInstance(context))
    }

    @Provides
    fun providesRemoteConfigCheck(remoteConfigDelegate: RemoteConfigDelegate): RemoteConfigCheck {
        return RemoteConfigCheck(remoteConfigDelegate)
    }

    @Provides
    fun providesGetRemoteConfigSourceUseCase(
        remoteConfigDelegate: RemoteConfigDelegate,
        remoteConfigCheck: RemoteConfigCheck,
    ): GetRemoteConfigSourceUseCase {
        return GetRemoteConfigSourceUseCase(remoteConfigDelegate, remoteConfigCheck)
    }

    @Provides
    fun providesForceFetchRemoteConfigAppUseCase(
        @ApplicationContext context: Context,
        sessionManager: SessionManager,
        remoteConfigAppUpdateResetUseCase: RemoteConfigAppUpdateResetUseCase,
        getRemoteConfigSourceUseCase: GetRemoteConfigSourceUseCase,
        remoteConfigDelegate: RemoteConfigDelegate
    ): ForceFetchRemoteConfigAppUseCase {
        return ForceFetchRemoteConfigAppUseCase(
            sessionManager = sessionManager,
            remoteConfigDelegate = remoteConfigDelegate,
            preferences = PreferenceManager.getDefaultSharedPreferences(context),
            remoteConfigAppUpdateResetUseCase = remoteConfigAppUpdateResetUseCase,
            getRemoteConfigSourceUseCase = getRemoteConfigSourceUseCase
        )
    }
}
