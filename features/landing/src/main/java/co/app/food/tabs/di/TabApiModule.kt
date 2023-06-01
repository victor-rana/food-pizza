package co.app.food.tabs.di

import co.app.food.tabs.model.TabApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
class TabApiModule {
    @Provides
    fun providesTabsApi(retrofit: Retrofit): TabApi {
        return retrofit.create(TabApi::class.java)
    }
}
