package co.app.food.andromeda.inputfield

import android.annotation.SuppressLint
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.StyleRes
import androidx.core.content.ContextCompat
import androidx.core.widget.TextViewCompat
import co.app.food.andromeda.extensions.toPxInt
import co.app.food.andromeda.extensions.tint

@SuppressLint("DiscouragedPrivateApi")
internal fun EditText.updateCursorColor(color: Int) {

    if (Build.VERSION.SDK_INT >= 29) {
        val gradientDrawable =
            GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP, intArrayOf(color, color))
        gradientDrawable.setSize(2f.toPxInt(context), textSize.toInt())
        textCursorDrawable = gradientDrawable
        return
    }

    try {
        val editorField = try {
            TextView::class.java.getDeclaredField("mEditor").apply { isAccessible = true }
        } catch (e: Exception) {
            null
        }
        val editor = editorField?.get(this) ?: this
        val editorClass: Class<*> =
            if (editorField == null) TextView::class.java else editor.javaClass
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val tinted = textCursorDrawable?.tint(color)
            tinted?.let {
                textCursorDrawable = it
            }
        } else {
            val tintedCursorDrawable = TextView::class.java.getDeclaredField("mCursorDrawableRes")
                .apply { isAccessible = true }
                .getInt(this)
                .let { ContextCompat.getDrawable(context, it) ?: return }
                .apply { tint(color) }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                editorClass
                    .getDeclaredField("mDrawableForCursor")
                    .apply { isAccessible = true }
                    .run { set(editor, tintedCursorDrawable) }
            } else {
                editorClass
                    .getDeclaredField("mCursorDrawable")
                    .apply { isAccessible = true }
                    .run { set(editor, arrayOf(tintedCursorDrawable, tintedCursorDrawable)) }
            }
        }
    } catch (e: Exception) {
    }
}

internal fun EditText.setStyle(@StyleRes style: Int) {
    TextViewCompat.setTextAppearance(this, style)
    val attrs = intArrayOf(android.R.attr.lineSpacingExtra)
    val ta = this.context.theme.obtainStyledAttributes(style, attrs)
    this.setLineSpacing(ta.getDimension(0, this.lineSpacingExtra), 0f)
    ta.recycle()
}

internal fun EditText.setTextIfDifferent(newText: CharSequence?): Boolean {
    if (!isTextDifferent(newText, text)) {
        // Previous text is the same. No op
        return false
    }
    if (newText == null)
        setText("")
    else
        setText(newText)
    // Since the text changed we move the cursor to the end of the new text.
    // This allows us to fill in text programmatically with a different value,
    // but if the user is typing and the view is rebound we won't lose their cursor position.
    newText?.let {
        if (it.length <= text.length) {
            setSelection(it.length)
        }
    }
    return true
}

private fun isTextDifferent(str1: CharSequence?, str2: CharSequence?): Boolean =
    when {
        str1 === str2 -> false
        str1 == null || str2 == null -> true
        str1.length != str2.length -> true
        else ->
            str1.toString() != str2.toString() // Needed in case either string is a Spannable
    }
