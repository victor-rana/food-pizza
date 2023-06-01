package co.app.food.configs.usecases

import co.app.food.configs.cache.RemoteConfigCache

private const val TEST_CONFIG_KEY = "test_frc"

class SetConfigCacheDefaultsUseCase(
    private val configCache: RemoteConfigCache
) {

    fun invoke(configs: Map<String, Any>) {
        val testValue = configCache.getString(TEST_CONFIG_KEY)
        if (isConfigFromRemote(testValue)) return

        configCache.save(configs)
    }

    private fun isConfigFromRemote(string: String): Boolean {
        return string.contains("remote", false)
    }
}
