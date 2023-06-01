package co.app.food.landing.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Entity(tableName = "crusts")
@Parcelize
data class CrustsItem(

    /* @Ignore
     var FoodItemStatus: FoodItemStatus = FoodItemStatus.Scheduled,*/

    @SerializedName("id")
    @Expose
    @ColumnInfo(name = "FoodItemId")
    @PrimaryKey(autoGenerate = false)
    var foodItemId: Int,

    @SerializedName("name")
    @Expose
    var name: String,

    @SerializedName("defaultSize")
    @Expose
    var defaultSize: Int,

    @SerializedName("sizes")
    @Expose
    var sizes: List<Sizes>

) : Parcelable
