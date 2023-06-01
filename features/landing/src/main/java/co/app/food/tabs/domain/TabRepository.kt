package co.app.food.tabs.domain

import co.app.food.andromeda.components.ComponentPayload
import co.app.food.tabs.model.TabApi
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

interface TabRepository {
    fun loadTabContent(url: String): Single<ComponentPayload>
}

class TabRepositoryImpl @Inject constructor(
    private val api: TabApi
) : TabRepository {
    override fun loadTabContent(url: String): Single<ComponentPayload> {
        return api.loadTabContent(url)
    }
}
