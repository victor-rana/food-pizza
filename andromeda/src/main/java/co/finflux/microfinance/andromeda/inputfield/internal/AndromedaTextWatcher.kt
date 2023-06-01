package co.app.food.andromeda.inputfield.internal

import android.text.Editable

class AndromedaTextWatcher(
    inputHandler: (String) -> Unit
) : DefaultTextWatcher() {
    private var inputHandler: ((String) -> Unit)? = {}

    init {
        this.inputHandler = inputHandler
    }

    fun setHandler(block: ((CharSequence) -> Unit)?) {
        this.inputHandler = block
    }

    override fun afterTextChanged(s: Editable?) {
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        s?.let { inputHandler?.invoke(it.toString()) }
    }
}
