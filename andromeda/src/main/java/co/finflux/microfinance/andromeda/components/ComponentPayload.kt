package co.app.food.andromeda.components

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ComponentPayload(
    val data: ArrayList<ComponentData> = arrayListOf()
) : Parcelable
