package co.app.food.configs.usecases

import co.app.food.configs.RemoteConfigStatsCollector
import co.app.food.configs.SessionInfo
import co.app.food.configs.cache.RemoteConfigCache
import timber.log.Timber

class UpdateConfigCacheUseCase(
    private val sessionInfo: SessionInfo,
    private val configCache: RemoteConfigCache,
    private val remoteConfigStatsCollector: RemoteConfigStatsCollector
) {

    fun invoke(configs: Map<String, Any>) {
        if (!sessionInfo.isSessionValid()) return

        Timber.i("Remote Config Cache: Flushed old configs, new configs written")
        configCache.flush()
        configCache.save(configs)
        remoteConfigStatsCollector.trackConfigCacheUpdateSuccess()
    }
}
