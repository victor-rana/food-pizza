package co.app.food.tabs.domain

import co.app.food.andromeda.components.ComponentData
import co.app.food.core.FeatureErrorCollector
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

interface TabUseCase {
    fun loadTabContent(url: String): Single<List<ComponentData>>
    fun trackError(error: Throwable)
}

class TabUseCaseImpl @Inject constructor(
    private val repository: TabRepository,
    private val errorCollector: FeatureErrorCollector
) : TabUseCase {
    override fun loadTabContent(url: String): Single<List<ComponentData>> {
        return repository.loadTabContent(url)
            .map {
                it.data
            }
    }

    override fun trackError(error: Throwable) {
        errorCollector.catchError("Home bottom Tab", error)
    }
}
