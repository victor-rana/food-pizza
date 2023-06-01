package co.app.food.andromeda.viewpager

import android.os.Parcelable
import co.app.food.andromeda.assets.icon.IconMetaData
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PageItem(
    val badge: Int = 0,
    val icon: IconMetaData? = null,
    val title: String = "",
    val urlToLoad: String = ""
) : Parcelable
