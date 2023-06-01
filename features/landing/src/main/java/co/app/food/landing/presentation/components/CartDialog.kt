package co.app.food.landing.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import co.app.food.andromeda.compose.AndromedaComposeTheme
import co.app.food.landing.R
import co.app.food.landing.model.CrustsItem

@Composable
fun CartDialog(
    cartItems: List<CrustsItem>,
    setShowCartDialog: (Boolean) -> Unit = {},
    onRemoveItem: (CrustsItem) -> Unit = {},
) {
    Dialog(
        onDismissRequest = {
            setShowCartDialog(false)
        }
    ) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = Color.White
        ) {
            LazyColumn(modifier = Modifier.fillMaxWidth().padding(10.dp)) {
                items(cartItems) { cartItems ->
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "${cartItems.sizes.size} ${cartItems.name}",
                            style = AndromedaComposeTheme.typography.titleModerateBoldTextStyle,
                        )
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_delete_forever_24),
                            contentDescription = "",
                            tint = Color.Red,
                            modifier = Modifier
                                .clickable { onRemoveItem(cartItems) }
                        )
                    }
                }
            }
        }
    }
}
