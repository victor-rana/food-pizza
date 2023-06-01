package co.app.food.landing.domain

import co.app.food.common.base.ViewState
import co.app.food.landing.model.CrustsItem

sealed class LandingState : ViewState {
    data class TabContentLoaded(val CrustsItem: List<CrustsItem>) : LandingState()
    data class TabContentEmpty(val message: String) : LandingState()
    data class TabContentLoadFailed(val message: String) : LandingState()
    object ShowLoading : LandingState()
    object HideLoading : LandingState()
}
