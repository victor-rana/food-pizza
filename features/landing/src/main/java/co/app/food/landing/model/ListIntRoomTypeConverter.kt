package co.app.food.landing.model

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class ListIntRoomTypeConverter {
    private val gson = Gson()

    @TypeConverter
    fun toList(data: String): List<Int> {
        val listType: Type = object : TypeToken<List<Int>>() {}.type
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun toString(someObjects: List<Int>): String {
        return gson.toJson(someObjects)
    }
}
