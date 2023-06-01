package co.app.food.landing.domain.provider

import co.app.food.landing.model.FoodItemsDao
import co.app.food.landing.model.CrustsItem
import io.reactivex.rxjava3.core.Flowable
import javax.inject.Inject

class LandingDataProvider @Inject constructor(
    private val landingDao: FoodItemsDao,
) : LandingProvider {
    override fun getData(
        url: String
    ): Flowable<List<CrustsItem>> {
        return landingDao.getAllFoodItems()
    }

    override fun updateData(FoodItems: List<CrustsItem>) {
        return landingDao.insertFoodItems(FoodItems = FoodItems)
    }
}
