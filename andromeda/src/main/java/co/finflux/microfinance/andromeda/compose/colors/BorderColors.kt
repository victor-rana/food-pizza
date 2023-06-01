package co.app.food.andromeda.compose.colors

import androidx.compose.ui.graphics.Color as ComposeColor

/**
 * Contains all the border colors in our palette. Each color is used for various states of a border.
 *
 *  ::WIP:: to add params desc
 * */
interface BorderColors {
    val borderActive: ComposeColor
    val borderPressed: ComposeColor
    val borderInactive: ComposeColor
    val borderStaticWhite: ComposeColor
    val borderFocus: ComposeColor
    val borderError: ComposeColor
    val borderMutePrimary: ComposeColor
}
