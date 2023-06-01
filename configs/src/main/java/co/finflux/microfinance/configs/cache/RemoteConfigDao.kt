package co.app.food.configs.cache

import android.content.SharedPreferences

class RemoteConfigDao(private val sharedPreferences: SharedPreferences) {

    fun write(configs: Map<String, String>) {
        sharedPreferences.edit().run {
            configs.forEach { (key, value) ->
                putString(key, value)
            }
            apply()
        }
    }

    fun read(key: String): String? {
        return sharedPreferences.getString(key, null)
    }

    fun clear() {
        sharedPreferences.edit().clear().apply()
    }
}
