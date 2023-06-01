package co.app.food.andromeda.icons

import android.os.Parcelable
import co.app.food.andromeda.assets.icon.Icon
import kotlinx.parcelize.Parcelize

@Parcelize
data class IconData(
    var icon: Icon,
    var iconColorToken: Int
) : Parcelable
