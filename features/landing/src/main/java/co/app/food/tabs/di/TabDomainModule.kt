package co.app.food.tabs.di

import co.app.food.tabs.domain.TabRepository
import co.app.food.tabs.domain.TabRepositoryImpl
import co.app.food.tabs.domain.TabUseCase
import co.app.food.tabs.domain.TabUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface TabDomainModule {
    @Binds
    fun repository(impl: TabRepositoryImpl): TabRepository

    @Binds
    fun useCase(impl: TabUseCaseImpl): TabUseCase
}
