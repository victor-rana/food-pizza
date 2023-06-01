package co.app.food.andromeda.compose.navbar

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import co.app.food.andromeda.compose.AndromedaComposeTheme

@Composable
fun AndromedaNavBar(
    backgroundColor: Color,
    modifier: Modifier = Modifier,
    elevation: Dp = NavBarDefaultElevation,
    title: String = "",
    subTitle: String = "",
    contentPadding: PaddingValues = ContentPadding,
    barHeight: Dp = NavBarHeight,
    shape: Shape = RectangleShape,
    navigationIcon: @Composable (() -> Unit)? = null,
    titleView: @Composable RowScope.(Modifier) -> Unit = { modifier ->
        DefaultAndromedaNavBarTitle(
            modifier = modifier,
            title = title,
            subTitle = subTitle
        )
    },
    menuView: @Composable RowScope.(Modifier) -> Unit = { DefaultAndromedaNavBarMenu(it) },
) {
    Surface(
        color = backgroundColor,
        elevation = elevation,
        shape = shape,
        modifier = modifier
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(contentPadding)
                .height(barHeight),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            content = {
                if (navigationIcon == null) {
                    Spacer(TitleInsetWithoutIcon)
                } else {
                    Row(TitleIconModifier, verticalAlignment = Alignment.CenterVertically) {
                        CompositionLocalProvider(
                            LocalContentAlpha provides ContentAlpha.high,
                            content = navigationIcon
                        )
                    }
                }
                titleView(Modifier.fillMaxWidth())
                Spacer(modifier = Modifier.weight(1f))
                menuView(Modifier)
            }
        )
    }
}

@Preview
@Composable
fun AndromedaNavbarPreview() {
    AndromedaComposeTheme {
        AndromedaNavBar(backgroundColor = AndromedaComposeTheme.colors.fillInActivePrimary)
    }
}

@Composable
fun DefaultAndromedaNavBarNavigationBack(onBackClick: () -> Unit) {
    IconButton(onClick = onBackClick) {
        Icon(
            imageVector = Icons.Filled.ArrowBack,
            tint = AndromedaComposeTheme.colors.iconDynamicDefault,
            contentDescription = "" // TODO
        )
    }
}

@Composable
fun DefaultAndromedaNavBarTitle(
    title: String,
    subTitle: String,
    modifier: Modifier = Modifier
) {
    Column(modifier) {
        Text(
            text = title,
            style = AndromedaComposeTheme.typography.titleModerateBoldTextStyle,
            color = AndromedaComposeTheme.colors.defaultFontColor
        )
        Text(
            text = subTitle,
            style = AndromedaComposeTheme.typography.titleSmallDemiTextStyle,
            color = AndromedaComposeTheme.colors.inActiveFontColor
        )
    }
}

@Preview
@Composable
fun DefaultAndromedaNavBarTitlePreview() {
    AndromedaComposeTheme {
        DefaultAndromedaNavBarTitle("Heelo world", "i am subtitle")
    }
}

@Composable
fun DefaultAndromedaNavBarMenu(modifier: Modifier) {
}

val ContentPadding = PaddingValues(
    start = 4.dp,
    end = 4.dp
)
val NavBarNoElevation = 0.dp
private val NavBarDefaultElevation = 4.dp
private val NavBarHeight = 56.dp
private val NavBarHorizontalPadding = 4.dp

// Start inset for the title when there is no navigation icon provided
private val TitleInsetWithoutIcon = Modifier.width(16.dp - NavBarHorizontalPadding)

// Start inset for the title when there is a navigation icon provided
private val TitleIconModifier = Modifier
    .fillMaxHeight()
    .width(72.dp - NavBarHorizontalPadding)
