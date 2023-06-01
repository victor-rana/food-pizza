package co.app.food.andromeda.compose.typography

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import co.app.food.andromeda.compose.colors.AndromedaComposeColors
import co.app.food.andromeda.compose.typography.body.BodyModerateTypographyStyle
import co.app.food.andromeda.compose.typography.body.BodySmallTypographyStyle
import co.app.food.andromeda.compose.typography.caption.CaptionModerateBookTypographyStyle
import co.app.food.andromeda.compose.typography.caption.CaptionModerateDemiTypographyStyle
import co.app.food.andromeda.compose.typography.caption.CaptionSmallBookTypographyStyle
import co.app.food.andromeda.compose.typography.caption.CaptionSmallDemiTypographyStyle
import co.app.food.andromeda.compose.typography.title.*

/**
 * Contains all the typography we provide for our components.
 *
 * */
class AndromedaComposeTypography(
    val titleHeroTextStyle: TextStyle,
    val titleModerateBoldTextStyle: TextStyle,
    val titleModerateDemiTextStyle: TextStyle,
    val titleSmallDemiTextStyle: TextStyle,
    val bodyModerateDefaultTypographyStyle: TextStyle,
    val bodySmallDefaultTypographyStyle: TextStyle,
    val captionModerateBookDefaultTypographyStyle: TextStyle,
    val captionModerateDemiDefaultTypographyStyle: TextStyle
)

/**
 * Builds the default typography set for our theme.
 * */
@Composable
fun textStyles(isDarkTheme: Boolean): AndromedaComposeTypography {
    return AndromedaComposeTypography(
        titleHeroTextStyle = TitleHeroTypographyStyle().getComposeTextStyle(),
        titleModerateBoldTextStyle = TitleModerateBoldTypographyStyle().getComposeTextStyle(),
        titleModerateDemiTextStyle = TitleModerateDemiTypographyStyle().getComposeTextStyle(),
        titleSmallDemiTextStyle = TitleSmallDemiTypographyStyle().getComposeTextStyle(),
        bodyModerateDefaultTypographyStyle = BodyModerateTypographyStyle().getComposeTextStyle(),
        bodySmallDefaultTypographyStyle = BodySmallTypographyStyle().getComposeTextStyle(),
        captionModerateBookDefaultTypographyStyle = CaptionModerateBookTypographyStyle()
            .getComposeTextStyle(),
        captionModerateDemiDefaultTypographyStyle = CaptionModerateDemiTypographyStyle()
            .getComposeTextStyle()
    )
}

private fun toTextStyle(typographyStyle: BaseTypography): TextStyle {
    return TextStyle(
        fontSize = typographyStyle.fontSize,
        fontFamily = typographyStyle.fontFamily,
        lineHeight = typographyStyle.lineHeight,
        fontWeight = typographyStyle.fontWeight
    )
}

fun BaseTypography.getComposeTextStyle(): TextStyle {
    return toTextStyle(this)
}

/** To be removed later
 *
 */
