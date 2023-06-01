package co.app.food.configs

import io.reactivex.rxjava3.core.Single

interface ConfigProvider {
    fun <T> get(key: String, defValue: T): T
    fun <T> getFromRemoteAsync(key: String, defValue: T): Single<T>
    fun setDefaults(defaults: Map<String, String>)
    fun isEnabled(key: String, default: Boolean = false): Boolean
    fun syncAll(): Single<Map<String, Any>>
}
