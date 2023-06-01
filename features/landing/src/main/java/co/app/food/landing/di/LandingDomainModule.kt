package co.app.food.landing.di

import co.app.food.landing.domain.LandingRepository
import co.app.food.landing.domain.LandingRepositoryImpl
import co.app.food.landing.domain.LandingUseCase
import co.app.food.landing.domain.LandingInteractor
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface LandingDomainModule {
    @Binds
    fun repository(impl: LandingRepositoryImpl): LandingRepository

    @Binds
    fun useCase(impl: LandingInteractor): LandingUseCase
}
