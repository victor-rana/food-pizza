package co.app.food.tabs.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TabArgs(
    val url: String
) : Parcelable
