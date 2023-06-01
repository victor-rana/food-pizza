package co.app.food.landing.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Entity(tableName = "food_details")
@Parcelize
data class FoodItemDetailsResponse(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "Id")
    @SerializedName("id")
    var id: String,

    @SerializedName("name")
    @ColumnInfo(name = "name")
    var name: String,

    @SerializedName("isVeg")
    @ColumnInfo(name = "isVeg")
    var isVeg: Boolean,

    @SerializedName("description")
    @ColumnInfo(name = "description")
    var description: String,

    @SerializedName("defaultCrust")
    @ColumnInfo(name = "defaultCrust")
    var defaultCrust: Int,

    @SerializedName("crusts")
    @ColumnInfo(name = "crusts")
    var crusts: List<CrustsItem>
) : Parcelable
