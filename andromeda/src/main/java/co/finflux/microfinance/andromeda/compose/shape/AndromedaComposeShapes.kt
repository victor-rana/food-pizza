package co.app.food.andromeda.compose.shape

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

/**
 * Contains all the shapes we provide for our components.
 *
 * @param avatar - The avatar shape.
 * @param imageThumbnail - The shape of image thumbnails, shown in selected attachments and image file attachments.
 * @param bottomSheet - The shape of components used as bottom sheets.
 * */
public class AndromedaComposeShapes(
    public val avatar: Shape,
    public val imageThumbnail: Shape,
    public val bottomSheet: Shape,
    public val priceInputField: Shape
) {
    public companion object {
        public val default: AndromedaComposeShapes = AndromedaComposeShapes(
            avatar = CircleShape,
            imageThumbnail = RoundedCornerShape(8.dp),
            bottomSheet = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
            priceInputField = RoundedCornerShape(4.dp)
        )
    }
}
