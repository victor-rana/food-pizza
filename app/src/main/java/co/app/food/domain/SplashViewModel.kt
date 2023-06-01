package co.app.food.domain

import androidx.lifecycle.MutableLiveData
import co.app.food.common.addTo
import co.app.food.common.base.BaseViewModel
import co.app.food.common.completableIo
import co.app.food.common.observableIo
import co.app.food.common.session.SessionManager
import co.app.food.common.session.model.SessionState
import co.app.food.common.singleIo
import co.app.food.core.FeatureErrorCollector
import co.app.food.domain.SplashState.*
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val errorCollector: FeatureErrorCollector,
    private val sessionManager: SessionManager,
    private val forceFetchRemoteConfigAppUseCase: ForceFetchRemoteConfigAppUseCase
) : BaseViewModel() {
    private val _state = MutableLiveData<SplashState>()
    val state = _state
    private val splashTimer = 3L

    fun load() {
        _state.value = ShowLoading
    }

    fun initFRC() {
        forceFetchRemoteConfigAppUseCase.invoke()
            .timeout(splashTimer, TimeUnit.SECONDS, Schedulers.io())
            .compose(completableIo())
            .doOnError {
                errorCollector.catchError("frc_init", it)
            }
            .onErrorComplete()
            .subscribe({
                Timber.tag("frc_init").d("FRC initialized")
            }, {
                // No-op
            })
            .addTo(bag)
    }

    fun startTimer() {
        Observable.timer(splashTimer, TimeUnit.SECONDS)
            .compose(observableIo())
            .subscribe {
                _state.value = NavigateToNextScreen
            }
            .addTo(bag)
    }

    fun decideUserNavigation() {
        sessionManager.checkSessionIsActive()
            .compose(singleIo())
            .subscribe(
                { status ->
                    when (status) {
                        SessionState.DeviceSecure,
                        SessionState.NotLoggedIn -> {
                            _state.value = NavigateToLoginScreen
                        }
                        SessionState.LoggedIn -> {
                            _state.value = NavigateToHomeScreen
                        }
                        is SessionState.NotVerified -> {
                            _state.value = NavigateToVerificationScreen(status.status)
                        }
                        SessionState.SessionInvalid,
                        SessionState.ShowOnboarding -> {
                            _state.value = NavigateOnboardingScreen
                        }
                        SessionState.DeviceUnSecure -> {
                            _state.value = NavigateSecureScreen
                        }
                    }
                },
                { error ->
                    errorCollector.catchError("Splash-screen", error)
                    _state.value = NavigateOnboardingScreen
                }
            )
            .addTo(bag)
    }
}
