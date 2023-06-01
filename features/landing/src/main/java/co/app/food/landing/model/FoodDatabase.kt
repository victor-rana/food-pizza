package co.app.food.landing.model

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [CrustsItem::class, FoodItemDetailsResponse::class],
    version = 1,
    exportSchema = true
)
@TypeConverters(
    ListIntRoomTypeConverter::class,
    FoodStatusTypeConverter::class,
    CrustsTypeConverter::class
)
abstract class FoodDatabase : RoomDatabase() {
    abstract fun FoodItemsDao(): FoodItemsDao
}
