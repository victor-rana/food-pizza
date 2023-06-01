package co.app.food.andromeda.pin

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.core.view.updateLayoutParams
import androidx.core.widget.addTextChangedListener
import co.app.food.andromeda.R
import co.app.food.andromeda.databinding.AndromedaPinBoxBinding
import co.app.food.andromeda.databinding.AndromedaPinFieldBinding
import co.app.food.andromeda.extensions.dpToPixels
import co.app.food.andromeda.extensions.readAttributes
import co.app.food.andromeda.toColorToken
import co.app.food.andromeda.Color as AndromedaColor

private const val UNFILLED = -1
private const val MEDIUM_SIZE = 48
private const val SMALL_SIZE = 40

/**
 * Andromeda Andromeda OTP field
 *
 * ```xml
 * <co.app.food.andromeda.pin.AndromedaPinField
 *     android:id="@+id/icon_view"
 *     android:layout_width="wrap_content"
 *     android:layout_height="wrap_content"
 *     app:pinLength=4 />
 * ```
 */
class AndromedaPinField @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleRes: Int = 0
) : FrameLayout(
    context,
    attributeSet,
    defStyleRes
) {
    var text = ""
    private val binding = AndromedaPinFieldBinding.inflate(LayoutInflater.from(context), this, true)
    private var pinLength = 4
    private var pinValue = UNFILLED
    private val blockViews: MutableList<AndromedaPinBoxBinding> = mutableListOf()
    private var otpListener: OtpChangeListener? = null
    private var message = ""

    init {
        readAttributes(attributeSet, R.styleable.AndromedaPinField) {
            pinLength = it.getInt(R.styleable.AndromedaPinField_pin_length, pinLength)
            pinValue = it.getInt(R.styleable.AndromedaPinField_pin_value, pinValue)
        }
        setUpInputFields()
    }

    fun setOtpLength(length: Int) {
        this.pinLength = length
        setUpInputFields()
    }

    fun setOtpText(otp: Int) {
        this.pinValue = otp
        resetOtp()
    }

    fun setFocused(focused: Boolean) {
        if (focused) {
            blockViews.forEach { block ->
                if (block.inputField.text.isEmpty()) {
                    block.inputField.requestFocus()
                    return
                }
            }
        }
    }

    fun reset() {
        text = ""
        blockViews.forEach { blockBinding ->
            blockBinding.inputField.text.clear()
        }
    }

    fun listenToChanges(otpListener: OtpChangeListener) {
        this.otpListener = otpListener
    }

    fun setErrorState(error: String? = null) {
        blockViews.forEach {
            val drawable = it.root.background as GradientDrawable
            drawable.setStroke(
                4,
                AndromedaColor.RED.toColorToken()
            ) // set stroke width and stroke color
        }
        message = error ?: context.getString(R.string.pin_incorrect)
        binding.atvMessage.text = message
    }

    fun clearState() {
        blockViews.forEach {
            val drawable = it.root.background as GradientDrawable
            drawable.setStroke(
                2,
                AndromedaColor.GRAY.toColorToken()
            ) // set stroke width and stroke color
        }
        message = ""
        binding.atvMessage.text = message
    }

    fun setMatchedState() {
        blockViews.forEach {
            val drawable = it.root.background as GradientDrawable
            drawable.setStroke(
                4,
                AndromedaColor.GREEN.toColorToken()
            ) // set stroke width and stroke color
        }
        message = context.getString(R.string.pin_matched)
        binding.atvMessage.text = message
    }

    fun getCurrentText(): String {
        return text
    }

    private fun setUpInputFields() {
        blockViews.clear()
        binding.otpContainer.removeAllViews()
        for (field in 1..pinLength) {
            val blockBinding = AndromedaPinBoxBinding.inflate(
                LayoutInflater.from(context),
                binding.otpContainer,
                true
            )
            blockViews.add(blockBinding)
            blockBinding.inputField.addTextChangedListener { changes ->
                if (changes.isNullOrEmpty().not() && field < pinLength) {
                    blockViews[field].inputField.requestFocus()
                } else if (changes.isNullOrEmpty() && field > 1) {
                    blockViews[field - 2].inputField.requestFocus()
                }
                updateOtpChanged()
            }
        }
        binding.atvMessage.text = message
        blockViews.forEachIndexed { index, block ->
            block.root.updateLayoutParams<MarginLayoutParams> {
                if (pinLength == 5) {
                    width = MEDIUM_SIZE.dpToPixels()
                    height = MEDIUM_SIZE.dpToPixels()
                } else if (pinLength > 5) {
                    width = SMALL_SIZE.dpToPixels()
                    height = SMALL_SIZE.dpToPixels()
                }
                if (index != 0) {
                    setMargins(16.dpToPixels(), 0, 0, 0)
                }
            }
        }
    }

    private fun updateOtpChanged() {
        text = ""
        blockViews.forEachIndexed { _, blockBinding ->
            val changes = blockBinding.inputField.text
            if (!changes.isNullOrEmpty()) {
                text += changes
            } else {
                return@forEachIndexed
            }
        }
        if (text.length == pinLength) {
            otpListener?.onOtpFilled(text)
        } else {
            clearState()
            otpListener?.onOtpChanged(text)
        }
    }

    private fun resetOtp() {
        if (pinValue == UNFILLED) {
            reset()
            return
        }
        pinValue.toString().toCharArray().forEachIndexed { index, character ->
            blockViews[index].inputField.setText(character.toString())
            blockViews[index].inputField.setSelection(1)
        }
    }

    interface OtpChangeListener {
        fun onOtpChanged(changes: String)
        fun onOtpFilled(otp: String)
    }
}
