package co.app.food.andromeda.compose.colors

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.colorResource
import co.app.food.andromeda.R
import androidx.compose.ui.graphics.Color as ComposeColor

/**
 * Contains all the colors in our palette. Each color is used for various things an can be changed to
 * customize the app design style.
 *
 * ::WIP:: to add params desc
 * */
class AndromedaComposeColors(
    override val fillPressed: ComposeColor,
    override val rippleControlHighlight: ComposeColor,
    override val fillActivePrimary: ComposeColor,
    override val fillActiveSecondary: ComposeColor,
    override val fillInActivePrimary: ComposeColor,
    override val fillInActiveSecondary: ComposeColor,
    override val fillErrorPrimary: ComposeColor,
    override val fillErrorSecondary: ComposeColor,
    override val fillMutePrimary: ComposeColor,
    override val fillMuteSecondary: ComposeColor,
    override val fillBackgroundPrimary: ComposeColor,
    override val fillBackgroundSecondary: ComposeColor,
    override val fillBackgroundTertiary: ComposeColor,
    override val borderActive: ComposeColor,
    override val borderPressed: ComposeColor,
    override val borderInactive: ComposeColor,
    override val borderStaticWhite: ComposeColor,
    override val borderFocus: ComposeColor,
    override val borderError: ComposeColor,
    override val borderMutePrimary: ComposeColor,
    override val iconStaticBlack: ComposeColor,
    override val iconStaticWhite: ComposeColor,
    override val iconStaticInactive: ComposeColor,
    override val iconDynamicDefault: ComposeColor,
    override val iconDynamicActive: ComposeColor,
    override val iconDynamicInactive: ComposeColor,
    override val iconDynamicInverted: ComposeColor,
    override val iconDynamicError: ComposeColor,
    override val defaultFontColor: ComposeColor,
    override val activeFontColor: ComposeColor,
    override val inActiveFontColor: ComposeColor,
    override val errorFontColor: ComposeColor,
    override val staticWhiteFontColor: ComposeColor,
    override val invertedFontColor: ComposeColor,
    val fillCardColor: ComposeColor
) : BaseColors, FillColors, BorderColors, IconColors, FontColors {
    public companion object {
        @Composable
        fun defaultColors(): AndromedaComposeColors = AndromedaComposeColors(
            fillPressed = colorResource(id = R.color.fill_pressed_light),
            rippleControlHighlight = colorResource(id = R.color.colorControlHighlight),
            fillActivePrimary = colorResource(id = R.color.fill_active_primary_light),
            fillActiveSecondary = colorResource(id = R.color.fill_active_secondary_light),
            fillInActivePrimary = colorResource(id = R.color.fill_active_secondary_light),
            fillInActiveSecondary = colorResource(id = R.color.fill_inactive_secondary_light),
            fillErrorPrimary = colorResource(id = R.color.fill_error_primary_light),
            fillErrorSecondary = colorResource(id = R.color.fill_error_secondary_light),
            fillMutePrimary = colorResource(id = R.color.fill_mute_primary_light),
            fillMuteSecondary = colorResource(id = R.color.fill_mute_secondary_light),
            fillBackgroundPrimary = colorResource(id = R.color.fill_background_primary_light),
            fillBackgroundSecondary = colorResource(id = R.color.fill_background_secondary_light),
            fillBackgroundTertiary = colorResource(id = R.color.fill_background_tertiary_light),
            borderActive = colorResource(id = R.color.border_active_light),
            borderPressed = colorResource(id = R.color.border_pressed_light),
            borderInactive = colorResource(id = R.color.border_inactive_light),
            borderStaticWhite = colorResource(id = R.color.border_static_white_light),
            borderFocus = colorResource(id = R.color.border_focus_light),
            borderError = colorResource(id = R.color.border_error_light),
            borderMutePrimary = colorResource(id = R.color.border_mute_primary_light),
            iconStaticBlack = colorResource(id = R.color.icon_static_black_light),
            iconStaticWhite = colorResource(id = R.color.icon_static_white_light),
            iconStaticInactive = colorResource(id = R.color.icon_static_inactive_light),
            iconDynamicDefault = colorResource(id = R.color.icon_dynamic_default_light),
            iconDynamicActive = colorResource(id = R.color.icon_dynamic_active_light),
            iconDynamicInactive = colorResource(id = R.color.icon_dynamic_inactive_light),
            iconDynamicInverted = colorResource(id = R.color.icon_dynamic_inverted_light),
            iconDynamicError = colorResource(id = R.color.icon_dynamic_error_light),
            defaultFontColor = colorResource(id = R.color.font_default_light),
            activeFontColor = colorResource(id = R.color.font_active_light),
            inActiveFontColor = colorResource(id = R.color.font_inactive_light),
            errorFontColor = colorResource(id = R.color.font_error_light),
            staticWhiteFontColor = ComposeColor.White,
            invertedFontColor = colorResource(id = R.color.font_inverted_light),
            fillCardColor = colorResource(id = R.color.fill_card_light)
        )

        @Composable
        public fun defaultDarkColors(): AndromedaComposeColors = AndromedaComposeColors(
            fillPressed = colorResource(id = R.color.fill_pressed_dark),
            rippleControlHighlight = colorResource(id = R.color.colorControlHighlight),
            fillActivePrimary = colorResource(id = R.color.fill_active_primary_dark),
            fillActiveSecondary = colorResource(id = R.color.fill_active_secondary_dark),
            fillInActivePrimary = colorResource(id = R.color.fill_active_secondary_dark),
            fillInActiveSecondary = colorResource(id = R.color.fill_inactive_secondary_dark),
            fillErrorPrimary = colorResource(id = R.color.fill_error_primary_dark),
            fillErrorSecondary = colorResource(id = R.color.fill_error_secondary_dark),
            fillMutePrimary = colorResource(id = R.color.fill_mute_primary_dark),
            fillMuteSecondary = colorResource(id = R.color.fill_mute_secondary_dark),
            fillBackgroundPrimary = colorResource(id = R.color.fill_background_primary_dark),
            fillBackgroundSecondary = colorResource(id = R.color.fill_background_secondary_dark),
            fillBackgroundTertiary = colorResource(id = R.color.fill_background_tertiary_dark),
            borderActive = colorResource(id = R.color.border_active_dark),
            borderPressed = colorResource(id = R.color.border_pressed_dark),
            borderInactive = colorResource(id = R.color.border_inactive_dark),
            borderStaticWhite = colorResource(id = R.color.border_static_white_dark),
            borderFocus = colorResource(id = R.color.border_focus_dark),
            borderError = colorResource(id = R.color.border_error_dark),
            borderMutePrimary = colorResource(id = R.color.border_mute_primary_dark),
            iconStaticBlack = colorResource(id = R.color.icon_static_black_dark),
            iconStaticWhite = colorResource(id = R.color.icon_static_white_dark),
            iconStaticInactive = colorResource(id = R.color.icon_static_inactive_dark),
            iconDynamicDefault = colorResource(id = R.color.icon_dynamic_default_dark),
            iconDynamicActive = colorResource(id = R.color.icon_dynamic_active_dark),
            iconDynamicInactive = colorResource(id = R.color.icon_dynamic_inactive_dark),
            iconDynamicInverted = colorResource(id = R.color.icon_dynamic_inverted_dark),
            iconDynamicError = colorResource(id = R.color.icon_dynamic_error_dark),
            defaultFontColor = colorResource(id = R.color.font_default_dark),
            activeFontColor = colorResource(id = R.color.font_active_dark),
            inActiveFontColor = colorResource(id = R.color.font_inactive_dark),
            errorFontColor = colorResource(id = R.color.font_error_dark),
            staticWhiteFontColor = ComposeColor.White,
            invertedFontColor = colorResource(id = R.color.font_inverted_dark),
            fillCardColor = colorResource(id = R.color.font_inverted_dark),
        )
    }
}
