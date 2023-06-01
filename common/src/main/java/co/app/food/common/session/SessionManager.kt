package co.app.food.common.session

import co.app.food.core.PreferenceHandler
import co.app.food.common.session.model.SessionState
import co.app.food.common.session.model.SessionUser
import io.reactivex.rxjava3.core.Single
import java.util.concurrent.atomic.AtomicReference
import javax.inject.Inject
import javax.inject.Named

private const val SESSION_TOKEN = "session-token"

/**
 * Exposed to allow features to check these fields
 */
const val DEVICE_SECURE_STATUS = "device_secure_status"
const val ON_BOARDING_STATUS = "on_boarding_status"
const val DEVICE_VERIFICATION_STATUS = "device_verify_status"

const val KEY_PROFILE = "profile"

interface SessionManager {
    fun checkSessionIsActive(): Single<SessionState>
    fun isSessionActive(): Boolean
    fun saveUser(user: SessionUser)
    fun getUserId(): String?
}

class SessionManagerImpl @Inject constructor(
    @Named("food-prefs")
    private val pref: PreferenceHandler,
    private val userReader: UserReader
) : SessionManager {
    private val userReference = AtomicReference<SessionUser>()

    override fun checkSessionIsActive(): Single<SessionState> {
        return Single.fromCallable {
            val secureStatus = pref.readBoolean(DEVICE_SECURE_STATUS, false)
            val sessionStatus = pref.readString(SESSION_TOKEN, "")
            val onboardingStatus = pref.readBoolean(ON_BOARDING_STATUS, false)
            val verificationStatus = pref.readInteger(DEVICE_VERIFICATION_STATUS)
            if (verificationStatus == 1 || verificationStatus == 2) {
                SessionState.NotVerified(verificationStatus)
            } else if (onboardingStatus) {
                when {
                    !secureStatus -> SessionState.DeviceUnSecure
                    secureStatus && sessionStatus.isNotEmpty() -> SessionState.LoggedIn
                    secureStatus && sessionStatus.isEmpty() -> SessionState.NotLoggedIn
                    else -> SessionState.SessionInvalid
                }
            } else {
                SessionState.ShowOnboarding
            }
        }
    }

    override fun isSessionActive(): Boolean {
        return pref.readString(SESSION_TOKEN, "").isNotEmpty()
    }

    override fun saveUser(user: SessionUser) {
        pref.writeInstance(KEY_PROFILE, user)
        userReference.set(user)
    }

    override fun getUserId(): String? {
        if (userReference.get() == null) {
            val user = userReader.read(pref.readString(KEY_PROFILE))
            userReference.set(user)
        }

        val user = userReference.get()
        return user?.userId ?: ""
    }
}
