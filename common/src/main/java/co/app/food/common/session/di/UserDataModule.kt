package co.app.food.common.session.di

import co.app.food.common.session.UserReader
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class UserDataModule {
    @Provides
    fun providesUserReader() = UserReader()
}
