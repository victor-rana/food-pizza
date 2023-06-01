package co.app.food.landing.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import co.app.food.andromeda.compose.AndromedaComposeTheme
import co.app.food.landing.model.CrustsItem
import co.app.food.landing.model.FoodItemDetailsResponse
import co.app.food.landing.model.Sizes

@Composable
fun CrustsDialog(
    itemsData: FoodItemDetailsResponse,
    setShowDialog: (Boolean) -> Unit = {},
    addItem: (CrustsItem) -> Unit = {}
) {
    val selectedIndexCrustsItem = remember {
        mutableStateOf(1)
    }
    val selectedIndexCrustsSizes = remember {
        mutableStateOf(1)
    }
    val crustsSizes = remember { mutableStateListOf<Sizes>() }
    if (crustsSizes.isEmpty() && itemsData.crusts.isNotEmpty()) {
        crustsSizes.addAll(itemsData.crusts[0].sizes)
    }
    Dialog(
        onDismissRequest = {
            setShowDialog(false)
        }
    ) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = Color.White
        ) {
            Column(modifier = Modifier.padding(10.dp)) {
                Text(
                    text = "Crusts",
                    style = AndromedaComposeTheme.typography.titleModerateBoldTextStyle,
                )
                LazyColumn(modifier = Modifier.fillMaxWidth().padding(10.dp)) {
                    items(itemsData.crusts) { crusts ->
                        Text(
                            text = crusts.name,
                            style = AndromedaComposeTheme.typography.bodyModerateDefaultTypographyStyle,
                            modifier = Modifier
                                .fillMaxWidth()
                                .selectable(
                                    selected = selectedIndexCrustsItem.value == crusts.foodItemId,
                                    onClick = {
                                        selectedIndexCrustsItem.value = crusts.foodItemId
                                        crustsSizes.clear()
                                        crustsSizes.addAll(crusts.sizes)
                                    }
                                )
                                .background(
                                    if (crusts.foodItemId == selectedIndexCrustsItem.value)
                                        Color.Gray
                                    else Color.Transparent
                                )
                        )
                    }
                }
                Spacer(modifier = Modifier.size(20.dp))
                Text(
                    text = "Sizes",
                    style = AndromedaComposeTheme.typography.titleModerateBoldTextStyle,
                )
                LazyColumn(modifier = Modifier.fillMaxWidth().padding(10.dp).height(100.dp)) {
                    items(crustsSizes) { size ->
                        size.name?.let {
                            Card(
                                shape = RoundedCornerShape(16.dp),
                                modifier = Modifier
                                    .padding(2.dp)
                                    .clip(RoundedCornerShape(16.dp))
                                    .fillMaxWidth().selectable(
                                        selected = selectedIndexCrustsSizes.value == size.id,
                                        onClick = {
                                            selectedIndexCrustsSizes.value = size.id!!
                                        }
                                    ),
                                backgroundColor = if (size.id == selectedIndexCrustsSizes.value)
                                    Color.Gray
                                else Color.Transparent
                            ) {
                                Row(horizontalArrangement = Arrangement.SpaceEvenly) {
                                    Text(
                                        text = it,
                                        style = AndromedaComposeTheme.typography.bodyModerateDefaultTypographyStyle,
                                    )
                                    Text(
                                        text = "₹" + size.price.toString(),
                                        style = AndromedaComposeTheme.typography.bodyModerateDefaultTypographyStyle,
                                    )
                                }
                            }
                        }
                    }
                }
                Button(
                    onClick = {
                        val sizes: ArrayList<Sizes> = ArrayList()
                        sizes.add(crustsSizes[selectedIndexCrustsSizes.value - 1])
                        addItem(
                            CrustsItem(
                                foodItemId = itemsData.crusts[selectedIndexCrustsItem.value - 1].foodItemId,
                                name = itemsData.crusts[selectedIndexCrustsItem.value - 1].name,
                                defaultSize = itemsData.crusts[selectedIndexCrustsItem.value - 1].defaultSize,
                                sizes = sizes
                            )
                        )
                        setShowDialog(false)
                    },
                    colors = AndromedaComposeTheme.buttonColors,
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                        .height(48.dp),
                    enabled = selectedIndexCrustsSizes.value != -1 && selectedIndexCrustsItem.value != 0
                ) {
                    Text(
                        text = "Add to cart",
                        modifier = Modifier
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        color = AndromedaComposeTheme.colors.staticWhiteFontColor
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewCrustsDialog() {
    CrustsDialog(
        itemsData = FoodItemDetailsResponse(
            id = "1",
            name = "Non-Veg Pizza",
            isVeg = false,
            description = "",
            defaultCrust = 1,
            crusts = emptyList(),
        )
    )
}
