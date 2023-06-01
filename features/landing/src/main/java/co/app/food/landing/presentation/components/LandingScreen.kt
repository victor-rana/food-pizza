package co.app.food.landing.presentation.components

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import co.app.food.andromeda.compose.AndromedaComposeTheme
import co.app.food.andromeda.compose.navbar.AndromedaNavBar
import co.app.food.landing.R
import co.app.food.landing.domain.LandingViewModel
import co.app.food.landing.model.FoodItemDetailsResponse
import co.app.food.landing.model.LandingScreenState
import java.util.*

@Composable
fun LandingScreen() {
    val viewModel: LandingViewModel = viewModel()
    val uiState = viewModel.landingState
    val showDialog = remember { mutableStateOf(false) }
    val showCartDialog = remember { mutableStateOf(false) }

    if (showDialog.value) {
        CrustsDialog(
            itemsData = uiState.foodItems,
            setShowDialog = {
                showDialog.value = it
            },
            addItem = {
                viewModel.insertItemCart(it)
            }
        )
    }
    if (showCartDialog.value) {
        CartDialog(
            cartItems = uiState.cartItems,
            setShowCartDialog = {
                showCartDialog.value = it
            },
            onRemoveItem = {
                viewModel.removeItemCart(it)
                showCartDialog.value = false
            }
        )
    }
    Scaffold(
        topBar = {
            AndromedaNavBar(
                backgroundColor = AndromedaComposeTheme.colors.fillActivePrimary,
            )
        }
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxSize()
        ) {
            LazyColumn(
                Modifier
                    .fillMaxWidth()
            ) {
                item {
                    ItemCard(
                        itemsData = uiState.foodItems,
                        onClickAddItem = {
                            showDialog.value = true
                        },
                        uiState = uiState
                    )
                }
            }
            Box(
                modifier = Modifier
                    .background(AndromedaComposeTheme.colors.fillActivePrimary)
                    .height(48.dp)
                    .fillMaxWidth()
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = rememberRipple(
                            color = Color.DarkGray
                        )
                    ) { showCartDialog.value = true }
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Cart →",
                        fontWeight = FontWeight.W500,
                        style = AndromedaComposeTheme.typography.titleSmallDemiTextStyle,
                        color = AndromedaComposeTheme.colors.staticWhiteFontColor,
                        modifier = Modifier.padding(5.dp)
                    )
                    Text(
                        text = "₹" + uiState.cartItems.sumOf { cartItems -> cartItems.sizes.sumOf { sizes -> sizes.price!! } },
                        fontWeight = FontWeight.W500,
                        style = AndromedaComposeTheme.typography.titleSmallDemiTextStyle,
                        color = AndromedaComposeTheme.colors.staticWhiteFontColor,
                        modifier = Modifier.padding(5.dp)
                    )
                }
            }
        }
    }
}

@SuppressLint("RememberReturnType")
@Composable
fun ItemCard(
    itemsData: FoodItemDetailsResponse,
    onClickAddItem: () -> Unit = {},
    uiState: LandingScreenState
) {
    val itemColor: Color = if (itemsData.isVeg) {
        Color.Green
    } else {
        Color.Red
    }

    Card(
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, Color.Blue),
        modifier = Modifier
            .padding(10.dp)
            .clip(RoundedCornerShape(16.dp))
            .fillMaxWidth()
            .height(180.dp),
        backgroundColor = Color.White
    ) {
        Row(
            modifier = Modifier.padding(15.dp)
        ) {
            Row(modifier = Modifier.weight(2f)) {
                Icon(
                    painter = painterResource(id = R.drawable.food_identity),
                    tint = itemColor,
                    contentDescription = ""
                )
                Column() {
                    Text(
                        text = itemsData.name,
                        style = AndromedaComposeTheme.typography.titleModerateBoldTextStyle,
                    )
                    Text(
                        text = itemsData.description,
                        style = AndromedaComposeTheme.typography.bodyModerateDefaultTypographyStyle,
                    )
                }
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.weight(1f).fillMaxHeight(),
            ) {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    border = BorderStroke(1.dp, Color.Blue),
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = rememberRipple(
                                color = Color.DarkGray
                            )
                        ) { onClickAddItem() }
                        .fillMaxWidth(),
                    backgroundColor = Color.White
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.andromeda_icon_add),
                        contentDescription = ""
                    )
                }
                Row {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_shopping_cart_24),
                        contentDescription = "",
                        tint = AndromedaComposeTheme.colors.fillActivePrimary
                    )
                    Text(
                        text = uiState.cartItems.size.toString(),
                        style = AndromedaComposeTheme.typography.titleModerateBoldTextStyle,
                    )
                }
            }
        }
    }
}
