package co.app.food.andromeda.inputfield

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.InputFilter
import android.text.InputType.*
import android.text.Spanned
import android.text.method.PasswordTransformationMethod
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.EditText
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import co.app.food.andromeda.AndromedaAttributeManager
import co.app.food.andromeda.Color as AndromedaColor
import co.app.food.andromeda.OnClearInput
import co.app.food.andromeda.R
import co.app.food.andromeda.assets.icon.Icon
import co.app.food.andromeda.databinding.AndromedaInputFieldBinding
import co.app.food.andromeda.extensions.*
import co.app.food.andromeda.icons.IconManager
import co.app.food.andromeda.inputfield.internal.DefaultTextWatcher
import co.app.food.andromeda.text.TypographyStyle
import co.app.food.andromeda.text.setStyle
import co.app.food.andromeda.toColorToken
import co.app.food.andromeda.tokens.fill_active_primary
import co.app.food.andromeda.tokens.spacing_4x
import co.app.food.andromeda.tokens.spacing_6x

class AndromedaInputField @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleRes: Int = 0
) : LinearLayout(
    context,
    attributeSet,
    defStyleRes
) {

    private var inputType: InputType = InputType.TEXT
    private var disableClear = false
    private var isRequired = false
    private var label: String? = null
    private var type = InputFieldTypes.INPUT_FIELD_REGULAR
    private var counterMax: Int = 160
    private var filters = arrayOf<InputFilter>()
    private val binding: AndromedaInputFieldBinding = AndromedaInputFieldBinding.inflate(
        LayoutInflater.from(context)
    )

    private var drawableLeft: Drawable? = null
        set(value) {
            field = value
            updateDrawableLeft()
        }

    private var drawableRight: Drawable? = null
        set(value) {
            field = value
            updateDrawableRight()
        }

    private var iconLeftAccessibilityDescription: String? = null
        set(value) {
            field = value
            updateIconLeftAccessibilityDescription()
        }

    private var iconRightAccessibilityDescription: String? = null
        set(value) {
            field = value
            updateIconRightAccessibilityDescription()
        }

    private var runPostLayout: Runnable? = null

    private var focusChangeListener: ViewTreeObserver.OnGlobalFocusChangeListener? = null

    private var animator: Animator? = null

    private var onClearInput: OnClearInput? = null
    private var editText: EditText? = null

    init {
        orientation = VERTICAL
        readAttributes(attributeSet, R.styleable.AndromedaInputField) {
            type = it.getEnum(
                R.styleable.AndromedaInputField_inputField_type,
                InputFieldTypes.values(),
                type
            )
            val inputType =
                it.getInteger(R.styleable.AndromedaInputField_inputType, this.inputType.ordinal)
            this.inputType = InputType.values()[inputType]
            label = it.getString(R.styleable.AndromedaInputField_label)
            isRequired = it.getBoolean(R.styleable.AndromedaInputField_required, isRequired)
        }

        setPadding(
            spacing_4x.toInt(),
            spacing_6x.toInt(),
            spacing_4x.toInt(),
            0
        )
        binding.inputPwdVisibilityOn.setOnClickListener {
            binding.inputPwdVisibilityOn.makeGone()
            binding.inputPwdVisibilityOff.makeVisible()
            editText?.transformationMethod = PasswordTransformationMethod()
            editText?.setSelection(editText?.length() ?: 0)
        }
        binding.inputPwdVisibilityOff.setOnClickListener {
            binding.inputPwdVisibilityOn.makeVisible()
            binding.inputPwdVisibilityOff.makeGone()
            editText?.transformationMethod = null
            editText?.setSelection(editText?.length() ?: 0)
        }
    }

    /** @suppress **/
    override fun addView(child: View?, index: Int, params: ViewGroup.LayoutParams?) {
        if (child is EditText) {
            createInputField(child)
        } else {
            super.addView(child, index, params)
            if (inputType == InputType.PASSWORD) {
                editText?.transformationMethod = PasswordTransformationMethod()
                editText?.setSelection(editText?.length() ?: 0)
            }
        }
    }

    /** @suppress **/
    override fun onDetachedFromWindow() {
        editText?.viewTreeObserver?.removeOnGlobalFocusChangeListener(focusChangeListener)
        focusChangeListener = null
        animator?.cancel()
        super.onDetachedFromWindow()
    }

    fun resetIcons() {
        drawableLeft = null
        drawableRight = null
    }

    /**
     * shows or updates the left icon
     * @param icon Icon to be displayed
     * @param color Tint color to be applied to icon
     */
    fun setDrawableLeft(icon: Icon, color: Int) {
        drawableLeft = IconManager.getIconDrawable(
            context,
            icon,
            color
        )
    }

    /**
     * shows or updates the right icon
     * @param icon Icon to be displayed
     * @param color Tint color to be applied to icon
     */
    fun setDrawableRight(icon: Icon, color: Int) {
        drawableRight = IconManager.getIconDrawable(
            context,
            icon,
            color
        )
    }

    fun resetChildInputViews() {
        binding.inputFieldEditTextContainer.removeAllViews()
    }

    fun addChildInputView(view: View) {
        view.apply {
            if (id == NO_ID) {
                id = R.id.input_field
            }
            layoutParams = LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            setPadding(0, 0, 0, 0)
        }
        binding.inputFieldEditTextContainer.addView(view)

        bindInputFieldProperties()
        toggleRequiredIndicator()
        binding.inputFieldLine.makeGone()
    }

    fun setupCounterMax(counterMax: Int) {
        binding.inputCounter.makeVisible()
        this.counterMax = counterMax
        editText?.let {
            changeDigitsCount(counterMax)
            binding.inputCounter.text = "${it.length()}/$counterMax"
        } ?: binding.inputCounter.makeGone()
    }

    fun toggleError(errorMessage: String? = null) {
        if (errorMessage.isNullOrEmpty()) {
            hideError()
        } else {
            showError(errorMessage)
        }
    }

    private fun changeDigitsCount(maxCount: Int) {
        filters = arrayOf(
            InputFilter.LengthFilter(maxCount)
        )
        editText?.let {
            it.filters = filters
        }
    }

    private fun showError(errorMessage: String) {
        val xTranslation1 = ValueAnimator.ofFloat(0f, 10f)
        xTranslation1.duration = 33
        xTranslation1.startDelay = 34
        xTranslation1.addUpdateListener {
            val value = it.animatedValue as Float
            binding.inputFieldError.translationX = value
            binding.inputFieldLine.translationX = value
        }
        val xTranslation2 = ValueAnimator.ofFloat(10f, 0f)
        xTranslation2.duration = 33
        xTranslation2.startDelay = 34
        xTranslation2.addUpdateListener {
            val value = it.animatedValue as Float
            binding.inputFieldError.translationX = value
            binding.inputFieldLine.translationX = value
        }
        val xTranslation3 = ValueAnimator.ofFloat(0f, 10f)
        xTranslation3.duration = 33
        xTranslation3.startDelay = 34
        xTranslation3.addUpdateListener {
            val value = it.animatedValue as Float
            binding.inputFieldError.translationX = value
            binding.inputFieldLine.translationX = value
        }
        val xTranslation4 = ValueAnimator.ofFloat(10f, 0f)
        xTranslation4.duration = 33
        xTranslation4.startDelay = 34
        xTranslation4.addUpdateListener {
            val value = it.animatedValue as Float
            binding.inputFieldError.translationX = value
            binding.inputFieldLine.translationX = value
        }

        val animatorSet = AnimatorSet()
        animatorSet.playSequentially(xTranslation1, xTranslation2, xTranslation3, xTranslation4)
        animatorSet.startDelay = 33

        binding.inputFieldError.apply {
            text = errorMessage
            makeVisible()
            animatorSet.start()
        }
        binding.inputFieldLine.setBackgroundColor(
            AndromedaAttributeManager.getColorFromAttribute(
                context,
                AndromedaColor.RED.toColorToken()
            )
        )
        if (binding.inputPwdVisibilityOff.isVisible() or binding.inputPwdVisibilityOn.isVisible()) {
            disableClearButton()
        }
        updateInputFieldLineColor(editText?.hasFocus() ?: true)
    }

    private fun hideError() {
        binding.inputFieldError.text = ""
        binding.inputFieldError.makeGone()
        updateInputFieldLineColor(hasFocus())
        updateInputFieldLineColor(editText?.hasFocus() ?: true)
    }

    fun setLabel(label: String) {
        this.label = label
        bindInputFieldProperties()
    }

    fun setRequired(required: Boolean) {
        isRequired = required
        toggleRequiredIndicator()
    }

    fun disableUnderline() {
        binding.inputFieldLine.makeGone()
    }

    fun disableClearButton() {
        disableClear = true
        hideClearButton()
    }

    fun enableClearButton() {
        disableClear = false
        toggleClearButton(this, editText?.isFocused == true)
    }

    fun showCounter(show: Boolean) {
        if (show) {
            binding.inputCounter.makeVisible()
        } else {
            binding.inputCounter.makeGone()
        }
    }

    fun setInputType(inputType: InputType) {
        this.inputType = inputType
        editText?.inputType = TYPE_NULL
        binding.inputPwdVisibilityOff.makeGone()
        binding.inputPwdVisibilityOn.makeGone()
        when (inputType) {
            InputType.TEXT -> {
                editText?.inputType = TYPE_CLASS_TEXT
                enableClearButton()
            }
            InputType.NAME -> {
                editText?.inputType =
                    TYPE_CLASS_TEXT or TYPE_TEXT_FLAG_CAP_WORDS
                enableClearButton()
            }
            InputType.EMAIL -> {
                editText?.inputType =
                    TYPE_TEXT_VARIATION_EMAIL_ADDRESS
                enableClearButton()
            }
            InputType.PHONE -> {
                editText?.inputType = TYPE_CLASS_PHONE
                enableClearButton()
            }
            InputType.NUMBER -> {
                editText?.inputType =
                    TYPE_CLASS_NUMBER
                enableClearButton()
            }
            InputType.PASSWORD -> {
                counterMax = 24
                changeDigitsCount(24)
                editText?.inputType = TYPE_TEXT_VARIATION_PASSWORD
                disableClearButton()
                binding.inputPwdVisibilityOff.makeVisible()
            }
            InputType.DECIMAL -> {
                editText?.inputType =
                    TYPE_NUMBER_FLAG_DECIMAL or TYPE_CLASS_NUMBER
            }
            InputType.NUMBER_PASSWORD -> {
                counterMax = 4
                disableClearButton()
                changeDigitsCount(4)
                binding.inputPwdVisibilityOff.makeVisible()
                editText?.inputType = TYPE_CLASS_NUMBER or TYPE_NUMBER_VARIATION_PASSWORD
            }
        }
        editText?.filters = filters
    }

    internal fun alignViewToInputFoodItem(view: View) {
        if (ensureNoParent(view)) {
            binding.inputFieldContent.addView(view)
            val constraintSet = ConstraintSet().apply { clone(binding.inputFieldContent) }
            constraintSet.apply {
                connect(
                    view.id,
                    ConstraintSet.BOTTOM,
                    binding.inputFieldEditTextContainer.id,
                    ConstraintSet.BOTTOM
                )

                connect(
                    view.id,
                    ConstraintSet.TOP,
                    binding.inputFieldEditTextContainer.id,
                    ConstraintSet.TOP
                )

                connect(
                    view.id,
                    ConstraintSet.END,
                    ConstraintSet.PARENT_ID,
                    ConstraintSet.END
                )

                connect(
                    binding.inputFieldEditTextContainer.id,
                    ConstraintSet.END,
                    view.id,
                    ConstraintSet.START
                )
            }

            constraintSet.applyTo(binding.inputFieldContent)
        }
    }

    private fun createInputField(child: EditText) {
        editText = child
        addEditTextProperties(child)
        addListeners(child)
        val root = initInputFieldLayout().apply {
            binding.inputFieldEditTextContainer.addView(child)
        }
        editText?.isSingleLine = true

        setInputType(inputType)
        setInputFieldStyle(child)
        bindInputFieldProperties()
        toggleRequiredIndicator()
        addView(root)
        updateInputFieldLineColor(root.hasFocus())
        toggleClearButton(root, hasFocus())
        if (inputType == InputType.PASSWORD) {
            editText?.transformationMethod = PasswordTransformationMethod()
            editText?.setSelection(editText?.length() ?: 0)
        }
        runPostLayout?.run()
    }

    private fun addEditTextProperties(editText: EditText) {
        editText.apply {
            if (id == NO_ID) {
                id = R.id.input_field
            }
            layoutParams = LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            background = null
            setPadding(0, 0, 0, 0)
            includeFontPadding = false
            updateCursorColor(fill_active_primary)
        }
    }

    private fun initInputFieldLayout(): ConstraintLayout = binding.root

    private fun setInputFieldStyle(view: EditText) {
        when (type) {
            InputFieldTypes.INPUT_FIELD_LARGE -> {
                view.setStyle(TypographyStyle.TITLE_HERO_DEFAULT.style)
            }
            InputFieldTypes.INPUT_FIELD_REGULAR -> {
                view.setStyle(TypographyStyle.TITLE_MODERATE_DEMI_DEFAULT.style)
            }
        }

        view.minHeight = view.lineSpacingExtra.getLineHeightDp(
            context
        ).toPxInt(context)
    }

    private fun bindInputFieldProperties() {
        if (label.isNullOrEmpty().not()) {
            binding.inputFieldLabel.text = label
            binding.inputFieldLabel.makeVisible()
            binding.inputFieldLabel.setStyle(TypographyStyle.TITLE_SMALL_BOLD_DEFAULT.style, true)
        } else {
            binding.inputFieldLabel.makeGone()
        }
        binding.inputFieldClear.setOnClickListener {
            editText?.editableText?.clear()
            onClearInput?.invoke()
        }
    }

    private fun addListeners(view: EditText) {
        focusChangeListener = ViewTreeObserver.OnGlobalFocusChangeListener { _, _ ->
            updateInputFieldLineColor(view.hasFocus())
            toggleClearButton(this@AndromedaInputField, view.hasFocus())
        }
        editText?.viewTreeObserver?.addOnGlobalFocusChangeListener(focusChangeListener)

        editText?.addTextChangedListener(object : DefaultTextWatcher() {
            override fun afterTextChanged(s: Editable?) {
                binding.inputCounter.text = "${s?.length ?: 0}/$counterMax"
                s?.setSpan(
                    AndromedaLineHeightSpan(
                        view.minHeight,
                        view.textSize.toInt()
                    ),
                    0,
                    s.toString().length,
                    Spanned.SPAN_INCLUSIVE_INCLUSIVE
                )
            }
        })
    }

    private fun updateInputFieldLineColor(hasFocus: Boolean) {
        val inputFieldLineColor = when {
            hasFocus -> {
                R.attr.fill_active_primary
            }
            binding.inputFieldClear.isVisible() -> {
                R.attr.fill_inactive_primary
            }
            else -> {
                R.attr.border_inactive
            }
        }

        binding.inputFieldLine.setBackgroundColor(
            AndromedaAttributeManager.getColorFromAttribute(
                context,
                inputFieldLineColor
            )
        )
    }

    private fun hideClearButton() {
        binding.inputFieldClear.let {
            animator = ObjectAnimator.ofFloat(it, View.ALPHA, 0f).apply {
                duration = 84
                start()
            }
        }
    }

    private fun showClearButton() {
        val alphaAnimation = ObjectAnimator.ofFloat(binding.inputFieldClear, View.ALPHA, 1f)
        alphaAnimation.duration = 167
        val scaleToBigXAnimation =
            ObjectAnimator.ofFloat(binding.inputFieldClear, View.SCALE_X, 0f, 1.1f)
        scaleToBigXAnimation.duration = 167
        val scaleToBigYAnimation =
            ObjectAnimator.ofFloat(binding.inputFieldClear, View.SCALE_Y, 0f, 1.1f)
        scaleToBigYAnimation.duration = 167
        val scaleToSmallXAnimation =
            ObjectAnimator.ofFloat(binding.inputFieldClear, View.SCALE_X, 1.1f, 1f)
        scaleToSmallXAnimation.duration = 83
        scaleToSmallXAnimation.startDelay = 167
        val scaleToSmallYAnimation =
            ObjectAnimator.ofFloat(binding.inputFieldClear, View.SCALE_Y, 1.1f, 1f)
        scaleToSmallYAnimation.duration = 83
        scaleToSmallYAnimation.startDelay = 167

        animator = AnimatorSet().apply {
            playTogether(
                alphaAnimation,
                scaleToBigXAnimation,
                scaleToBigYAnimation,
                scaleToSmallXAnimation,
                scaleToSmallYAnimation
            )
            start()
        }
    }

    private fun toggleClearButton(view: View, isFocused: Boolean) {
        if (!editText!!.isEnabled) {
            hideClearButton()
        } else {
            if (!isFocused || disableClear) {
                hideClearButton()
            } else {
                showClearButton()
            }
        }
    }

    private fun toggleRequiredIndicator() {
        with(binding) {
            if (isRequired && binding.inputFieldLabel.isVisible()) {
                inputFieldRequiredMarker.makeVisible()
            } else {
                inputFieldRequiredMarker.makeGone()
            }
        }
    }

    private fun ensureNoParent(view: View): Boolean {
        removeView(view)
        if (null != view.parent) {
            binding.inputFieldContent.removeView(view)
        }

        return null == view.parent
    }

    private fun updateDrawableLeft() {
        if (drawableLeft != null) {
            binding.inputDrawableLeft.apply {
                makeVisible()
                setImageDrawable(drawableLeft)
            }
        }
    }

    private fun updateDrawableRight() {
        if (drawableRight != null) {
            binding.inputDrawableRight.apply {
                makeVisible()
                setImageDrawable(drawableRight)
            }
        }

        disableClear = drawableRight != null
    }

    private fun updateIconLeftAccessibilityDescription() {
        iconLeftAccessibilityDescription?.let {
            binding.inputDrawableLeft.contentDescription = it
        }
    }

    private fun updateIconRightAccessibilityDescription() {
        iconRightAccessibilityDescription?.let {
            binding.inputDrawableRight.contentDescription = it
        }
    }
}
