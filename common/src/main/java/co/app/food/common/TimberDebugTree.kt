package co.app.food.common

import com.orhanobut.logger.Logger
import timber.log.Timber

class TimberDebugTree : Timber.Tree() {
    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        Logger.log(priority, tag, message, t)
    }
}
