package co.app.food.landing.model

import android.os.Parcelable
import androidx.room.Entity
import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose
import kotlinx.parcelize.Parcelize

@Entity(tableName = "FoodItems_meeting_time")
@Parcelize
data class MeetingTime(
    @SerializedName("iLocalMillis")
    @Expose
    var iLocalMillis: Int
) : Parcelable
