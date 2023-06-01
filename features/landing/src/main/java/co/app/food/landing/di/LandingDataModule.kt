package co.app.food.landing.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import co.app.food.common.getJsonFromAsset
import co.app.food.landing.model.FoodItemDetailsResponse
import co.app.food.landing.model.FoodDatabase
import co.app.food.landing.model.LandingApi
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit
import javax.inject.Named

@Module
@InstallIn(ViewModelComponent::class)
class LandingDataModule {
    @Provides
    fun providesLandingApi(retrofit: Retrofit): LandingApi {
        return retrofit.create(LandingApi::class.java)
    }

    @Provides
    @Named("fetch_landing_response")
    fun providesLandingMockResponse(
        @ApplicationContext context: Context,
        gson: Gson
    ): List<FoodItemDetailsResponse> {
        val responseString = context.getJsonFromAsset(fileName = "fetch_landing_response.json")
        val listLandingResponseType = object : TypeToken<List<FoodItemDetailsResponse>>() {}.type

        return gson.fromJson(responseString, listLandingResponseType)
    }

    @Provides
    fun provideDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
        FoodDatabase::class.java,
        "FoodItems"
    )
        .setJournalMode(RoomDatabase.JournalMode.AUTOMATIC)
        .build()

    @Provides
    fun provideDao(db: FoodDatabase) =
        db.FoodItemsDao()
}
