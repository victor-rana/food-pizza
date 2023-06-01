package co.app.food.common.di

import android.content.Context
import co.app.food.core.PreferenceHandler
import co.app.food.common.preferences.PreferenceHandlerImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
class PrefsModule {

    @Provides
    @Named("food-prefs")
    fun providesDefaultPreferences(@ApplicationContext context: Context): PreferenceHandler {
        return PreferenceHandlerImpl(context, "loan_book")
    }
}
