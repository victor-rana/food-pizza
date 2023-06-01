package co.app.food.andromeda.inputfield.internal

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AndromedaFieldValidation(
    val listOfFieldRegex: List<FieldRegex>
) : Parcelable

@Parcelize
data class FieldRegex(
    val pattern: String,
    val errorMessage: String
) : Parcelable
