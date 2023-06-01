package co.app.food.landing.presentation.components

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import co.app.food.andromeda.compose.AndromedaComposeTheme
import co.app.food.landing.R

@Composable
fun BottomBar(modifier: Modifier = Modifier) {
    BottomNavigation(
        modifier = modifier,
        backgroundColor = Color.White,
        elevation = 8.dp,
        contentColor = AndromedaComposeTheme.colors.fillActivePrimary
    ) {
        BottomNavigationItem(
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.andromeda_icon_today),
                    contentDescription = ""
                )
            },
            selectedContentColor = AndromedaComposeTheme.colors.fillActivePrimary,
            unselectedContentColor = Color.Gray,
            label = { Text("Today") },
            selected = true,
            onClick = {
            }
        )
        BottomNavigationItem(
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.andromeda_icon_collections),
                    contentDescription = ""
                )
            },
            selectedContentColor = AndromedaComposeTheme.colors.fillActivePrimary,
            unselectedContentColor = Color.Gray,
            label = { Text("My") },
            selected = false,
            onClick = {
            }
        )
        BottomNavigationItem(
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.andromeda_icon_bell),
                    contentDescription = ""
                )
            },
            selectedContentColor = AndromedaComposeTheme.colors.fillActivePrimary,
            unselectedContentColor = Color.Gray,
            label = { Text("Alerts") },
            selected = false,
            onClick = {
            }
        )
        BottomNavigationItem(
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.andromeda_icon_more),
                    contentDescription = ""
                )
            },
            selectedContentColor = AndromedaComposeTheme.colors.fillActivePrimary,
            unselectedContentColor = Color.Gray,
            label = { Text("More") },
            selected = false,
            onClick = {
            }
        )
    }
}
