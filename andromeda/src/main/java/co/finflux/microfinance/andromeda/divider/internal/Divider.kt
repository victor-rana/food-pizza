package co.app.food.andromeda.divider.internal

import android.graphics.Paint

internal interface Divider {

    val height: Int

    val dividerLineHeight: Int

    val paint: Paint

    val backgroundPaint: Paint?
}
