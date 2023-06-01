package co.app.food.andromeda.assets.icon

import android.os.Parcelable
import co.app.food.andromeda.Color as AndromedaColor
import kotlinx.android.parcel.Parcelize

@Parcelize
data class IconMetaData(
    var icon: Icon,
    var iconColor: AndromedaColor
) : Parcelable
