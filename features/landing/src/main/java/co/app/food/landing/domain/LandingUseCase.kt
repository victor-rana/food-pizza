package co.app.food.landing.domain

import co.app.food.core.FeatureErrorCollector
import co.app.food.landing.model.FoodItemDetailsResponse
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

interface LandingUseCase {
    fun sync(): Single<FoodItemDetailsResponse>
    fun trackError(error: Throwable)
}

class LandingInteractor @Inject constructor(
    private val repository: LandingRepository,
    private val errorCollector: FeatureErrorCollector
) : LandingUseCase {

    override fun sync(): Single<FoodItemDetailsResponse> {
        return repository.sync()
    }

    override fun trackError(error: Throwable) {
        errorCollector.catchError("Home bottom Tab", error)
    }
}
