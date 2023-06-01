package co.app.food.landing.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable

@Dao
interface FoodItemsDao {

    @Query("SELECT * FROM crusts")
    fun getAllFoodItems(): Flowable<List<CrustsItem>>

    @Insert
    fun insertFoodItems(FoodItems: List<CrustsItem>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateFoodItem(FoodItem: CrustsItem): Completable

    @Query("DELETE FROM crusts")
    fun nuke(): Completable
}
