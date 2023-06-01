package co.app.food.andromeda.compose

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.platform.LocalContext
import co.app.food.andromeda.compose.colors.AndromedaComposeButtonColors
import co.app.food.andromeda.compose.colors.AndromedaComposeButtonDefaultColors
import co.app.food.andromeda.compose.colors.AndromedaComposeColors
import co.app.food.andromeda.compose.shape.AndromedaComposeShapes
import co.app.food.andromeda.compose.typography.AndromedaComposeTypography
import co.app.food.andromeda.compose.typography.textStyles
import coil.compose.LocalImageLoader

/**
 * Local providers for various properties we connect to our components, for styling.
 * */
private val LocalColors = compositionLocalOf<AndromedaComposeColors> {
    error(
        "No colors provided! Make sure to wrap all usages of components in a ."
    )
}
private val LocalButtonColors = compositionLocalOf<AndromedaComposeButtonColors> {
    error(
        "No button colors provided! Make sure to wrap all usages of components in a " +
            "AndromedaComposeTheme."
    )
}
private val LocalTypography = compositionLocalOf<AndromedaComposeTypography> {
    error(
        "No typography provided! Make sure to wrap all usages of components in a " +
            "AndromedaComposeTheme."
    )
}
private val LocalShapes = compositionLocalOf<AndromedaComposeShapes> {
    error(
        "No shapes provided! Make sure to wrap all usages of Andromeda components in a " +
            "AndromedaComposeTheme."
    )
}

@Composable
fun AndromedaComposeTheme(
    isInDarkMode: Boolean = isSystemInDarkTheme(),
    colors: AndromedaComposeColors = if (isInDarkMode)
        AndromedaComposeColors.defaultDarkColors()
    else
        AndromedaComposeColors.defaultColors(),
    buttonColors: AndromedaComposeButtonColors = AndromedaComposeButtonDefaultColors.buttonColors(),
    typography: AndromedaComposeTypography = textStyles(isDarkTheme = isInDarkMode),
    shapes: AndromedaComposeShapes = AndromedaComposeShapes.default,
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(
        LocalColors provides colors,
        LocalTypography provides typography,
        LocalShapes provides shapes,
        LocalButtonColors provides buttonColors,
        LocalImageLoader provides AndromedaCoilImageLoader.imageLoader(LocalContext.current)
    ) {
        content()
    }
}

/**
 * Useful static object to access currently configured Theme properties.
 */
object AndromedaComposeTheme {

    /**
     * These represent the default ease-of-use accessors for colors, typography, shapes, attachment factories and
     * reaction types.
     * */
    public val colors: AndromedaComposeColors
        @Composable
        @ReadOnlyComposable
        get() = LocalColors.current

    public val buttonColors: AndromedaComposeButtonColors
        @Composable
        @ReadOnlyComposable
        get() = LocalButtonColors.current

    public val typography: AndromedaComposeTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalTypography.current

    public val shapes: AndromedaComposeShapes
        @Composable
        @ReadOnlyComposable
        get() = LocalShapes.current
}
