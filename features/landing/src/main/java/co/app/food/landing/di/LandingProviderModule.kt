package co.app.food.landing.di

import co.app.food.landing.domain.provider.LandingDataProvider
import co.app.food.landing.domain.provider.LandingMockProviderImpl
import co.app.food.landing.domain.provider.LandingProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import javax.inject.Named

@Module
@InstallIn(ViewModelComponent::class)
interface LandingProviderModule {
    @Binds
    @Named("mock_landing_provider")
    fun bindLandingMockProvider(impl: LandingMockProviderImpl): LandingProvider

    @Binds
    @Named("local_landing_provider")
    fun bindLandingApiProvider(impl: LandingDataProvider): LandingProvider
}
