package co.app.food.landing.model

import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Url

interface LandingApi {
    @GET
    fun getDashboardContent(
        @Url url: String
    ): Single<FoodItemDetailsResponse>
}
