package co.app.food.landing.presentation

import co.app.food.landing.model.NavigationItem

sealed class LandingEvent {
    data class HomeLoadingError(val message: String) : LandingEvent()
    data class HomeTabsFetchedEvent(val tabs: List<NavigationItem>) : LandingEvent()
    data class ItemClickedEvent(val deepLink: String) : LandingEvent()
    object ShowLoadingDialogEvent : LandingEvent()
    object HideLoadingDialogEvent : LandingEvent()
}
