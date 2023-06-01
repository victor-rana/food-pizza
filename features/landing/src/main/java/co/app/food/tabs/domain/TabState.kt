package co.app.food.tabs.domain

import co.app.food.andromeda.components.ComponentData
import co.app.food.common.base.ViewState

sealed class TabState : ViewState {
    data class TabContentLoaded(val components: List<ComponentData>) : TabState()
    data class TabContentEmpty(val message: String) : TabState()
    data class TabContentLoadFailed(val message: String) : TabState()
    object ShowLoading : TabState()
    object HideLoading : TabState()
}
