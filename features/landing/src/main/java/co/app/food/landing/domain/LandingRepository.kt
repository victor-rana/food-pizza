package co.app.food.landing.domain

import android.annotation.SuppressLint
import co.app.food.common.RemoteConfigDelegate
import co.app.food.core.PreferenceHandler
import co.app.food.landing.domain.provider.LandingProvider
import co.app.food.landing.model.LandingApi
import co.app.food.landing.model.FoodItemDetailsResponse
import io.reactivex.rxjava3.core.Single
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import javax.inject.Named

interface LandingRepository {
    fun sync(): Single<FoodItemDetailsResponse>
}

class LandingRepositoryImpl @Inject constructor(
    @Named("mock_landing_provider")
    private val mockProvider: LandingProvider,
    @Named("local_landing_provider")
    private val localProvider: LandingProvider,
    private val landingApi: LandingApi,
    @Named("food-prefs")
    private val preferences: PreferenceHandler,
    private val remoteConfigDelegate: RemoteConfigDelegate
) : LandingRepository {

    @SuppressLint("SimpleDateFormat")
    val dateFormatter = SimpleDateFormat("dd+MMMM+yyyy")

    override fun sync(): Single<FoodItemDetailsResponse> {
        return landingApi.getDashboardContent(
            url = "https://625bbd9d50128c570206e502.mockapi.io/api/v1/pizza/1"
        )
    }

    private fun getDateString(): String {
        return dateFormatter.format(Date())
    }
}
