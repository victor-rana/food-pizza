package co.app.food.andromeda.divider.internal

import android.content.Context
import android.graphics.Paint
import android.graphics.Paint.ANTI_ALIAS_FLAG
import co.app.food.andromeda.extensions.toPxInt
import co.app.food.andromeda.tokens.fill_mute_primary

internal class PlainDivider(context: Context) : Divider {

    override val height = 1f.toPxInt(context)

    override val dividerLineHeight = height

    override val paint = Paint(ANTI_ALIAS_FLAG).apply {
        color = context.fill_mute_primary
        style = Paint.Style.STROKE
        strokeWidth = height.toFloat()
    }

    override val backgroundPaint: Paint? = null
}
