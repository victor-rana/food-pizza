package co.app.food.tabs.presentation

sealed class TabEvent {
    data class ItemClickedEvent(val deepLink: String) : TabEvent()
    object ShowLoadingDialogEvent : TabEvent()
    object HideLoadingDialogEvent : TabEvent()
    object RefreshEvent : TabEvent()
    data class ItemFetchFailedEvent(val errorMessage: String) : TabEvent()
    data class ShowItemsFetchedEvent(val data: Any) : TabEvent()
}
