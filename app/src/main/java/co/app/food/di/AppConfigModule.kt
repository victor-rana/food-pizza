package co.app.food.di

import android.content.Context
import co.app.food.AppConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class AppConfigModule {
    @Provides
    fun providesAppConfig(@ApplicationContext context: Context): AppConfig = AppConfig(context)
}
