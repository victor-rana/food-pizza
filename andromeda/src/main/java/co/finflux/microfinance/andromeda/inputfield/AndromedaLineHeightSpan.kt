package co.app.food.andromeda.inputfield

import android.graphics.Paint
import android.text.style.LineHeightSpan
import kotlin.math.ceil
import kotlin.math.floor

internal class AndromedaLineHeightSpan(
    private val height: Int,
    private val textSize: Int
) : LineHeightSpan {

    override fun chooseHeight(
        text: CharSequence?,
        start: Int,
        end: Int,
        spanstartv: Int,
        lineHeight: Int,
        fm: Paint.FontMetricsInt
    ) {
        if (textSize < height) {
            fm.descent = fm.ascent.plus(textSize)
            fm.top = floor(fm.ascent.minus((height - textSize).div(2f))).toInt()
            fm.bottom = ceil(fm.descent.plus((height - textSize).div(2f))).toInt()
            fm.leading = 0
        }
    }
}
