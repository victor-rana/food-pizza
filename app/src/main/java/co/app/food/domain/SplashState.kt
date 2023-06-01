package co.app.food.domain

import co.app.food.common.base.ViewState

sealed class SplashState : ViewState {
    object ShowLoading : SplashState()
    object NavigateToNextScreen : SplashState()
    object NavigateToLoginScreen : SplashState()
    object NavigateToHomeScreen : SplashState()
    object NavigateOnboardingScreen : SplashState()
    object NavigateSecureScreen : SplashState()
    data class NavigateToVerificationScreen(val status: Int) : SplashState()
}
