package co.app.food.landing.model

import android.os.Parcelable
import androidx.room.Entity
import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose
import kotlinx.parcelize.Parcelize

@Entity(tableName = "FoodItem_status")
@Parcelize
data class Status(
    @SerializedName("id")
    @Expose
    var id: Int,

    @SerializedName("code")
    @Expose
    var code: String,

    @SerializedName("value")
    @Expose
    var value: String
) : Parcelable
