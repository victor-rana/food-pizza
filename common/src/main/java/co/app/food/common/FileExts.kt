package co.app.food.common

import android.content.Context
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

fun <T> load(clazz: Class<T>, file: String): T {
    clazz.classLoader?.let { classLoader ->
        val fixtureStreamReader = InputStreamReader(classLoader.getResourceAsStream(file))
        return Gson().fromJson(fixtureStreamReader, clazz)
    } ?: throw IllegalArgumentException("classLoader value cannot be null")
}

inline fun <reified T> Context.readFromAsset(fileName: String, gson: Gson = Gson()): T {
    val br = BufferedReader(InputStreamReader(assets.open(fileName)))
    return gson.fromJson(br, T::class.java)
}

inline fun Context.getJsonFromAsset(fileName: String): String? {
    val jsonString: String
    try {
        jsonString = assets.open(fileName).bufferedReader().use { it.readText() }
    } catch (ioException: IOException) {
        ioException.printStackTrace()
        return null
    }
    return jsonString
}
