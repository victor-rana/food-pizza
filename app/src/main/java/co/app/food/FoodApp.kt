package co.app.food

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import co.app.food.andromeda.theme.AndromedaThemeLifecycleCallbacks
import co.app.food.common.CONFIG_SERVICE
import co.app.food.common.ConfigService
import co.app.food.common.ConfigServiceDependency
import co.app.food.common.RemoteConfigDelegate
import co.app.food.common.TimberDebugTree
import co.app.food.common.session.SessionManager
import co.app.food.common.toggles.FeatureToggles
import com.jakewharton.threetenabp.AndroidThreeTen
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class FoodApp : Application() {

    private lateinit var configService: ConfigService

    @Inject
    lateinit var sessionManager: SessionManager

    @Inject
    lateinit var configServiceDependency: ConfigServiceDependency

    @Inject
    lateinit var remoteConfigDelegate: RemoteConfigDelegate

    @Inject
    lateinit var appConfig: AppConfig

    override fun onCreate() {
        super.onCreate()
        plantTimberTree()
        initComponents()
        setupInitializer()
    }

    override fun getSystemService(name: String): Any? {
        return if (name == CONFIG_SERVICE) {
            configService
        } else {
            super.getSystemService(name)
        }
    }

    private fun initComponents() {
        AndromedaThemeLifecycleCallbacks(this)
        AndroidThreeTen.init(this)
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
    }

    private fun plantTimberTree() {
        if (BuildConfig.DEBUG) {
            val logFormatting = PrettyFormatStrategy.newBuilder()
                .methodCount(0)
                .tag("food")
                .build()
            Logger.addLogAdapter(AndroidLogAdapter(logFormatting))
            Timber.plant(TimberDebugTree())
        }
    }

    private fun setupInitializer() {
        if (sessionManager.isSessionActive()) {
//            SetFirebaseUserPropertiesUseCase(appConfig, FirebaseAnalytics.getInstance(this))
//                .invoke()
        }

        configService = ConfigService.getInstance(configServiceDependency)

        if (sessionManager.isSessionActive()) {
            FeatureToggles.getInstance(this).setSegmentIdentifiers()
        }
        RemoteConfigCheck(remoteConfigDelegate = remoteConfigDelegate).track(this)
        ensureRemoteConfigInitialized()
    }

    private fun ensureRemoteConfigInitialized() {
//        SetRemoteConfigDefaultsUseCase(configService.remoteConfigDelegate)
//            .invoke()
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe(object : SingleObserver<Result<Boolean?>?> {
//                override fun onSubscribe(d: Disposable) {
//                    // NO-OP
//                }
//
//                override fun onError(error: Throwable) {
//                    Timber.e(error)
//                }
//
//                override fun onSuccess(t: Result<Boolean?>?) {
//                    // NO-OP
//                }
//            })
    }
}
