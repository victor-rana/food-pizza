package co.app.food.andromeda.navbar

import androidx.annotation.IdRes
import co.app.food.andromeda.assets.icon.Icon

data class MenuItem(
    @IdRes val id: Int,
    val title: String?,
    val icon: Icon,
    val iconColorToken: Int
)
