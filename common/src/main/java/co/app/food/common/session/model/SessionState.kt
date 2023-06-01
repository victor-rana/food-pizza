package co.app.food.common.session.model

sealed class SessionState {
    object ShowOnboarding : SessionState()
    object DeviceSecure : SessionState()
    object DeviceUnSecure : SessionState()

    object NotLoggedIn : SessionState()
    data class NotVerified(val status: Int) : SessionState()

    object SessionInvalid : SessionState()
    object LoggedIn : SessionState()
}
