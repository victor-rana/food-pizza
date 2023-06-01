package co.app.food.landing.domain.provider

import co.app.food.landing.model.CrustsItem
import io.reactivex.rxjava3.core.Flowable

interface LandingProvider {
    fun getData(url: String): Flowable<List<CrustsItem>>
    fun updateData(FoodItems: List<CrustsItem>)
}
