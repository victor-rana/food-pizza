package co.app.food.landing.model

import androidx.room.TypeConverter
import com.google.gson.Gson

class FoodStatusTypeConverter {
    @TypeConverter
    fun sealedClassToString(sealedClass: FoodItemStatus): String = Gson().toJson(sealedClass)

    @TypeConverter
    fun sealedClassFromString(sealedClass: String): FoodItemStatus =
        Gson().fromJson(sealedClass, FoodItemStatus::class.java)
}
