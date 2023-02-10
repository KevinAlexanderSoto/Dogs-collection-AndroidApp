package com.kalex.dogescollection.authentication.epoxy

import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.kalex.dogescollection.R
import com.kalex.dogescollection.common.epoxyhelpers.KotlinEpoxyHolder

@EpoxyModelClass(layout = R.layout.authentication_input_password_field)
abstract class EpoxyInputPasswordModel : EpoxyModelWithHolder<EpoxyInputPasswordModel.Holder>() {

    @EpoxyAttribute
    lateinit var textHint: String

    @EpoxyAttribute
    lateinit var regexValidation: Regex

    @EpoxyAttribute
    lateinit var onIsFocus: () -> Unit

    @EpoxyAttribute
    lateinit var onValidationResult: (result: Boolean) -> Unit

    @JvmField
    @EpoxyAttribute
    var minLength: Int = 5
    
    private var currentText: String = ""
    private lateinit var textFieldLayoutView: TextInputLayout
    override fun bind(holder: EpoxyInputPasswordModel.Holder) {
        super.bind(holder)
        textFieldLayoutView = holder.textFieldLayoutView
        holder.textFieldLayoutView.hint = textHint
        holder.textFieldEditView.setOnFocusChangeListener { _, focus: Boolean ->
            currentText = holder.textFieldEditView.text.toString()
            if (!focus) {
                    handleMinLength(currentText) {
                        handleRegex()
                    }
            } else {
                onIsFocus.invoke()
                textFieldLayoutView.error = null
            }
        }
    }

    private fun onValidationError(textFieldLayoutView: TextInputLayout) {
        //TODO: Add error messages
        textFieldLayoutView.error = "error"
    }

    private fun handleMinLength(currentText: String, nextStep: () -> Unit) {
        if (currentText.length >= minLength) {
            nextStep()
        } else {
            handleError()
        }
    }

    private fun handleError() {
        onValidationError(textFieldLayoutView)
        onValidationResult.invoke(false)
    }

    private fun handleRegex() {
        if (regexValidation.matches(currentText)) {
            onValidationResult.invoke(true)
        } else {
            handleError()
        }
    }

    inner class Holder():  KotlinEpoxyHolder(){
        val textFieldLayoutView by bind<TextInputLayout>(R.id.passwordInputText)
        val textFieldEditView by bind<TextInputEditText>(R.id.passwordEditText)
    }
}