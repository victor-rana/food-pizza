package co.app.food.andromeda.divider.internal

import android.content.Context
import android.graphics.Paint
import android.graphics.Paint.ANTI_ALIAS_FLAG
import co.app.food.andromeda.extensions.toPxInt
import co.app.food.andromeda.tokens.fill_mute_primary
import co.app.food.andromeda.tokens.fill_mute_secondary

internal class BigDivider(context: Context) : Divider {

    override val height = 8f.toPxInt(context)

    override val dividerLineHeight = 1f.toPxInt(context)

    override val paint = Paint(ANTI_ALIAS_FLAG).apply {
        color = context.fill_mute_primary
        style = Paint.Style.STROKE
        strokeWidth = dividerLineHeight.toFloat()
    }

    override val backgroundPaint = Paint(ANTI_ALIAS_FLAG).apply {
        color = context.fill_mute_secondary
    }
}
