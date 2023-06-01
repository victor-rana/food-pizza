package co.app.food.tabs.model

import co.app.food.andromeda.components.ComponentPayload
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Url

interface TabApi {
    @GET
    fun loadTabContent(
        @Url url: String
    ): Single<ComponentPayload>
}
