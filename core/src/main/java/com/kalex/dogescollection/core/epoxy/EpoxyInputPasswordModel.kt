package com.kalex.dogescollection.core.epoxy

import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.widget.TextView
import androidx.core.widget.doOnTextChanged
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.kalex.dogescollection.core.R
import com.kalex.dogescollection.core.common.epoxyhelpers.KotlinEpoxyHolder

@EpoxyModelClass()
abstract class EpoxyInputPasswordModel : EpoxyModelWithHolder<EpoxyInputPasswordModel.Holder>() {
    override fun getDefaultLayout(): Int {
        return R.layout.authentication_input_password_field
    }

    @EpoxyAttribute
    lateinit var textHint: String

    @EpoxyAttribute
    lateinit var regexValidation: Regex

    @EpoxyAttribute
    lateinit var regexError: String

    @EpoxyAttribute
    var onIsFocus: () -> Unit = {}

    @EpoxyAttribute
    lateinit var comparablePassword: () -> Map<ComparableKey, String>

    @JvmField
    @EpoxyAttribute
    var isComparable: Boolean = false

    @EpoxyAttribute
    lateinit var onValidationResult: (result: Boolean, text: String) -> Unit

    @JvmField
    @EpoxyAttribute
    var minLength: Int = 5

    private var currentText: String = ""
    private lateinit var textFieldLayoutView: TextInputLayout
    override fun bind(holder: Holder) {
        super.bind(holder)
        textFieldLayoutView = holder.textFieldLayoutView
        holder.textFieldLayoutView.hint = textHint
        holder.textFieldEditView.setOnFocusChangeListener { _, focus: Boolean ->

            if (!focus) {
                handleComparable {
                    handleRegex(currentText)
                }
            } else {
                onIsFocus.invoke()
                textFieldLayoutView.error = null
            }
        }
        holder.textFieldEditView.afterTextChangedDelayed {
            handleComparable {
                handleRegex(it)
            }
        }
        holder.textFieldEditView.doOnTextChanged { text, _, _, _ ->
            onValidationResult.invoke(false, "")
            currentText = text.toString()
            textFieldLayoutView.error = null
        }
    }

    private fun handleComparable(function: () -> Unit) {
        if (isComparable) {
            val map = comparablePassword.invoke()
            if (currentText == map[ComparableKey.COMPARABLE_PASSWORD_TEXT]) {
                function.invoke()
            } else {
                handleError(map[ComparableKey.COMPARABLE_PASSWORD_ERROR])
            }
        } else {
            function.invoke()
        }
    }

    private fun onValidationError(errorMessage: String) {
        // TODO: Add error messages
        textFieldLayoutView.error = errorMessage
    }

    private fun handleError(errorMessage: String?) {
        onValidationError(errorMessage ?: "Unknown")
        onValidationResult.invoke(false, "")
    }

    private fun handleRegex(currentText: String) {
        if (regexValidation.matches(currentText)) {
            textFieldLayoutView.error = null
            onValidationResult.invoke(true, currentText)
        } else {
            handleError(regexError)
        }
    }

    inner class Holder() : KotlinEpoxyHolder() {
        val textFieldLayoutView by bind<TextInputLayout>(R.id.passwordInputText)
        val textFieldEditView by bind<TextInputEditText>(R.id.passwordEditText)
    }
    private fun TextView.afterTextChangedDelayed(afterTextChanged: (String) -> Unit) {
        this.addTextChangedListener(object : TextWatcher {
            var timer: CountDownTimer? = null

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(editable: Editable?) {
                timer?.cancel()
                timer = object : CountDownTimer(1300, 1800) {
                    override fun onTick(millisUntilFinished: Long) {}
                    override fun onFinish() {
                        afterTextChanged.invoke(editable.toString())
                    }
                }.start()
            }
        })
    }
}

enum class ComparableKey {
    COMPARABLE_PASSWORD_TEXT,
    COMPARABLE_PASSWORD_ERROR,
}
