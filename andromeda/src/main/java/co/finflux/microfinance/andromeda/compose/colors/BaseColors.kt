package co.app.food.andromeda.compose.colors

import androidx.compose.ui.graphics.Color as ComposeColor

/**
 * Contains all the cross functional colors in our palette.
 * This subset of color attributes are generic to our design System [AndromedaTheme].
 *
 * */
interface BaseColors {
    /**
     * Ripple control color used across screens.
     */
    val rippleControlHighlight: ComposeColor
}
