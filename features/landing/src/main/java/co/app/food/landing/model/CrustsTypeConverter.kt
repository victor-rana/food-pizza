package co.app.food.landing.model

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class CrustsTypeConverter {

    @TypeConverter
    fun fromCrustsItem(attendance: List<CrustsItem>): String {
        val gson = Gson()
        val type: Type = object : TypeToken<List<CrustsItem>>() {}.type
        return gson.toJson(attendance, type)
    }

    @TypeConverter
    fun toCrustsItem(value: String): List<CrustsItem> {
        val gson = Gson()
        val type: Type = object : TypeToken<List<CrustsItem>>() {}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun fromSizes(attendance: List<Sizes>): String {
        val gson = Gson()
        val type: Type = object : TypeToken<List<CrustsItem>>() {}.type
        return gson.toJson(attendance, type)
    }

    @TypeConverter
    fun toSizes(value: String): List<Sizes> {
        val gson = Gson()
        val type: Type = object : TypeToken<List<CrustsItem>>() {}.type
        return gson.fromJson(value, type)
    }
}
