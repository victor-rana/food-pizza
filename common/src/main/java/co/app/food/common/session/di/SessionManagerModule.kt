package co.app.food.common.session.di

import co.app.food.common.session.SessionManager
import co.app.food.common.session.SessionManagerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class SessionManagerModule {
    @Binds
    abstract fun manager(impl: SessionManagerImpl): SessionManager
}
