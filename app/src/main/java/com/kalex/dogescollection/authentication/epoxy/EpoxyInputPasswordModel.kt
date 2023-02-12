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
    lateinit var comparablePassword: () -> Map<ComparableKey,String>

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
    override fun bind(holder: EpoxyInputPasswordModel.Holder) {
        super.bind(holder)
        textFieldLayoutView = holder.textFieldLayoutView
        holder.textFieldLayoutView.hint = textHint
        holder.textFieldEditView.setOnFocusChangeListener { _, focus: Boolean ->
            currentText = holder.textFieldEditView.text.toString()
            if (!focus) {
                handleComparable{
                    handleRegex(currentText)
                }
            } else {
                onIsFocus.invoke()
                textFieldLayoutView.error = null
            }
        }
    }

    fun handleComparable(function: () -> Unit){

        if(isComparable){
            val map = comparablePassword.invoke()
            if (currentText == map[ComparableKey.COMPARABLE_PASSWORD_TEXT]) {
                function.invoke()
            } else {
                handleError(map[ComparableKey.COMPARABLE_PASSWORD_ERROR])
            }
        }else{
            function.invoke()
        }
    }

    private fun onValidationError(errorMessage: String) {
        //TODO: Add error messages
        textFieldLayoutView.error = errorMessage
    }

    private fun handleError(get: String?) {

        onValidationError(get ?:"Unknown")
        onValidationResult.invoke(false,"")
    }

    private fun handleRegex(currentText: String) {
        if (regexValidation.matches(currentText)) {
            onValidationResult.invoke(true,currentText)
        } else {
            handleError("Validation do not mach")
        }
    }

    inner class Holder():  KotlinEpoxyHolder(){
        val textFieldLayoutView by bind<TextInputLayout>(R.id.passwordInputText)
        val textFieldEditView by bind<TextInputEditText>(R.id.passwordEditText)
    }
}

enum class ComparableKey {
    COMPARABLE_PASSWORD_TEXT,
    COMPARABLE_PASSWORD_ERROR,
}