package co.app.food.core

import com.google.gson.annotations.SerializedName

data class ErrorResponse(
    @SerializedName("error_description")
    val errorDescription: String = "",
    @SerializedName("error")
    val error: String = ""
)
