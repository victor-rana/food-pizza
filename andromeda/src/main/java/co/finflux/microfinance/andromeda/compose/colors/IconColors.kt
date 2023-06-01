package co.app.food.andromeda.compose.colors

import androidx.compose.ui.graphics.Color as ComposeColor

/**
 * Contains all the icon colors in our palette. Each color is used for various states of an icon component.
 *
 *  ::WIP:: to add params desc
 * */
interface IconColors {
    val iconStaticBlack: ComposeColor
    val iconStaticWhite: ComposeColor
    val iconStaticInactive: ComposeColor
    val iconDynamicDefault: ComposeColor
    val iconDynamicActive: ComposeColor
    val iconDynamicInactive: ComposeColor
    val iconDynamicInverted: ComposeColor
    val iconDynamicError: ComposeColor
}
