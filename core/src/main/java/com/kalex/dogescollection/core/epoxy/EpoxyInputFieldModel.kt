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
abstract class EpoxyInputFieldModel : EpoxyModelWithHolder<EpoxyInputFieldModel.Holder>() {
    override fun getDefaultLayout(): Int {
        return R.layout.authentication_input_field
    }

    @EpoxyAttribute
    lateinit var textHint: String

    @EpoxyAttribute
    lateinit var regexValidation: Regex

    @EpoxyAttribute
    lateinit var onValidationResult: (result: Boolean, text: String) -> Unit

    @EpoxyAttribute
    var onIsFocus: () -> Unit = {}

    @JvmField
    @EpoxyAttribute
    var isOptional: Boolean = false

    private var currentText: String = ""
    private lateinit var textFieldLayoutView: TextInputLayout
    override fun bind(holder: Holder) {
        super.bind(holder)
        textFieldLayoutView = holder.textFieldLayoutView
        holder.textFieldLayoutView.hint = textHint
        holder.textFieldEditView.setOnFocusChangeListener { _, focus: Boolean ->
            currentText = holder.textFieldEditView.text.toString()
            if (!focus) {
                handleOptional {
                    handleNullOrEmpty(currentText) {
                        handleRegex(currentText)
                    }
                }
            } else {
                onIsFocus.invoke()
                textFieldLayoutView.error = null
            }
        }
        holder.textFieldEditView.afterTextChangedDelayed {
            currentText = holder.textFieldEditView.text.toString()
            handleOptional {
                handleNullOrEmpty(currentText) {
                    handleRegex(currentText)
                }
            }
        }
        holder.textFieldEditView.doOnTextChanged { text, start, before, count ->
            onValidationResult.invoke(false, "")
            textFieldLayoutView.error = null
        }
    }

    private fun onValidationError() {
        // TODO: Add error messages
        textFieldLayoutView.error = "Valida tu usuario"
    }

    private fun handleNullOrEmpty(currentText: String, nextStep: () -> Unit) {
        if (currentText.isNotEmpty()) {
            nextStep()
        } else {
            handleError()
        }
    }

    private fun handleError() {
        onValidationError()
        onValidationResult.invoke(false, "")
    }

    private fun handleRegex(currentText: String) {
        if (regexValidation.matches(currentText)) {
            textFieldLayoutView.error = null
            onValidationResult.invoke(true, currentText)
        } else {
            handleError()
        }
    }

    private fun handleOptional(nextStep: () -> Unit) {
        if (!isOptional) {
            nextStep()
        } else {
            handleRegex(currentText)
        }
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
    inner class Holder() : KotlinEpoxyHolder() {
        val textFieldLayoutView by bind<TextInputLayout>(R.id.textFieldlayout)
        val textFieldEditView by bind<TextInputEditText>(R.id.textFieldEdit)
    }
}
