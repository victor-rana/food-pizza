package co.app.food.andromeda.components.button.data

import android.os.Parcelable
import co.app.food.andromeda.Color as AndromedaColor
import co.app.food.andromeda.assets.icon.Icon
import kotlinx.parcelize.Parcelize

@Parcelize
data class ButtonIconData(
    var iconName: Icon,
    var iconColor: AndromedaColor
) : Parcelable
