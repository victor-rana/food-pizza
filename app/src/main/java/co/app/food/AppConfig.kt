package co.app.food

import android.content.Context
import android.os.Build
import co.app.food.common.utils.isLollipopOrAbove

const val SUPPORTED_ABIS = "SUPPORTED_ABIS"

class AppConfig(context: Context) {

    private val appContext = context.applicationContext

    fun getVersionCode(): String = BuildConfig.VERSION_CODE.toString()

    fun getVersionName(): String = BuildConfig.VERSION_NAME

    fun isRelease(): Boolean = getBuildType() == BuildType.RELEASE

    fun isDebug(): Boolean = getBuildType() == BuildType.DEBUG

    fun getSupportedABIS(): List<String> {
        return if (isLollipopOrAbove()) {
            Build.SUPPORTED_ABIS.asList()
        } else {
            listOf(Build.CPU_ABI, Build.CPU_ABI2)
        }
    }

    private fun getBuildType(): BuildType =
        when (BuildConfig.BUILD_TYPE) {
            "debug" -> BuildType.DEBUG
            "integration" -> BuildType.INTEGRATION
            "release" -> BuildType.RELEASE
            else -> BuildType.DEBUG
        }

    enum class BuildType {
        DEBUG,
        INTEGRATION,
        RELEASE
    }
}
