package co.app.food.andromeda.compose.colors

import androidx.compose.material.Button
import androidx.compose.material.ButtonColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.State
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.graphics.Color

val disabledBackgroundColor = Color.parse("#CCCCCC")
val disabledContentColor = Color.parse("#FFFFFF")

@Immutable
class AndromedaComposeButtonColors(
    private val bgColor: Color,
    private val contentColor: Color
) : ButtonColors {
    @Composable
    override fun backgroundColor(enabled: Boolean): State<Color> {
        return rememberUpdatedState(if (enabled) bgColor else disabledBackgroundColor)
    }

    @Composable
    override fun contentColor(enabled: Boolean): State<Color> {
        return rememberUpdatedState(if (enabled) contentColor else disabledContentColor)
    }
}

object AndromedaComposeButtonDefaultColors {

    /**
     * Creates a [AndromedaComposeButtonColors] that represents the default background and content colors used in
     * a [Button].
     *
     * @param backgroundColor the background color of this [Button] when enabled
     * @param contentColor the content color of this [Button] when enabled
     */
    @Composable
    fun buttonColors(
        backgroundColor: Color = Color.parse("#2196F3"),
        contentColor: Color = Color.parse("#FFFFFF")
    ): AndromedaComposeButtonColors = AndromedaComposeButtonColors(
        bgColor = backgroundColor,
        contentColor = contentColor
    )
}