@Composable
fun defaultTextStyles(colors: AndromedaComposeColors): List<TextStyle> {
    val titleHeroDefaultTypographyStyle: TitleHeroTypographyStyle =
        TitleHeroTypographyStyle()
    val titleHeroActiveTypographyStyle: TitleHeroTypographyStyle =
        TitleHeroTypographyStyle()
    val titleHeroInActiveTypographyStyle: TitleHeroTypographyStyle =
        TitleHeroTypographyStyle()
    val titleHeroErrorTypographyStyle: TitleHeroTypographyStyle =
        TitleHeroTypographyStyle()
    val titleHeroStaticWhiteTypographyStyle: TitleHeroTypographyStyle =
        TitleHeroTypographyStyle()
    val titleHeroInvertedTypographyStyle: TitleHeroTypographyStyle =
        TitleHeroTypographyStyle()
    val titleExtraLargeDefaultTypographyStyle: TitleExtraLargeTypographyStyle =
        TitleExtraLargeTypographyStyle()
    val titleExtraLargeActiveTypographyStyle: TitleExtraLargeTypographyStyle =
        TitleExtraLargeTypographyStyle()
    val titleExtraLargeInActiveTypographyStyle: TitleExtraLargeTypographyStyle =
        TitleExtraLargeTypographyStyle()
    val titleExtraLargeErrorTypographyStyle: TitleExtraLargeTypographyStyle =
        TitleExtraLargeTypographyStyle()
    val titleExtraLargeStaticWhiteTypographyStyle: TitleExtraLargeTypographyStyle =
        TitleExtraLargeTypographyStyle()
    val titleExtraLargeInvertedTypographyStyle: TitleExtraLargeTypographyStyle =
        TitleExtraLargeTypographyStyle()
    val titleLargeDefaultTypographyStyle: TitleLargeTypographyStyle =
        TitleLargeTypographyStyle()
    val titleLargeActiveTypographyStyle: TitleLargeTypographyStyle =
        TitleLargeTypographyStyle()
    val titleLargeInActiveTypographyStyle: TitleLargeTypographyStyle =
        TitleLargeTypographyStyle()
    val titleLargeErrorTypographyStyle: TitleLargeTypographyStyle =
        TitleLargeTypographyStyle()
    val titleLargeStaticWhiteTypographyStyle: TitleLargeTypographyStyle =
        TitleLargeTypographyStyle()
    val titleLargeInvertedTypographyStyle: TitleLargeTypographyStyle =
        TitleLargeTypographyStyle()
    val titleModerateBoldDefaultTypographyStyle: TitleModerateBoldTypographyStyle =
        TitleModerateBoldTypographyStyle()
    val titleModerateBoldActiveTypographyStyle: TitleModerateBoldTypographyStyle =
        TitleModerateBoldTypographyStyle()
    val titleModerateBoldInActiveTypographyStyle: TitleModerateBoldTypographyStyle =
        TitleModerateBoldTypographyStyle()
    val titleModerateBoldErrorTypographyStyle: TitleModerateBoldTypographyStyle =
        TitleModerateBoldTypographyStyle()
    val titleModerateBoldStaticWhiteTypographyStyle: TitleModerateBoldTypographyStyle =
        TitleModerateBoldTypographyStyle()
    val titleModerateBoldInvertedTypographyStyle: TitleModerateBoldTypographyStyle =
        TitleModerateBoldTypographyStyle()
    val titleModerateDemiDefaultTypographyStyle: TitleModerateDemiTypographyStyle =
        TitleModerateDemiTypographyStyle()
    val titleModerateDemiActiveTypographyStyle: TitleModerateDemiTypographyStyle =
        TitleModerateDemiTypographyStyle()
    val titleModerateDemiInActiveTypographyStyle: TitleModerateDemiTypographyStyle =
        TitleModerateDemiTypographyStyle()
    val titleModerateDemiErrorTypographyStyle: TitleModerateDemiTypographyStyle =
        TitleModerateDemiTypographyStyle()
    val titleModerateDemiStaticWhiteTypographyStyle: TitleModerateDemiTypographyStyle =
        TitleModerateDemiTypographyStyle()
    val titleModerateDemiInvertedTypographyStyle: TitleModerateDemiTypographyStyle =
        TitleModerateDemiTypographyStyle()
    val titleSmallBoldDefaultTypographyStyle: TitleSmallBoldTypographyStyle =
        TitleSmallBoldTypographyStyle()
    val titleSmallBoldActiveTypographyStyle: TitleSmallBoldTypographyStyle =
        TitleSmallBoldTypographyStyle()
    val titleSmallBoldInActiveTypographyStyle: TitleSmallBoldTypographyStyle =
        TitleSmallBoldTypographyStyle()
    val titleSmallBoldErrorTypographyStyle: TitleSmallBoldTypographyStyle =
        TitleSmallBoldTypographyStyle()
    val titleSmallBoldStaticWhiteTypographyStyle: TitleSmallBoldTypographyStyle =
        TitleSmallBoldTypographyStyle()
    val titleSmallBoldInvertedTypographyStyle: TitleSmallBoldTypographyStyle =
        TitleSmallBoldTypographyStyle()
    val titleSmallDemiDefaultTypographyStyle: TitleSmallDemiTypographyStyle =
        TitleSmallDemiTypographyStyle()
    val titleSmallDemiActiveTypographyStyle: TitleSmallDemiTypographyStyle =
        TitleSmallDemiTypographyStyle()
    val titleSmallDemiInActiveTypographyStyle: TitleSmallDemiTypographyStyle =
        TitleSmallDemiTypographyStyle()
    val titleSmallDemiErrorTypographyStyle: TitleSmallDemiTypographyStyle =
        TitleSmallDemiTypographyStyle()
    val titleSmallDemiStaticWhiteTypographyStyle: TitleSmallDemiTypographyStyle =
        TitleSmallDemiTypographyStyle()
    val titleSmallDemiInvertedTypographyStyle: TitleSmallDemiTypographyStyle =
        TitleSmallDemiTypographyStyle()
    val titleTinyBoldDefaultTypographyStyle: TitleTinyBoldTypographyStyle =
        TitleTinyBoldTypographyStyle()
    val titleTinyBoldActiveTypographyStyle: TitleTinyBoldTypographyStyle =
        TitleTinyBoldTypographyStyle()
    val titleTinyBoldInActiveTypographyStyle: TitleTinyBoldTypographyStyle =
        TitleTinyBoldTypographyStyle()
    val titleTinyBoldErrorTypographyStyle: TitleTinyBoldTypographyStyle =
        TitleTinyBoldTypographyStyle()
    val titleTinyBoldStaticWhiteTypographyStyle: TitleTinyBoldTypographyStyle =
        TitleTinyBoldTypographyStyle()
    val titleTinyBoldInvertedTypographyStyle: TitleTinyBoldTypographyStyle =
        TitleTinyBoldTypographyStyle()
    val titleTinyDemiDefaultTypographyStyle: TitleTinyDemiTypographyStyle =
        TitleTinyDemiTypographyStyle()
    val titleTinyDemiActiveTypographyStyle: TitleTinyDemiTypographyStyle =
        TitleTinyDemiTypographyStyle()
    val titleTinyDemiInActiveTypographyStyle: TitleTinyDemiTypographyStyle =
        TitleTinyDemiTypographyStyle()
    val titleTinyDemiErrorTypographyStyle: TitleTinyDemiTypographyStyle =
        TitleTinyDemiTypographyStyle()
    val titleTinyDemiStaticWhiteTypographyStyle: TitleTinyDemiTypographyStyle =
        TitleTinyDemiTypographyStyle()
    val titleTinyDemiInvertedTypographyStyle: TitleTinyDemiTypographyStyle =
        TitleTinyDemiTypographyStyle()
    val bodyModerateDefaultTypographyStyle: BodyModerateTypographyStyle =
        BodyModerateTypographyStyle()
    val bodyModerateActiveTypographyStyle: BodyModerateTypographyStyle =
        BodyModerateTypographyStyle()
    val bodyModerateInActiveTypographyStyle: BodyModerateTypographyStyle =
        BodyModerateTypographyStyle()
    val bodyModerateErrorTypographyStyle: BodyModerateTypographyStyle =
        BodyModerateTypographyStyle()
    val bodyModerateStaticWhiteTypographyStyle: BodyModerateTypographyStyle =
        BodyModerateTypographyStyle()
    val bodyModerateInvertedTypographyStyle: BodyModerateTypographyStyle =
        BodyModerateTypographyStyle()
    val bodySmallDefaultTypographyStyle: BodySmallTypographyStyle =
        BodySmallTypographyStyle()
    val bodySmallActiveTypographyStyle: BodySmallTypographyStyle =
        BodySmallTypographyStyle()
    val bodySmallInActiveTypographyStyle: BodySmallTypographyStyle =
        BodySmallTypographyStyle()
    val bodySmallErrorTypographyStyle: BodySmallTypographyStyle =
        BodySmallTypographyStyle()
    val bodySmallStaticWhiteTypographyStyle: BodySmallTypographyStyle =
        BodySmallTypographyStyle()
    val bodySmallInvertedTypographyStyle: BodySmallTypographyStyle =
        BodySmallTypographyStyle()
    val captionModerateBookDefaultTypographyStyle: CaptionModerateBookTypographyStyle =
        CaptionModerateBookTypographyStyle()
    val captionModerateBookActiveTypographyStyle: CaptionModerateBookTypographyStyle =
        CaptionModerateBookTypographyStyle()
    val captionModerateBookInActiveTypographyStyle: CaptionModerateBookTypographyStyle =
        CaptionModerateBookTypographyStyle()
    val captionModerateBookErrorTypographyStyle: CaptionModerateBookTypographyStyle =
        CaptionModerateBookTypographyStyle()
    val captionModerateBookStaticWhiteTypographyStyle: CaptionModerateBookTypographyStyle =
        CaptionModerateBookTypographyStyle()
    val captionModerateBookInvertedTypographyStyle: CaptionModerateBookTypographyStyle =
        CaptionModerateBookTypographyStyle()
    val captionModerateDemiDefaultTypographyStyle: CaptionModerateDemiTypographyStyle =
        CaptionModerateDemiTypographyStyle()
    val captionModerateDemiActiveTypographyStyle: CaptionModerateDemiTypographyStyle =
        CaptionModerateDemiTypographyStyle()
    val captionModerateDemiInActiveTypographyStyle: CaptionModerateDemiTypographyStyle =
        CaptionModerateDemiTypographyStyle()
    val captionModerateDemiErrorTypographyStyle: CaptionModerateDemiTypographyStyle =
        CaptionModerateDemiTypographyStyle()
    val captionModerateDemiStaticWhiteTypographyStyle: CaptionModerateDemiTypographyStyle =
        CaptionModerateDemiTypographyStyle()
    val captionModerateDemiInvertedTypographyStyle: CaptionModerateDemiTypographyStyle =
        CaptionModerateDemiTypographyStyle()
    val captionSmallBookDefaultTypographyStyle: CaptionSmallBookTypographyStyle =
        CaptionSmallBookTypographyStyle()
    val captionSmallBookActiveTypographyStyle: CaptionSmallBookTypographyStyle =
        CaptionSmallBookTypographyStyle()
    val captionSmallBookInActiveTypographyStyle: CaptionSmallBookTypographyStyle =
        CaptionSmallBookTypographyStyle()
    val captionSmallBookErrorTypographyStyle: CaptionSmallBookTypographyStyle =
        CaptionSmallBookTypographyStyle()
    val captionSmallBookStaticWhiteTypographyStyle: CaptionSmallBookTypographyStyle =
        CaptionSmallBookTypographyStyle()
    val captionSmallBookInvertedTypographyStyle: CaptionSmallBookTypographyStyle =
        CaptionSmallBookTypographyStyle()
    val captionSmallDemiDefaultTypographyStyle: CaptionSmallDemiTypographyStyle =
        CaptionSmallDemiTypographyStyle()
    val captionSmallDemiActiveTypographyStyle: CaptionSmallDemiTypographyStyle =
        CaptionSmallDemiTypographyStyle()
    val captionSmallDemiInActiveTypographyStyle: CaptionSmallDemiTypographyStyle =
        CaptionSmallDemiTypographyStyle()
    val captionSmallDemiErrorTypographyStyle: CaptionSmallDemiTypographyStyle =
        CaptionSmallDemiTypographyStyle()
    val captionSmallDemiStaticWhiteTypographyStyle: CaptionSmallDemiTypographyStyle =
        CaptionSmallDemiTypographyStyle()
    val captionSmallDemiInvertedTypographyStyle: CaptionSmallDemiTypographyStyle =
        CaptionSmallDemiTypographyStyle()

    return listOf(
        titleHeroDefaultTypographyStyle,
        titleHeroActiveTypographyStyle,
        titleHeroInActiveTypographyStyle,
        titleHeroErrorTypographyStyle,
        titleHeroStaticWhiteTypographyStyle,
        titleHeroInvertedTypographyStyle,

        titleExtraLargeDefaultTypographyStyle,
        titleExtraLargeActiveTypographyStyle,
        titleExtraLargeInActiveTypographyStyle,
        titleExtraLargeErrorTypographyStyle,
        titleExtraLargeStaticWhiteTypographyStyle,
        titleExtraLargeInvertedTypographyStyle,

        titleLargeDefaultTypographyStyle,
        titleLargeActiveTypographyStyle,
        titleLargeInActiveTypographyStyle,
        titleLargeErrorTypographyStyle,
        titleLargeStaticWhiteTypographyStyle,
        titleLargeInvertedTypographyStyle,

        titleModerateBoldDefaultTypographyStyle,
        titleModerateBoldActiveTypographyStyle,
        titleModerateBoldInActiveTypographyStyle,
        titleModerateBoldErrorTypographyStyle,
        titleModerateBoldStaticWhiteTypographyStyle,
        titleModerateBoldInvertedTypographyStyle,

        titleModerateDemiDefaultTypographyStyle,
        titleModerateDemiActiveTypographyStyle,
        titleModerateDemiInActiveTypographyStyle,
        titleModerateDemiErrorTypographyStyle,
        titleModerateDemiStaticWhiteTypographyStyle,
        titleModerateDemiInvertedTypographyStyle,

        titleSmallBoldDefaultTypographyStyle,
        titleSmallBoldActiveTypographyStyle,
        titleSmallBoldInActiveTypographyStyle,
        titleSmallBoldErrorTypographyStyle,
        titleSmallBoldStaticWhiteTypographyStyle,
        titleSmallBoldInvertedTypographyStyle,

        titleSmallDemiDefaultTypographyStyle,
        titleSmallDemiActiveTypographyStyle,
        titleSmallDemiInActiveTypographyStyle,
        titleSmallDemiErrorTypographyStyle,
        titleSmallDemiStaticWhiteTypographyStyle,
        titleSmallDemiInvertedTypographyStyle,

        titleTinyBoldDefaultTypographyStyle,
        titleTinyBoldActiveTypographyStyle,
        titleTinyBoldInActiveTypographyStyle,
        titleTinyBoldErrorTypographyStyle,
        titleTinyBoldStaticWhiteTypographyStyle,
        titleTinyBoldInvertedTypographyStyle,

        titleTinyDemiDefaultTypographyStyle,
        titleTinyDemiActiveTypographyStyle,
        titleTinyDemiInActiveTypographyStyle,
        titleTinyDemiErrorTypographyStyle,
        titleTinyDemiStaticWhiteTypographyStyle,
        titleTinyDemiInvertedTypographyStyle,

        bodyModerateDefaultTypographyStyle,
        bodyModerateActiveTypographyStyle,
        bodyModerateInActiveTypographyStyle,
        bodyModerateErrorTypographyStyle,
        bodyModerateStaticWhiteTypographyStyle,
        bodyModerateInvertedTypographyStyle,

        bodySmallDefaultTypographyStyle,
        bodySmallActiveTypographyStyle,
        bodySmallInActiveTypographyStyle,
        bodySmallErrorTypographyStyle,
        bodySmallStaticWhiteTypographyStyle,
        bodySmallInvertedTypographyStyle,

        captionModerateBookDefaultTypographyStyle,
        captionModerateBookActiveTypographyStyle,
        captionModerateBookInActiveTypographyStyle,
        captionModerateBookErrorTypographyStyle,
        captionModerateBookStaticWhiteTypographyStyle,
        captionModerateBookInvertedTypographyStyle,

        captionModerateDemiDefaultTypographyStyle,
        captionModerateDemiActiveTypographyStyle,
        captionModerateDemiInActiveTypographyStyle,
        captionModerateDemiErrorTypographyStyle,
        captionModerateDemiStaticWhiteTypographyStyle,
        captionModerateDemiInvertedTypographyStyle,

        captionSmallBookDefaultTypographyStyle,
        captionSmallBookActiveTypographyStyle,
        captionSmallBookInActiveTypographyStyle,
        captionSmallBookErrorTypographyStyle,
        captionSmallBookStaticWhiteTypographyStyle,
        captionSmallBookInvertedTypographyStyle,

        captionSmallDemiDefaultTypographyStyle,
        captionSmallDemiActiveTypographyStyle,
        captionSmallDemiInActiveTypographyStyle,
        captionSmallDemiErrorTypographyStyle,
        captionSmallDemiStaticWhiteTypographyStyle,
        captionSmallDemiInvertedTypographyStyle,
    ).map { typographyStyle ->
        TextStyle(
            fontSize = typographyStyle.fontSize,
            fontFamily = typographyStyle.fontFamily,
            lineHeight = typographyStyle.lineHeight,
            fontWeight = typographyStyle.fontWeight
        )
    }
}
