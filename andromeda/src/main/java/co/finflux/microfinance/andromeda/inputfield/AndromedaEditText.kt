package co.app.food.andromeda.inputfield

import android.content.Context
import android.content.res.ColorStateList
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import co.app.food.andromeda.AndromedaAttributeManager
import co.app.food.andromeda.R
import co.app.food.andromeda.assets.icon.Icon
import co.app.food.andromeda.databinding.AndromedaEditTextBinding
import co.app.food.andromeda.extensions.readAttributes
import co.app.food.andromeda.icons.IconManager
import co.app.food.andromeda.text.TypographyStyle
import co.app.food.andromeda.then
import co.app.food.andromeda.toColorToken
import com.google.android.material.textfield.TextInputLayout.*
import android.graphics.Color as AndroidColor
import android.text.InputType as AndroidInputType
import co.app.food.andromeda.Color as AndromedaColor

private const val DEFAULT_TEXT_LIMIT = 30

class AndromedaEditText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private lateinit var text: String
    private lateinit var hint: String
    private lateinit var helperText: String
    private lateinit var errorText: String
    private var mode: Mode = Mode.EDIT
    private var style: Style = Style.NORMAL
    private var inputType: InputType = InputType.TEXT
    private var iconPosition: IconPosition = IconPosition.START
    private var errorColor: Int = AndroidColor.RED
    private var limit: Int = DEFAULT_TEXT_LIMIT
    private var showLimit: Boolean = false
    private var iconColor: Int = 0
    private var icon: Int = -1
    private var isMandatory: Boolean = false

    private var filters = arrayOf<InputFilter>()
    private val binding: AndromedaEditTextBinding =
        AndromedaEditTextBinding.inflate(LayoutInflater.from(context), this, true)

    // Must be ordered as AndromedaButton attributes in attrs.xml
    enum class Mode {
        EDIT,
        READ_ONLY
    }

    // Must be ordered as AndromedaButton attributes in attrs.xml
    enum class Style {
        OUTLINED,
        NORMAL
    }

    // Must be ordered as AndromedaButton attributes in attrs.xml
    enum class InputType {
        TEXT,
        NAME,
        EMAIL,
        PHONE,
        NUMBER,
        PASSWORD,
        DECIMAL,
        NUMBER_PASSWORD
    }

    // Must be ordered as AndromedaButton attributes in attrs.xml
    enum class IconPosition {
        START,
        END
    }

    init {
        initAttrs(attrs)
        updateView()
    }

    fun setTextIfDifferent(newText: CharSequence?): Boolean {
        if (!isTextDifferent(newText, text)) {
            // Previous text is the same. No op
            return false
        }
        if (newText == null)
            binding.andromedaET.setText("")
        else
            binding.andromedaET.setText(newText)
        // Since the text changed we move the cursor to the end of the new text.
        // This allows us to fill in text programmatically with a different value,
        // but if the user is typing and the view is rebound we won't lose their cursor position.
        newText?.let {
            if (it.length <= binding.andromedaET.text.length) {
                binding.andromedaET.setSelection(it.length)
            }
        }
        return true
    }

    fun setIncludeFontPadding(bool: Boolean) {
        binding.andromedaET.includeFontPadding = bool
    }

    fun getEditableText(): Editable? = binding.andromedaET.editableText

    fun updateCursorColor(color: Int) {
        binding.andromedaET.updateCursorColor(color)
    }

    fun setSingleLine(singleLine: Boolean) {
        this.binding.andromedaET.isSingleLine = singleLine
    }

    fun showError(error: String) {
        binding.andromedaTIL.isErrorEnabled = true
        binding.andromedaTIL.error = error
    }

    fun clearError() {
        binding.andromedaTIL.error = ""
    }

    fun setText(text: String) {
        this.text = text
        this.binding.andromedaET.setText(text)
    }

    fun getText(): String {
        return this.binding.andromedaET.text.toString()
    }

    fun setHint(hint: String) {
        this.hint = hint
        binding.andromedaTIL.hint = hint
    }

    fun setHelperText(helperText: String) {
        this.helperText = helperText
        binding.andromedaTIL.helperText = helperText
        binding.andromedaTIL.isHelperTextEnabled = helperText.isNotEmpty()
    }

    fun setMaxLength(length: Int, showLimit: Boolean = true) {
        this.limit = length
        binding.andromedaTIL.isCounterEnabled = showLimit
        binding.andromedaTIL.counterMaxLength = length
        changeDigitsCount(length)
    }

    fun setMandatory(mandatory: Boolean) {
        this.isMandatory = mandatory
    }

    fun showClearButton(show: Boolean) {
        if (show) {
            binding.andromedaTIL.isEndIconVisible = true
            binding.andromedaTIL.endIconMode = END_ICON_CLEAR_TEXT
        } else {
            binding.andromedaTIL.endIconMode = END_ICON_NONE
        }
    }

    fun setIcon(
        icon: Icon,
        iconColor: AndromedaColor?,
        iconPosition: IconPosition?
    ) {
        when (iconPosition) {
            IconPosition.START -> {
                binding.andromedaTIL.endIconMode = END_ICON_NONE
                binding.andromedaTIL.startIconDrawable =
                    IconManager.getIconDrawable(
                        context,
                        icon,
                        iconColor?.toColorToken()
                            ?: AndromedaAttributeManager.getColorFromAttribute(
                                context,
                                R.attr.fill_active_primary
                            )
                    )
            }
            IconPosition.END -> {
                binding.andromedaTIL.endIconMode = END_ICON_CUSTOM
                binding.andromedaTIL.isEndIconVisible = true
                binding.andromedaTIL.endIconDrawable =
                    IconManager.getIconDrawable(
                        context,
                        icon,
                        iconColor?.toColorToken()
                            ?: AndromedaAttributeManager.getColorFromAttribute(
                                context,
                                R.attr.fill_active_primary
                            )
                    )
            }
        }
    }

    fun setInputType(inputType: InputType) {
        this.inputType = inputType
        binding.andromedaTIL.counterMaxLength = limit
        this.binding.andromedaET.inputType = AndroidInputType.TYPE_NULL
        when (inputType) {
            InputType.TEXT -> {
                this.binding.andromedaET.inputType = AndroidInputType.TYPE_CLASS_TEXT
            }
            InputType.NAME -> {
                this.binding.andromedaET.inputType =
                    AndroidInputType.TYPE_CLASS_TEXT or AndroidInputType.TYPE_TEXT_FLAG_CAP_WORDS
            }
            InputType.EMAIL -> {
                this.binding.andromedaET.inputType =
                    AndroidInputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
            }
            InputType.PHONE -> {
                this.binding.andromedaET.inputType = AndroidInputType.TYPE_CLASS_PHONE
            }
            InputType.NUMBER -> {
                this.binding.andromedaET.inputType =
                    AndroidInputType.TYPE_CLASS_NUMBER
            }
            InputType.PASSWORD -> {
                this.binding.andromedaET.inputType = AndroidInputType.TYPE_TEXT_VARIATION_PASSWORD
                binding.andromedaTIL.isEndIconVisible = true
                binding.andromedaTIL.endIconMode = END_ICON_PASSWORD_TOGGLE
            }
            InputType.DECIMAL -> {
                this.binding.andromedaET.inputType = AndroidInputType.TYPE_NUMBER_FLAG_DECIMAL
            }
            InputType.NUMBER_PASSWORD -> {
                binding.andromedaTIL.counterMaxLength = 4
                changeDigitsCount(4)
                val type = AndroidInputType.TYPE_CLASS_NUMBER or
                    AndroidInputType.TYPE_NUMBER_VARIATION_PASSWORD
                this.binding.andromedaET.inputType = type
                binding.andromedaTIL.isEndIconVisible = true
                binding.andromedaTIL.endIconMode = END_ICON_PASSWORD_TOGGLE
            }
        }
        this.binding.andromedaET.filters = filters
    }

    fun setInputFocus(isFocused: Boolean) {
        isFocused then this.binding.andromedaET.requestFocus()
            ?: this.binding.andromedaET.clearFocus()
    }

    fun isInputFocused(): Boolean {
        return this.binding.andromedaET.isFocused
    }

    fun hideIcons() {
        binding.andromedaTIL.isStartIconVisible = false
        binding.andromedaTIL.isEndIconVisible = false
    }

    fun setError(error: String?) {
        binding.andromedaTIL.error = error
    }

    fun setErrorColor(errorColor: AndromedaColor = AndromedaColor.RED) {
        binding.andromedaTIL.setErrorTextColor(
            ColorStateList.valueOf(
                AndroidColor.parseColor(
                    errorColor.value
                )
            )
        )
    }

    fun setTextChangeWatcher(watcher: TextWatcher) {
        this.binding.andromedaET.addTextChangedListener(watcher)
    }

    fun getMinHeight(): Int {
        return binding.andromedaET.minHeight
    }

    fun setMinHeight(height: Int) {
        binding.andromedaET.minHeight = height
    }

    fun getLineSpacingExtra() = binding.andromedaET.lineSpacingExtra

    fun getTextSize(): Float {
        return binding.andromedaET.textSize
    }

    fun removeTextChangeWatcher(watcher: TextWatcher) {
        this.binding.andromedaET.removeTextChangedListener(watcher)
    }

    fun setLines(lines: Int) {
        this.binding.andromedaET.setLines(lines)
    }

    /**
     *  ET will replace the existing input filters with new ones.
     *  this method Combines old input filters
     *  with newly set input filters for avoiding loss of old input filters.
     *
     *  @param filters New Filters to be added
     *  @see InputFilter
     */
    private fun addFilters(filters: Array<InputFilter.LengthFilter>) {
        val oldFilters = this.binding.andromedaET.filters
        this.binding.andromedaET.filters = oldFilters + filters
    }

    private fun clearInputFilters() {
        this.binding.andromedaET.filters = arrayOf()
    }

    private fun isTextDifferent(str1: CharSequence?, str2: CharSequence?): Boolean =
        when {
            str1 === str2 -> false
            str1 == null || str2 == null -> true
            str1.length != str2.length -> true
            else ->
                str1.toString() != str2.toString() // Needed in case either string is a Spannable
        }

    private fun changeDigitsCount(maxCount: Int) {
        filters = arrayOf(
            InputFilter.LengthFilter(maxCount)
        )
    }

    private fun updateView() {
        setText(this@AndromedaEditText.text)
        setHint(this@AndromedaEditText.hint)
        setMaxLength(
            length = this@AndromedaEditText.limit,
            showLimit = this@AndromedaEditText.showLimit
        )
        setHelperText(this@AndromedaEditText.helperText)
    }

    private fun initAttrs(attrs: AttributeSet?) {
        readAttributes(attrs, R.styleable.AndromedaEditText) { typedArray ->
            this.hint = typedArray.getString(R.styleable.AndromedaEditText_hint) ?: ""
            this.text = typedArray.getString(R.styleable.AndromedaEditText_text) ?: ""
            this.icon = typedArray.getInt(R.styleable.AndromedaEditText_icon_name, this.icon)
            this.isMandatory =
                typedArray.getBoolean(R.styleable.AndromedaEditText_mandatory, this.isMandatory)
            this.iconColor =
                typedArray.getInt(R.styleable.AndromedaEditText_icon_color_token, this.iconColor)
            this.helperText = typedArray.getString(R.styleable.AndromedaEditText_helperText) ?: ""
            this.limit = typedArray.getInteger(R.styleable.AndromedaEditText_limit, this.limit)
            this.showLimit =
                typedArray.getBoolean(R.styleable.AndromedaEditText_showLimit, this.showLimit)
            this.errorText = typedArray.getString(R.styleable.AndromedaEditText_errorText) ?: ""
            this.errorColor =
                typedArray.getColor(R.styleable.AndromedaEditText_errorColor, this.errorColor)
            val mode = typedArray.getInteger(R.styleable.AndromedaEditText_mode, this.mode.ordinal)
            this.mode = Mode.values()[mode]
            val style =
                typedArray.getInteger(R.styleable.AndromedaEditText_style, this.style.ordinal)
            this.style = Style.values()[style]
            val inputType =
                typedArray.getInteger(
                    R.styleable.AndromedaEditText_inputTextType,
                    this.inputType.ordinal
                )
            this.inputType = InputType.values()[inputType]
            val iconPosition = typedArray.getInteger(
                R.styleable.AndromedaEditText_icon_position,
                this.iconPosition.ordinal
            )
            this.iconPosition = IconPosition.values()[iconPosition]
        }
    }

    fun setStyle(textStyle: TypographyStyle) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            this.binding.andromedaET.setTextAppearance(textStyle.style)
        } else {
            this.binding.andromedaET.setTextAppearance(this.context, textStyle.style)
        }
    }
}
