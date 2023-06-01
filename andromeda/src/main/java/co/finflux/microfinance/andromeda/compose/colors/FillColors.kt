package co.app.food.andromeda.compose.colors

import androidx.compose.ui.graphics.Color as ComposeColor

/**
 * Contains all the fill colors in our palette. Each color is used for various states of
 * many UI component.
 *
 *  ::WIP:: to add params desc
 * */
interface FillColors {
    val fillActivePrimary: ComposeColor
    val fillActiveSecondary: ComposeColor
    val fillInActivePrimary: ComposeColor
    val fillInActiveSecondary: ComposeColor
    val fillErrorPrimary: ComposeColor
    val fillErrorSecondary: ComposeColor
    val fillMutePrimary: ComposeColor
    val fillMuteSecondary: ComposeColor
    val fillBackgroundPrimary: ComposeColor
    val fillBackgroundSecondary: ComposeColor
    val fillBackgroundTertiary: ComposeColor
    val fillPressed: ComposeColor
}
