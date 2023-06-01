package co.app.food.andromeda.compose

import androidx.compose.ui.graphics.Color as ComposeColor
import android.graphics.Color as AndroidColor

fun ComposeColor.Companion.parse(colorString: String): ComposeColor =
    ComposeColor(color = AndroidColor.parseColor(colorString))
