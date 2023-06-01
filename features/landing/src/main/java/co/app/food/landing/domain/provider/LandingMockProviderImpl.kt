package co.app.food.landing.domain.provider

import co.app.food.landing.model.FoodItemDetailsResponse
import co.app.food.landing.model.CrustsItem
import io.reactivex.rxjava3.core.Flowable
import javax.inject.Inject
import javax.inject.Named

class LandingMockProviderImpl @Inject constructor(
    @Named("fetch_landing_response") val dashboardResponse: List<FoodItemDetailsResponse>
) : LandingProvider {
    override fun getData(url: String): Flowable<List<CrustsItem>> {
        return Flowable.just(dashboardResponse)
            .map {
                if (it.isEmpty())
                    emptyList()
                else
                    it[0].crusts
            }
    }

    override fun updateData(FoodItems: List<CrustsItem>) {
    }
}
